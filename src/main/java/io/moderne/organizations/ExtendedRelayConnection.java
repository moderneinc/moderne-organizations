package io.moderne.organizations;

import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import org.openrewrite.internal.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a connection in Relay however contains extra metadata about the
 * connection.  For example (original intent) storing the total result count
 * before Relay pagination.
 */
public class ExtendedRelayConnection<T> implements Connection<T> {

    static final int NO_COUNT = -1;

    private final List<Edge<T>> edges;
    private final PageInfo pageInfo;
    private final Integer count;

    public ExtendedRelayConnection(List<Edge<T>> edges, PageInfo pageInfo, Integer count) {
        this.edges = edges;
        this.pageInfo = pageInfo;
        this.count = count;
    }

    @Override
    public List<Edge<T>> getEdges() {
        return edges;
    }

    @Override
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public Integer getCount() {
        return count;
    }

    public static <V> Mono<ExtendedRelayConnection<V>> getConnection(
            DataFetchingEnvironment dfe,
            Mono<Integer> resultCountMono,
            Function<LimitOffset, Flux<V>> nodeFetcher
    ) {
        String after = dfe.getArgument("after");
        Integer first = dfe.getArgument("first");
        String before = dfe.getArgument("before");
        Integer last = dfe.getArgument("last");
        boolean isTotalRequested = dfe.getSelectionSet().contains("count");
        Mono<Integer> resultCountMonoIfRequested = isTotalRequested ? resultCountMono : Mono.just(NO_COUNT);

        int offset = 0;
        int limit = first == null ? 20 : first;
        if (!isTotalRequested) {
            // If we do not have the total count we search for 1 more than requested to determine if there is a next page
            limit += 1;
        }
        if (!StringUtils.isNullOrEmpty(after)) {
            offset = Integer.parseInt(after) + 1;
        } else if (!StringUtils.isNullOrEmpty(before)) {
            limit = last == null ? 20 : last;
            offset = Integer.parseInt(before) - limit;
        }

        LimitOffset limitOffset = new LimitOffset(limit, Math.max(offset, 0));
        return resultCountMonoIfRequested.flatMap(resultCount -> nodeFetcher.apply(limitOffset)
                .collectList()
                .map(nodes -> {
                    boolean hasNextPage;
                    int edgeCount = nodes.size();
                    if (isTotalRequested) {
                        hasNextPage = resultCount > limitOffset.getOffset() + limitOffset.getLimit();
                    } else {
                        hasNextPage = StringUtils.isNullOrEmpty(before) && nodes.size() == limitOffset.getLimit(); // we found 1 more than we actually want, so there is a next page
                        if (hasNextPage) {
                            edgeCount = nodes.size() - 1;
                        }
                    }
                    List<Edge<V>> edges = new ArrayList<>(edgeCount);
                    for (int i = 0; i < edgeCount; i++) {
                        edges.add(new DefaultEdge<>(nodes.get(i),
                                new DefaultConnectionCursor(Integer.toString(limitOffset.getOffset() + i))));
                    }
                    return new ExtendedRelayConnection<>(edges,
                            new DefaultPageInfo(
                                    new DefaultConnectionCursor(Integer.toString(limitOffset.getOffset())),
                                    new DefaultConnectionCursor(Integer.toString(limitOffset.getOffset() + edges.size() - 1)),
                                    limitOffset.getOffset() > 0,
                                    hasNextPage
                            ),
                            resultCount
                    );
                }));
    }

    public static class LimitOffset {
        private final int limit;
        private final int offset;

        public LimitOffset(int limit, int offset) {
            this.limit = limit;
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public int getOffset() {
            return offset;
        }
    }
}
