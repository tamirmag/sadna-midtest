package ObservableLayer;


public class ObservablePlayer {
    private String ipAddress;
    private int port;
    private String name;


    public ObservablePlayer(String ipAddress, int port, String name) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }


}
