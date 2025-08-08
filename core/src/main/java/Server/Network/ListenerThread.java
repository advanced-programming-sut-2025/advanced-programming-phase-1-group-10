package Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
    private final int port;
    private final String serverPassword;
    private ServerSocket serverSocket;

    public ListenerThread(int port, String password) throws IOException {
        this.port = port;
        this.serverPassword = (password != null && !password.isEmpty()) ? password : null;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Lobby Listener started on port " + port +
            (serverPassword != null ? " (password protected)" : ""));

        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                String clientId = generateClientId();
                ServerToClientConnection connection = new ServerToClientConnection(clientSocket, clientId, serverPassword);
                connection.start();

            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    private String generateClientId() {
        return "Client - " + System.currentTimeMillis();
    }
}
