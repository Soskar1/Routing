package network;

import graphs.WeightedGraph;

import java.util.ArrayList;

public class Network {
    private final WeightedGraph<Router> graph;
    private final ArrayList<Router> routers;

    public Network() {
        graph = new WeightedGraph<>();
        routers = new ArrayList<>();
    }

    public void addRouter(Router router) {
        if (!routers.contains(router)) {
            graph.addNode(router.hashCode(), router);
            routers.add(router);
        }
    }

    public void removeRouter(Router router) {
        if (routers.contains(router)) {
            graph.removeNode(router.hashCode());
            routers.remove(router);
        }
    }

    public void connectRouters(Router firstRouter, Router secondRouter, int connectionCost) {
        if (routers.contains(firstRouter) && routers.contains(secondRouter)) {
            graph.connectNodes(firstRouter.hashCode(), secondRouter.hashCode(), connectionCost);
        }
    }

    public void updateRoutingTables() {

    }


}
