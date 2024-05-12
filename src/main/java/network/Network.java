package network;

import graphs.DijkstraJob;
import graphs.WeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

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

    public ArrayList<Router> getRouters() {
        return routers;
    }

    public void connectRouters(Router firstRouter, Router secondRouter, int connectionCost) {
        if (routers.contains(firstRouter) && routers.contains(secondRouter)) {
            graph.connectNodes(firstRouter.hashCode(), secondRouter.hashCode(), connectionCost);
        }
    }

    public void updateRoutingTables() {
        Set<Integer> nodeIDs = graph.getNodes();

        for (Integer nodeID : nodeIDs) {
            DijkstraJob job = new DijkstraJob(graph, nodeID);
            HashMap<Integer, Integer> pathTree = job.execute();

            graph.getNode(nodeID).getValue().updateRoutingTable(pathTree);
        }
    }

    public void sendPacket(Packet packet) {
        Router source = packet.getSource();
        Router destination = packet.getDestination();

        if (source == destination) {
            return;
        }

        System.out.println("Sending packet from router " + source.getName() +
                " to router " + destination.getName());

        Stack<Integer> path = source.constructPath(destination.hashCode());
        if (path.size() == 1) {
            return;
        }

        Integer currentRouterID = path.pop();
        Integer nextRouterID = path.peek();
        Router currentRouter;
        Router nextRouter;
        while (!path.isEmpty()) {
            currentRouter = graph.getNode(currentRouterID).getValue();
            nextRouter = graph.getNode(nextRouterID).getValue();

            System.out.println("Packet " + packet.getID() + " is now in router " + currentRouter.getName());

            if (!graph.checkConnection(currentRouterID, nextRouterID)) {
                System.out.println("Can't send packet from router " + currentRouter.getName() +
                        " to router " + nextRouter.getName());

                return;
            }

            currentRouterID = path.pop();

            if (!path.isEmpty()) {
                nextRouterID = path.peek();
            }
        }

        currentRouter = graph.getNode(currentRouterID).getValue();
        System.out.println("Packet " + packet.getID() + " reached the router " + currentRouter.getName());
    }
}
