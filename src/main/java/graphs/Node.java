package graphs;

import java.util.HashMap;
import java.util.Set;

public class Node {
    private final int id;
    private final HashMap<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addAdjacentNode(Node node, int weight) {
        adjacentNodes.put(node, weight);
    }

    public void removeAdjacentNode(Node node) {
        adjacentNodes.remove(node);
    }

    public void removeAdjacentNode(int id) {
        Set<Node> nodes = adjacentNodes.keySet();

        for (Node node : nodes) {
            if (node.getId() == id) {
                adjacentNodes.remove(node);
                return;
            }
        }
    }
}
