package Server.Network;

import Common.Network.Send.Message;
import Common.Network.Send.Message.MessageType;
import Common.Network.Send.MessageTypes.JoinRequestMessage;
import Common.Utilis.JsonUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
    private final int port;
    private final String serverPassword;
    private final ServerSocket serverSocket;

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
                Socket socket = serverSocket.accept();
                System.out.println("Incoming connection from " + socket.getInetAddress());

                DataInputStream input = new DataInputStream(socket.getInputStream());
                String json = input.readUTF();

                Message message = JsonUtils.fromJson(json);

                if (message.getType() == MessageType.JOIN_REQUEST) {
                    JoinRequestMessage joinRequest = (JoinRequestMessage) JsonUtils.fromJson(json);

                    String username = joinRequest.getUsername();
                    String clientPassword = joinRequest.getPassword();

                    // Validate username
                    if (username == null || username.isEmpty()) {
                        System.out.println("Join failed: username is missing.");
                        socket.close();
                        continue;
                    }

                    // Validate password if server requires one
                    if (serverPassword != null && (clientPassword == null || !serverPassword.equals(clientPassword))) {
                        System.out.println("Join failed for user " + username + ": incorrect password.");
                        socket.close();
                        continue;
                    }

                    // Accept the connection
                    System.out.println("Join accepted: " + username);
                    ServerToClientConnection connection = new ServerToClientConnection(socket, username);
                    connection.start();

                } else {
                    System.out.println("Invalid message type. Connection closed.");
                    socket.close();
                }

            } catch (IOException e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }
}
