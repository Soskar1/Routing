import graphs.WeightedGraph;

public class Main {
    public static void main(String[] args) {

        WeightedGraph graph = new WeightedGraph();

        graph.addNode(4);
        graph.addNode(2);
        graph.addNode(1);
        graph.addNode(59);
        graph.addNode(35);

        graph.connectNodes(4, 2, 10);
        graph.connectNodes(59, 35, 1);
        graph.connectNodes(1, 4, 5);
        graph.connectNodes(35, 4, 2);
        graph.connectNodes(59, 1, 2);

        graph.removeNode(0);
        graph.removeNode(2);
        graph.removeConnection(59, 1);

        graph.updateWeight(1, 4, 222);
    }
}