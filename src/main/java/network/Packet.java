package network;

public class Packet {
    private final Router source;
    private final Router destination;
    private final String ID;

    public Packet(Router source, Router destination, String ID) {
        this.source = source;
        this.destination = destination;
        this.ID = ID;
    }

    public Router getSource() {
        return source;
    }

    public Router getDestination() {
        return destination;
    }

    public String getID() {
        return ID;
    }
}
