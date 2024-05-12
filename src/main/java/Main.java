import network.Network;
import network.Router;
import simulation.PacketSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        Network network = new Network();
        ArrayList<Router> routers = new ArrayList<>();
        routers.add(new Router("0"));
        routers.add(new Router("1"));
        routers.add(new Router("2"));
        routers.add(new Router("3"));
        routers.add(new Router("4"));

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

        PacketSender packetSender = new PacketSender(network, 2000);
        packetSender.start();

        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            input = reader.readLine();
            System.out.println("USER INPUT: " + input);
        } while (!Objects.equals(input, "stop"));

        packetSender.stop();
    }
}