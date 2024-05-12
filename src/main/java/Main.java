import network.Network;
import network.Router;
import simulation.PacketSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        Network network = new Network(15000);
        ArrayList<Router> routers = new ArrayList<>();
        routers.add(new Router("0"));
        routers.add(new Router("1"));
        routers.add(new Router("2"));
        routers.add(new Router("3"));
        routers.add(new Router("4"));
        routers.add(new Router("5"));
        routers.add(new Router("6"));
        routers.add(new Router("7"));
        routers.add(new Router("8"));

        network.addRouters(routers);

        network.connectRouters(routers.get(0), routers.get(1), 3);
        network.connectRouters(routers.get(0), routers.get(2), 1);
        network.connectRouters(routers.get(1), routers.get(2), 2);
        network.connectRouters(routers.get(1), routers.get(3), 2);
        network.connectRouters(routers.get(2), routers.get(3), 6);
        network.connectRouters(routers.get(4), routers.get(2), 4);
        network.connectRouters(routers.get(4), routers.get(3), 3);
        network.connectRouters(routers.get(0), routers.get(5), 2);
        network.connectRouters(routers.get(0), routers.get(8), 3);
        network.connectRouters(routers.get(5), routers.get(8), 2);
        network.connectRouters(routers.get(5), routers.get(6), 4);
        network.connectRouters(routers.get(6), routers.get(8), 1);
        network.connectRouters(routers.get(6), routers.get(7), 4);
        network.connectRouters(routers.get(7), routers.get(8), 2);

        network.start();

        PacketSender packetSender = new PacketSender(network, 2000, 2);
        packetSender.start();

        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            input = reader.readLine();

            Iterator<Router> it = routers.iterator();
            while (it.hasNext()) {
                Router router = it.next();

                if (Objects.equals(router.getName(), input)) {
                    network.removeRouter(router);
                    it.remove();
                    break;
                }
            }

        } while (!Objects.equals(input, "stop"));

        packetSender.stop();
        network.stop();
    }
}