package Server.Network;

import Common.Network.Send.Message;
import Common.Network.Send.Message.MessageType;
import Common.Network.Send.MessageTypes.JoinRequestMessage;
import Common.Network.Send.MessageTypes.StartGameMessage;
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

        while (!serverSocket.isClosed() && !ServerApp.gameStarted) {
            try {
                Socket socket = serverSocket.accept();

                if (ServerApp.playerUsernames.size() >= ServerApp.MAX_PLAYERS) {
                    System.out.println("Lobby full. Rejecting connection from " + socket.getInetAddress());
                    socket.close();
                    continue;
                }

                DataInputStream input = new DataInputStream(socket.getInputStream());
                String json = input.readUTF();
                Message message = JsonUtils.fromJson(json);

                if (message.getType() == MessageType.JOIN_REQUEST) {
                    JoinRequestMessage joinRequest = (JoinRequestMessage) JsonUtils.fromJson(json);

                    String username = joinRequest.getUsername();
                    String clientPassword = joinRequest.getPassword();

                    if (username == null || username.isEmpty()) {
                        System.out.println("Join failed: missing username.");
                        socket.close();
                        continue;
                    }

                    if (serverPassword != null &&
                        (clientPassword == null || !serverPassword.equals(clientPassword))) {
                        System.out.println("Join failed for user " + username + ": incorrect password.");
                        socket.close();
                        continue;
                    }

                    ServerApp.playerUsernames.add(username);
                    ServerApp.playerSockets.add(socket);
                    System.out.println("Player joined: " + username +
                        (ServerApp.playerUsernames.size() == 1 ? " (Admin)" : ""));

                } else if (message.getType() == MessageType.START_GAME) {
                    StartGameMessage start = (StartGameMessage) JsonUtils.fromJson(json);
                    String requester = start.getUsername();

                    if (!ServerApp.playerUsernames.isEmpty() &&
                        ServerApp.playerUsernames.get(0).equals(requester)) {

                        if (ServerApp.playerUsernames.size() == ServerApp.MAX_PLAYERS) {
                            System.out.println("Game is starting...");

                            ServerApp.gameStarted = true;

                            for (int i = 0; i < ServerApp.MAX_PLAYERS; i++) {
                                String username = ServerApp.playerUsernames.get(i);
                                Socket s = ServerApp.playerSockets.get(i);
                                ServerToClientConnection conn = new ServerToClientConnection(s, username);
                                conn.start();
                            }

                        } else {
                            System.out.println("Not enough players to start the game.");
                        }
                    } else {
                        System.out.println("Start request denied: only admin can start the game.");
                    }
                } else {
                    System.out.println("Invalid message type from " + socket.getInetAddress());
                    socket.close();
                }

            } catch (IOException e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }
}
