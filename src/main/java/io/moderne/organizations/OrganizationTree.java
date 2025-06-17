package io.moderne.organizations;

import org.jspecify.annotations.Nullable;

import java.util.*;

class OrganizationTree {
    private final Map<String, Node> nodeMap = new LinkedHashMap<>();
    private final List<Organization<?>> roots = new ArrayList<>();

    public OrganizationTree(Collection<Organization<?>> organizations) {
        for (Organization<?> org : organizations) {
            nodeMap.put(org.getId(), new Node(org));
        }

        for (Node node : nodeMap.values()) {
            if (node.getParentId() != null && !node.isParentRoot()) {
                Node parent = nodeMap.get(node.getParentId());
                parent.children.add(node);
            } else {
                roots.add(node.organization);
            }
        }
    }

    public int size() {
        return nodeMap.size();
    }

    public boolean isEmpty() {
        return nodeMap.isEmpty();
    }

    public Collection<Organization<?>> roots() {
        return roots;
    }

    /**
     * Returns the subtree of the given organization excluding itself
     *
     * @param orgId the id of the organization
     * @return the children
     */
    public Collection<Organization<?>> findChildren(String orgId) {
        List<Organization<?>> subTree = findSubtree(orgId);
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
    public List<Organization<?>> findSubtree(String orgId) {
        Node node = nodeMap.get(orgId);
        if (node == null) {
            return List.of();
        }
        List<Organization<?>> subtree = new ArrayList<>();
        collectSubtree(node, subtree);
        return subtree;
    }

    private void collectSubtree(Node node, List<Organization<?>> subtree) {
        subtree.add(node.organization);
        for (Node child : node.children) {
            collectSubtree(child, subtree);
        }
    }

    private static class Node {
        Organization<?> organization;
        List<Node> children = new ArrayList<>();

        Node(Organization<?> organization) {
            this.organization = organization;
        }

        public @Nullable String getParentId() {
            return organization.getParent().getId();
        }

        public Boolean isParentRoot() {
            return organization.getParent().isRoot();
        }
    }

}
