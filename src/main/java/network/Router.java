package network;

import java.util.HashMap;

public class Router {
    private HashMap<Integer, Integer> routingTable = new HashMap<>();

    public void updateRoutingTable(HashMap<Integer, Integer> newRoutingTable) {
        routingTable = newRoutingTable;
    }
}
