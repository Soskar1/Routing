import graphs.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ArrayList<List<Integer>> adjacencyMatrix = new ArrayList<>();
        adjacencyMatrix.add(Arrays.asList(0, 0, 7));
        adjacencyMatrix.add(Arrays.asList(0, 0, 8));
        adjacencyMatrix.add(Arrays.asList(7, 8, 0));

        WeightedGraph graph = new WeightedGraph(adjacencyMatrix);
        System.out.println("Hello");
    }
}