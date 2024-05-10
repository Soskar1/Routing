package graphs;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph {
    private final ArrayList<Node> nodes = new ArrayList<>();

    public WeightedGraph(List<List<Integer>> adjacencyMatrix) {
        constructGraph(adjacencyMatrix);
    }

    private void constructGraph(List<List<Integer>> adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.size(); ++i) {
            Node node = new Node(i);
            nodes.add(node);
        }

        for (int i = 0; i < adjacencyMatrix.size(); ++i) {
            for (int j = 0; j < adjacencyMatrix.get(i).size(); ++j) {
                int weight = adjacencyMatrix.get(i).get(j);

                if (weight == 0) {
                    continue;
                }

                Node tmp1 = nodes.get(i);
                Node tmp2 = nodes.get(j);

                tmp1.addAdjacentNode(tmp2, weight);
                tmp2.addAdjacentNode(tmp1, weight);
            }
        }
    }
}
