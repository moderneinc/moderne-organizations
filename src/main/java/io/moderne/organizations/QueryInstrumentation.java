package io.moderne.organizations;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class QueryInstrumentation implements Instrumentation {

    private final Logger log = LoggerFactory.getLogger(QueryInstrumentation.class);

    @Override
    public @NotNull CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult, InstrumentationExecutionParameters parameters, InstrumentationState state) {
        String errors = executionResult.getErrors().isEmpty() ? "None" : executionResult.getErrors().stream().map(Object::toString).collect(Collectors.joining("- \n", "- \n", ""));
        if (parameters.getQuery().contains("TypeIntrospection")) {
            log.info("""
                    Completed execution of TypeIntrospection:
                    {}
                    Variables: {}
                    Result: {}
                    Errors: {}
                    """, parameters.getQuery(), parameters.getVariables(), executionResult.getData(), errors);
        } else {
            log.info("Completed execution of query `{}...` with errors: {}", parameters.getQuery().split("\n")[0], errors);
        }
        return Instrumentation.super.instrumentExecutionResult(executionResult, parameters, state);
    }
}