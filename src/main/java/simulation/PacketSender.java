package simulation;

import network.Network;
import network.Packet;
import network.Router;

import java.util.List;

public class PacketSender implements Runnable {
    private final Network network;
    private final List<Router> routers;
    private final int sendingIntervalInMillis;

    private Thread thread;
    private boolean isRunning;
    private int sendPacketCount = 0;

    public PacketSender(Network network, int sendingIntervalInMillis) {
        this.network = network;
        routers = network.getRouters();
        this.sendingIntervalInMillis = sendingIntervalInMillis;
    }

    public void start() {
        thread = new Thread(this, "PacketSenderThread");
        thread.start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                Router srcRouter;
                Router dstRouter;

                do {
                    srcRouter = selectRandomRouter();
                    dstRouter = selectRandomRouter();
                } while (srcRouter == dstRouter);

                Packet packet = new Packet(srcRouter, dstRouter, "Packet_" + sendPacketCount);
                network.sendPacket(packet);
                ++sendPacketCount;

                Thread.sleep(sendingIntervalInMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Router selectRandomRouter() {
        int randomIndex = (int) (Math.random() * routers.size());
        return routers.get(randomIndex);
    }
}
