package network;

import graphs.DijkstraJob;
import graphs.WeightedGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

public class Network implements Runnable {
    private final WeightedGraph<Router> graph;
    private final ArrayList<Router> routers;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private Thread networkThread;
    private boolean isRunning = false;
    private final int routingTableUpdateIntervalInMillis;

    private static Logger logger = LogManager.getLogger(Network.class);

    public Network(int routingTableUpdateIntervalInMillis) {
        graph = new WeightedGraph<>();
        routers = new ArrayList<>();
        this.routingTableUpdateIntervalInMillis = routingTableUpdateIntervalInMillis;
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

    public void start() {
        networkThread = new Thread(this);
        networkThread.start();
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                updateRoutingTables();
                Thread.sleep(routingTableUpdateIntervalInMillis);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        isRunning = false;
        threadPool.shutdownNow();
        networkThread.interrupt();
    }

    private void updateRoutingTables() throws InterruptedException, ExecutionException {
        logger.info("Updating routing tables");

        Set<Integer> nodeIDs = graph.getNodes();
        List<Callable<DijkstraJob>> todo = new ArrayList<>();

        for (Integer nodeID : nodeIDs) {
            todo.add(new DijkstraJob(graph, nodeID));
        }

        List<Future<DijkstraJob>> jobs = threadPool.invokeAll(todo);

        for (var job : jobs) {
            DijkstraJob dijkstraJob = job.get();
            int nodeID = dijkstraJob.getStartNodeID();
            graph.getNode(nodeID).getValue().updateRoutingTable(dijkstraJob.getPath());
        }
    }

    public int sendPacket(Packet packet) {
        Router source = packet.getSource();
        Router destination = packet.getDestination();

        if (source == destination) {
            return 0;
        }

        logger.info("Sending packet {} from router {} to router {}", packet.getID(), source.getName(), destination.getName());

        Stack<Integer> path = source.constructPath(destination.hashCode());
        if (path.size() == 1) {
            logger.warn("Unable to construct a path for packet {} from router {} to router {}. Aborting",
                    packet.getID(), source.getName(), destination.getName());
            return -1;
        }

        StringBuilder pathLog = new StringBuilder();

        Integer currentRouterID = path.pop();
        Integer nextRouterID = path.peek();
        Router currentRouter;
        Router nextRouter;

        while (!path.isEmpty()) {
            currentRouter = graph.getNode(currentRouterID).getValue();
            nextRouter = graph.getNode(nextRouterID).getValue();

            pathLog.append(currentRouter.getName());
            if (nextRouterID != null) {
                pathLog.append(" -> ");
            }

            logger.debug("Packet {} is now in router {}", packet.getID(), currentRouter.getName());

            if (!graph.checkConnection(currentRouterID, nextRouterID)) {
                logger.warn("Can't send packet from router {} to router {}", currentRouter.getName(), nextRouter.getName());
                return -1;
            }

            currentRouterID = path.pop();

            if (!path.isEmpty()) {
                nextRouterID = path.peek();
            }
        }

        currentRouter = graph.getNode(currentRouterID).getValue();
        pathLog.append(currentRouter.getName());

        logger.info("Packet {} reached the router {}. Path: {}", packet.getID(), currentRouter.getName(), pathLog);
        return 1;
    }
}
