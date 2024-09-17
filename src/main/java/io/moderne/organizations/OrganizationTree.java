package io.moderne.organizations;

import org.jspecify.annotations.Nullable;

import java.util.*;

class OrganizationTree {
    private final Map<String, Node> nodeMap = new LinkedHashMap<>();
    private final List<OrganizationRepositories> roots = new ArrayList<>();

    public OrganizationTree(Collection<OrganizationRepositories> organizations) {
        for (OrganizationRepositories org : organizations) {
            nodeMap.put(org.id(), new Node(org));
        }

        for (Node node : nodeMap.values()) {
            if (node.getParentId() != null) {
                Node parent = nodeMap.get(node.getParentId());
                parent.children.add(node);
            } else {
                roots.add(node.organization);
            }
        }
    }

    public Collection<OrganizationRepositories> all() {
        return asMap().values();
    }

    public @Nullable OrganizationRepositories findOrganization(String id) {
        return nodeMap.containsKey(id) ? nodeMap.get(id).organization : null;
    }

    public int size() {
        return nodeMap.size();
    }

    public boolean isEmpty() {
        return nodeMap.isEmpty();
    }

    public Collection<OrganizationRepositories> roots() {
        return roots;
    }

    /**
     * Returns the subtree of the given organization excluding itself
     *
     * @param orgId the id of the organization
     * @return the children
     */
    public Collection<OrganizationRepositories> findChildren(String orgId) {
        List<OrganizationRepositories> subTree = findSubtree(orgId);
        if (!subTree.isEmpty()) {
            subTree.remove(0);
        }
        return subTree;
    }

    /**
     * Returns the subtree of the given organization including itself
     *
     * @param orgId the id of the organization
     * @return the subtree
     */
    public List<OrganizationRepositories> findSubtree(String orgId) {
        Node node = nodeMap.get(orgId);
        if (node == null) {
            return List.of();
        }
        List<OrganizationRepositories> subtree = new ArrayList<>();
        collectSubtree(node, subtree);
        return subtree;
    }

    private void collectSubtree(Node node, List<OrganizationRepositories> subtree) {
        subtree.add(node.organization);
        for (Node child : node.children) {
            collectSubtree(child, subtree);
        }
    }

    public Map<String, OrganizationRepositories> asMap() {
        // Instead of recomputing the map we extract values on the fly in a view of the original NodeMap
        return new AbstractMap<>() {
            @Override
            public Set<Entry<String, OrganizationRepositories>> entrySet() {
                return new AbstractSet<>() {
                    @Override
                    public Iterator<Entry<String, OrganizationRepositories>> iterator() {
                        return new Iterator<>() {
                            private final Iterator<Entry<String, Node>> originalIterator = nodeMap.entrySet().iterator();

                            @Override
                            public boolean hasNext() {
                                return originalIterator.hasNext();
                            }

                            @Override
                            public Entry<String, OrganizationRepositories> next() {
                                Entry<String, Node> originalEntry = originalIterator.next();
                                return new SimpleEntry<>(originalEntry.getKey(), originalEntry.getValue().organization);
                            }
                        };
                    }

                    @Override
                    public int size() {
                        return nodeMap.size();
                    }
                };
            }
        };
    }

    private static class Node {
        OrganizationRepositories organization;
        List<Node> children = new ArrayList<>();

        Node(OrganizationRepositories organization) {
            this.organization = organization;
        }

        public @Nullable String getParentId() {
            return organization.parent();
        }
    }

}
