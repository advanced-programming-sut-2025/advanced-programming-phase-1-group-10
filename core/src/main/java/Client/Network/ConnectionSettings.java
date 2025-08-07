package Client.Network;

public class ConnectionSettings {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 12345;

    private static String host = DEFAULT_HOST;
    private static int port = DEFAULT_PORT;

    public static String getHost() {
        return host;
    }

    public static void setHost(String newHost) {
        host = newHost;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int newPort) {
        port = newPort;
    }

    public static void resetToDefaults() {
        host = DEFAULT_HOST;
        port = DEFAULT_PORT;
    }
}
