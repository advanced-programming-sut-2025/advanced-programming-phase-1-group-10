package Server;

import Server.Network.ListenerThread;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java Main <port> [password]");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number.");
            return;
        }

        String serverPassword = args.length == 2 ? args[1] : null;

        try {
            ListenerThread listener = new ListenerThread(port, serverPassword);
            listener.start();
        } catch (Exception e) {
            System.err.println("Failed to start listener: " + e.getMessage());
        }
    }
}
