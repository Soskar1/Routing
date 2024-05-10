package graphs;

import java.util.HashMap;
import java.util.Set;

public class WeightedGraph<T> {
    private final HashMap<Integer, Node<T>> nodes = new HashMap<>();

    public WeightedGraph() {}

    public WeightedGraph(List<List<Integer>> adjacencyMatrix) {
        constructGraph(adjacencyMatrix);
    }

    private void constructGraph(List<List<Integer>> adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.size(); ++i) {
            Node node = new Node(i);
            nodes.put(i, node);
        }

        for (int i = 0; i < adjacencyMatrix.size(); ++i) {
            for (int j = 0; j < adjacencyMatrix.get(i).size(); ++j) {
                int weight = adjacencyMatrix.get(i).get(j);

                if (weight == 0) {
                    continue;
                }

                connectNodes(i, j, weight);
            }
        }
    }

    public void addNode(int nodeID) {
        if (nodes.containsKey(nodeID)) {
            System.out.println("Node with id " + nodeID + " already exists");
            return;
        }

        Node<T> node = new Node<>(nodeID, value);
        nodes.put(nodeID, node);
    }

    public void connectNodes(int firstNodeID, int secondNodeID, int weight) {
        if (nodes.containsKey(firstNodeID) && nodes.containsKey(secondNodeID)) {
            Node<T> tmp1 = nodes.get(firstNodeID);
            Node<T> tmp2 = nodes.get(secondNodeID);

            tmp1.addAdjacentNode(tmp2, weight);
            tmp2.addAdjacentNode(tmp1, weight);
        }
    }

    public void removeConnection(int firstNodeID, int secondNodeID) {
        if (nodes.containsKey(firstNodeID) && nodes.containsKey(secondNodeID)) {
            Node<T> tmp1 = nodes.get(firstNodeID);
            Node<T> tmp2 = nodes.get(secondNodeID);

            tmp1.removeAdjacentNode(tmp2);
            tmp2.removeAdjacentNode(tmp1);
        }
    }

    public void removeNode(int nodeID) {
        if (!nodes.containsKey(nodeID)) {
            System.out.println("Node with id " + nodeID + " does not exist");
            return;
        }

        Node<T> nodeToRemove = nodes.get(nodeID);
        Set<Node<T>> adjacentNodes = nodeToRemove.getAdjacentNodes();

        for (Node<T> node : adjacentNodes) {
            nodeToRemove.removeAdjacentNode(node);
            node.removeAdjacentNode(nodeToRemove);
        }

        nodes.remove(nodeID);
    }

    public void updateWeight(int firstNodeID, int secondNodeID, int newWeight) {
        if (nodes.containsKey(firstNodeID) && nodes.containsKey(secondNodeID)) {
            Node<T> tmp1 = nodes.get(firstNodeID);
            Node<T> tmp2 = nodes.get(secondNodeID);

            tmp1.updateWeight(tmp2, newWeight);
            tmp2.updateWeight(tmp1, newWeight);
        }
    }
}
