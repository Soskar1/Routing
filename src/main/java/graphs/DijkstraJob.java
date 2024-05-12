package graphs;

import java.util.*;
import java.util.concurrent.Callable;

public class DijkstraJob implements Callable<DijkstraJob> {
    private final WeightedGraph graph;
    private final Node startNode;
    private final int startNodeID;

    private final HashMap<Integer, Integer> path = new HashMap<>();
    private final HashMap<Integer, Integer> distances = new HashMap<>();
    private final HashMap<Integer, Boolean> visited = new HashMap<>();

    Queue<Node> queue = new PriorityQueue<>((node1, node2) -> {
        int distance1 = distances.get(node1.getId());
        int distance2 = distances.get(node2.getId());

        if (distance1 < distance2) {
            return -1;
        }

        if (distance1 > distance2) {
            return 1;
        }

        return 0;
    });

    public DijkstraJob(WeightedGraph graph, int startNodeID) {
        this.graph = graph;
        this.startNodeID = startNodeID;
        this.startNode = graph.getNode(startNodeID);
    }

    public HashMap<Integer, Integer> getPath() {
        return path;
    }

    public int getStartNodeID() {
        return startNodeID;
    }

    @Override
    public DijkstraJob call() {
        dijkstra();
        return this;
    }

    private void dijkstra() {
        Set<Integer> nodeIDs = graph.getNodes();

        for (Integer nodeID : nodeIDs) {
            path.put(nodeID, null);
            distances.put(nodeID, Integer.MAX_VALUE);
            visited.put(nodeID, false);
        }

        distances.replace(startNode.getId(), 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node queuedNode = queue.poll();

            HashMap<Node, Integer> adjacentNodeWeights = queuedNode.getAdjacentNodesWithWeights();
            Set<Node> adjacentNodes = adjacentNodeWeights.keySet();

            for (Node node : adjacentNodes) {
                if (!visited.get(node.getId())) {
                    int newDistance = distances.get(queuedNode.getId()) + adjacentNodeWeights.get(node);
                    int currentDistance = distances.get(node.getId());

                    if (newDistance < currentDistance) {
                        distances.replace(node.getId(), newDistance);
                        path.put(node.getId(), queuedNode.getId());

                        if (currentDistance == Integer.MAX_VALUE) {
                            queue.add(node);
                        }
                    }
                }
            }

            visited.put(queuedNode.getId(), true);
        }
    }
}
