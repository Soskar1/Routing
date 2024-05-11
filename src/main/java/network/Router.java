package network;

import java.util.HashMap;
import java.util.Stack;

public class Router {
    private HashMap<Integer, Integer> routingTable = new HashMap<>();
    private final String name;

    public Router(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void updateRoutingTable(HashMap<Integer, Integer> newRoutingTable) {
        routingTable = newRoutingTable;
    }

    public Stack<Integer> constructPath(Integer destination) {
        Stack<Integer> path = new Stack<>();
        path.add(destination);

        Integer tmp = destination;
        while (routingTable.get(tmp) != null) {
            tmp = routingTable.get(tmp);
            path.add(tmp);
        }

        return path;
    }
}
