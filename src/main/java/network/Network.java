package network;

import graphs.DijkstraJob;
import graphs.WeightedGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Network {
    private final WeightedGraph<Router> graph;
    private final ArrayList<Router> routers;
    private static Logger logger = LogManager.getLogger(Network.class);

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

        logger.info("Sending packet from router {} to router {}", source.getName(), destination.getName());

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

            logger.debug("Packet {} is now in router {}", packet.getID(), currentRouter.getName());

            if (!graph.checkConnection(currentRouterID, nextRouterID)) {
                logger.warn("Can't send packet from router {} to router {}", currentRouter.getName(), nextRouter.getName());
                return;
            }

            currentRouterID = path.pop();

            if (!path.isEmpty()) {
                nextRouterID = path.peek();
            }
        }

        currentRouter = graph.getNode(currentRouterID).getValue();
        logger.info("Packet {} reached the router {}", packet.getID(), currentRouter.getName());
    }
}
