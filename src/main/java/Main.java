import graphs.DijkstraJob;
import graphs.WeightedGraph;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        WeightedGraph<Integer> graph = new WeightedGraph<>();

        // graph.addNode(0, 0);
        // graph.addNode(1, 0);
        // graph.addNode(2, 0);
        // graph.addNode(3, 0);
        // graph.addNode(4, 0);
        // graph.connectNodes(0, 1, 1);
        // graph.connectNodes(0, 2, 5);
        // graph.connectNodes(0, 3, 7);
        // graph.connectNodes(0, 4, 9);
        // graph.connectNodes(1, 2, 3);
        // graph.connectNodes(2, 3, 2);
        // graph.connectNodes(3, 4, 1);

        // graph.addNode(0, 0);
        // graph.addNode(1, 0);
        // graph.addNode(2, 0);
        // graph.addNode(3, 0);
        // graph.addNode(4, 0);
        // graph.addNode(5, 0);
        // graph.addNode(6, 0);
        // graph.connectNodes(0, 1, 3);
        // graph.connectNodes(0, 2, 2);
        // graph.connectNodes(1, 2, 2);
        // graph.connectNodes(1, 3, 1);
        // graph.connectNodes(1, 4, 4);
        // graph.connectNodes(2, 3, 3);
        // graph.connectNodes(2, 5, 6);
        // graph.connectNodes(2, 6, 5);
        // graph.connectNodes(3, 5, 2);
        // graph.connectNodes(4, 5, 1);
        // graph.connectNodes(5, 6, 2);

        // graph.addNode(0, 0);
        // graph.addNode(1, 0);
        // graph.addNode(2, 0);
        // graph.addNode(3, 0);
        // graph.addNode(4, 0);
        // graph.addNode(5, 0);
        // graph.addNode(6, 0);
        // graph.addNode(7, 0);
        // graph.addNode(8, 0);
        // graph.connectNodes(0, 1, 4);
        // graph.connectNodes(0, 7, 8);
        // graph.connectNodes(1, 7, 11);
        // graph.connectNodes(1, 2, 8);
        // graph.connectNodes(7, 8, 7);
        // graph.connectNodes(7, 6, 1);
        // graph.connectNodes(8, 2, 2);
        // graph.connectNodes(8, 6, 6);
        // graph.connectNodes(2, 3, 7);
        // graph.connectNodes(2, 5, 4);
        // graph.connectNodes(6, 5, 2);
        // graph.connectNodes(5, 3, 14);
        // graph.connectNodes(5, 4, 10);
        // graph.connectNodes(3, 4, 9);

        DijkstraJob shortestPathJob = new DijkstraJob(graph, 0);
        HashMap<Integer, Integer> pathTree = shortestPathJob.execute();
        System.out.println("Hello");
    }
}