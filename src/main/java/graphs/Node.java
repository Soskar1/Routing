package graphs;

import java.util.HashMap;
import java.util.Set;

public class Node<T> {
    private final int id;
    private final T value;
    private final HashMap<Node<T>, Integer> adjacentNodes = new HashMap<>();

    public Node(int id, T value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

    public void addAdjacentNode(Node<T> node, int weight) {
        adjacentNodes.put(node, weight);
    }

    public boolean hasAdjacentNode(Node<T> node) {
        return adjacentNodes.containsKey(node);
    }

    public Set<Node<T>> getAdjacentNodes() {
        return adjacentNodes.keySet();
    }

    public HashMap<Node<T>, Integer> getAdjacentNodesWithWeights() {
        return adjacentNodes;
    }

    public void removeAdjacentNode(Node<T> node) {
        adjacentNodes.remove(node);
    }

    public void updateWeight(Node<T> node, int newWeight) {
        if (adjacentNodes.containsKey(node)) {
            adjacentNodes.replace(node, newWeight);
        }
    }
}
