import network.Network;
import network.Router;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Network network = new Network();
        ArrayList<Router> routers = new ArrayList<>();
        routers.add(new Router());
        routers.add(new Router());
        routers.add(new Router());
        routers.add(new Router());
        routers.add(new Router());

        network.addRouter(routers.get(0));
        network.addRouter(routers.get(1));
        network.addRouter(routers.get(2));
        network.addRouter(routers.get(3));
        network.addRouter(routers.get(4));

        network.connectRouters(routers.get(0), routers.get(1), 3);
        network.connectRouters(routers.get(0), routers.get(2), 1);

        network.connectRouters(routers.get(1), routers.get(2), 2);
        network.connectRouters(routers.get(1), routers.get(3), 2);

        network.connectRouters(routers.get(2), routers.get(3), 6);

        network.connectRouters(routers.get(4), routers.get(2), 4);
        network.connectRouters(routers.get(4), routers.get(3), 3);

        network.updateRoutingTables();
        System.out.println("Hello");
    }
}