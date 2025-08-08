package Server.Network;

import Common.Models.Lobby;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;
import Common.Utilis.JsonUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ListenerThread extends Thread {
    private final int port;
    private final String serverPassword;
    private final ServerSocket serverSocket;

    private final Map<String, Lobby> lobbies = new HashMap<>();
    private final Map<String, List<Socket>> lobbySockets = new HashMap<>();
    private final Map<Socket, String> socketToUsername = new HashMap<>();
    private final Map<String, String> userToLobbyId = new HashMap<>();

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
                System.out.println("New connection from: " + socket.getInetAddress());

                if (ServerApp.playerUsernames.size() >= ServerApp.MAX_PLAYERS) {
                    System.out.println("Lobby full. Rejecting connection from " + socket.getInetAddress());
                    socket.close();
                    continue;
                }

                new Thread(() -> {
                    try {
                        handleClientConnection(socket);
                    } catch (Exception e) {
                        System.err.println("Error handling client connection: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }

    private void handleClientConnection(Socket socket) {
        try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
            while (true) {
                try {
                    String json = input.readUTF();
                    System.out.println("Received JSON: " + json);

                    Message message = JsonUtils.fromJsonWithType(json);
                    System.out.println("Message type: " + message.getType());

                    switch (message.getType()) {
                        case JOIN_REQUEST:
                            handleJoinRequest(socket, (JoinRequestMessage) message);
                            break;

                        case CREATE_LOBBY:
                            handleCreateLobby(socket, (CreateLobbyMessage) message);
                            break;

                        case LIST_LOBBIES_REQUEST:
                            handleListLobbiesRequest(socket, (ListLobbiesRequestMessage) message);
                            break;

                        case JOIN_LOBBY_REQUEST:
                            handleJoinLobbyRequest(socket, (JoinLobbyRequestMessage) message);
                            break;

                        case PLAYER_READY:
                            handlePlayerReady(socket, (PlayerReadyMessage) message);
                            break;

                        case LEAVE_LOBBY:
                            handleLeaveLobby(socket, (LeavelobbyMessage) message);
                            break;

                        case START_GAME:
                            handleStartGame(socket, (StartGameMessage) message);
                            break;

                        case PLAYER_FARM_TYPE_UPDATE:
                            handlePlayerFarmTypeUpdate(socket, (PlayerFarmTypeUpdateMessage) message);
                            break;

                        default:
                            System.out.println("Invalid message type from " + socket.getInetAddress() + ": " + message.getType());
                            return;
                    }

                } catch (EOFException eof) {
                    System.out.println("Client disconnected: " + socket.getInetAddress());
                    break;
                } catch (Exception e) {
                    System.err.println("Error processing message: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from socket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleJoinRequest(Socket socket, JoinRequestMessage joinRequest) throws IOException {
        String username = joinRequest.getUsername();
        String clientPassword = joinRequest.getPassword();

        if (username == null || username.isEmpty()) {
            System.out.println("Join failed: missing username.");
            return;
        }

        if (serverPassword != null &&
            (clientPassword == null || !serverPassword.equals(clientPassword))) {
            System.out.println("Join failed for user " + username + ": incorrect password.");
            return;
        }

        ServerApp.playerUsernames.add(username);
        ServerApp.playerSockets.add(socket);
        socketToUsername.put(socket, username);

        System.out.println("Player joined: " + username +
            (ServerApp.playerUsernames.size() == 1 ? " (Admin)" : ""));
    }

    private void handleCreateLobby(Socket socket, CreateLobbyMessage createMsg) throws IOException {
        String username = createMsg.getCreatorUsername();

        if (!socketToUsername.containsKey(socket)) {
            socketToUsername.put(socket, username);
            ServerApp.playerUsernames.add(username);
            ServerApp.playerSockets.add(socket);
            System.out.println("New player registered: " + username);
        }

        String lobbyName = createMsg.getLobbyName();
        boolean isPrivate = createMsg.isPrivate();
        boolean isVisible = createMsg.isVisible();
        String password = createMsg.getPassword();

        String lobbyId = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lobbyId, lobbyName);
        lobby.setPrivate(isPrivate);
        lobby.setVisible(isVisible);
        if (isPrivate && password != null && !password.isEmpty()) {
            lobby.setPassword(password);
        }
        lobby.getPlayerFarmTypes().put(username, "1");

        lobbies.put(lobbyId, lobby);
        lobbySockets.put(lobbyId, new ArrayList<>());

        lobby.getPlayerNames().add(username);
        lobby.getPlayersReadyStatus().put(username, false);
        lobbySockets.get(lobbyId).add(socket);
        userToLobbyId.put(username, lobbyId);

        System.out.println("Lobby created: " + lobbyName + " (ID: " + lobbyId + ") by " + username);

        JoinLobbyResponseMessage response = new JoinLobbyResponseMessage(
            true, null, lobbyId, lobbyName, lobby.getPlayerNames(), true
        );

        try {
            sendMessage(socket, response);
            System.out.println("Sent JoinLobbyResponseMessage to client");
        } catch (Exception e) {
            System.err.println("Error sending response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket, Message message) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        String json = JsonUtils.toJson(message);
        System.out.println("Sending JSON: " + json);
        output.writeUTF(json);
        output.flush();
    }

    private void handleListLobbiesRequest(Socket socket, ListLobbiesRequestMessage listMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        List<ListLobbiesResponseMessage.LobbyInfo> lobbyInfoList = new ArrayList<>();

        for (Lobby lobby : lobbies.values()) {
            if (lobby.isVisible()) {
                lobbyInfoList.add(new ListLobbiesResponseMessage.LobbyInfo(
                    lobby.getLobbyId(),
                    lobby.getName(),
                    lobby.getPlayerNames().size(),
                    lobby.isPrivate(),
                    true
                ));
            }
        }

        System.out.println("Sending lobby list to " + username + ". Found " + lobbyInfoList.size() + " lobbies.");

        ListLobbiesResponseMessage response = new ListLobbiesResponseMessage(lobbyInfoList);
        sendMessage(socket, response);
    }

    private void handleJoinLobbyRequest(Socket socket, JoinLobbyRequestMessage joinMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        String lobbyId = joinMsg.getLobbyId();
        Lobby lobby = lobbies.get(lobbyId);

        if (lobby == null) {
            sendError(socket, "Lobby not found.");
            return;
        }

        if (lobby.isPrivate()) {
            String providedPassword = joinMsg.getPassword();
            if (providedPassword == null || !providedPassword.equals(lobby.getPassword())) {
                sendError(socket, "Incorrect password for this lobby.");
                return;
            }
        }

        lobby.getPlayerNames().add(username);
        lobby.getPlayersReadyStatus().put(username, false);
        lobby.getPlayerFarmTypes().put(username, "1");
        lobbySockets.get(lobbyId).add(socket);
        userToLobbyId.put(username, lobbyId);

        boolean isAdmin = lobby.getPlayerNames().size() == 1;
        System.out.println(username + " joined lobby: " + lobby.getName() + " (ID: " + lobbyId + ")");

        JoinLobbyResponseMessage response = new JoinLobbyResponseMessage(
            true, null, lobbyId, lobby.getName(), lobby.getPlayerNames(), isAdmin
        );
        sendMessage(socket, response);

        broadcastLobbyUpdate(lobbyId);
    }

    private void handlePlayerReady(Socket socket, PlayerReadyMessage readyMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        String lobbyId = userToLobbyId.get(username);

        if (lobbyId == null) {
            sendError(socket, "You are not in a lobby.");
            return;
        }

        Lobby lobby = lobbies.get(lobbyId);

        if (lobby.getPlayerNames().contains(username)) {
            lobby.getPlayersReadyStatus().put(username, readyMsg.isReady());
            System.out.println(username + " is now " + (readyMsg.isReady() ? "ready" : "not ready") + " in lobby: " + lobby.getName());

            broadcastLobbyUpdate(lobbyId);
        }
    }


    private void handlePlayerFarmTypeUpdate(Socket socket, PlayerFarmTypeUpdateMessage updateMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        String lobbyId = userToLobbyId.get(username);

        if (lobbyId == null) {
            sendError(socket, "You are not in a lobby.");
            return;
        }

        Lobby lobby = lobbies.get(lobbyId);

        if (lobby == null) {
            sendError(socket, "Lobby not found.");
            return;
        }


        if (!username.equals(updateMsg.getUsername())) {
            sendError(socket, "Unauthorized farm type update for another player.");
            return;
        }

        String newFarmType = updateMsg.getFarmType();

        if (lobby.getPlayerNames().contains(username)) {
            lobby.getPlayerFarmTypes().put(username, newFarmType);
            System.out.println(username + " changed farm type to: " + newFarmType + " in lobby: " + lobby.getName());
            broadcastLobbyUpdate(lobbyId);
        } else {
            sendError(socket, "Player not found in lobby.");
        }
    }

    private void handleLeaveLobby(Socket socket, LeavelobbyMessage leaveMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        String lobbyId = userToLobbyId.get(username);

        if (lobbyId == null) {
            sendError(socket, "You are not in a lobby.");
            return;
        }

        Lobby lobby = lobbies.get(lobbyId);

        if (lobby.getPlayerNames().contains(username)) {
            lobby.getPlayerNames().remove(username);
            lobby.getPlayersReadyStatus().remove(username);
            lobby.getPlayerFarmTypes().remove(username);
            lobbySockets.get(lobbyId).remove(socket);
            userToLobbyId.remove(username);

            System.out.println(username + " left lobby: " + lobby.getName());

            if (lobby.getPlayerNames().isEmpty()) {
                lobbies.remove(lobbyId);
                lobbySockets.remove(lobbyId);
                System.out.println("Lobby " + lobby.getName() + " removed (empty)");
            } else {
                broadcastLobbyUpdate(lobbyId);
            }
        }
    }

    private void handleStartGame(Socket socket, StartGameMessage startMsg) throws IOException {
        String username = socketToUsername.get(socket);

        if (username == null) {
            sendError(socket, "You need to join the server first.");
            return;
        }

        String lobbyId = userToLobbyId.get(username);

        if (lobbyId == null) {
            sendError(socket, "You are not in a lobby.");
            return;
        }

        Lobby lobby = lobbies.get(lobbyId);

        if (lobby.getPlayerNames().isEmpty() || !lobby.getPlayerNames().get(0).equals(username)) {
            sendError(socket, "Only the lobby admin can start the game.");
            return;
        }

        boolean allReady = true;
        for (Map.Entry<String, Boolean> entry : lobby.getPlayersReadyStatus().entrySet()) {
            if (!entry.getValue()) {
                allReady = false;
                break;
            }
        }

        if (!allReady) {
            sendError(socket, "Not all players are ready.");
            return;
        }

        if (lobby.getPlayerNames().size() < 2) {
            sendError(socket, "Need at least 2 players to start the game.");
            return;
        }

        System.out.println("Game is starting in lobby: " + lobby.getName());


        startMsg.setWorldSeed(ThreadLocalRandom.current().nextLong());
        startMsg.setPlayerNames(lobby.getPlayerNames());

        for (Socket playerSocket : lobbySockets.get(lobbyId)) {
            sendMessage(playerSocket, startMsg);
        }

    }

    private void broadcastLobbyUpdate(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);

        if (lobby != null) {
            LobbyUpdateMessage updateMsg = new LobbyUpdateMessage(
                lobbyId, lobby.getPlayerNames(), lobby.getPlayersReadyStatus()
            );

            updateMsg.setFarmTypes(lobby.getPlayerFarmTypes());

            for (Socket playerSocket : lobbySockets.get(lobbyId)) {
                try {
                    sendMessage(playerSocket, updateMsg);
                } catch (IOException e) {
                    System.err.println("Error broadcasting lobby update: " + e.getMessage());
                }
            }
        }
    }

    private void sendError(Socket socket, String errorMessage) throws IOException {
        ErrorMessage error = new ErrorMessage(errorMessage);
        sendMessage(socket, error);
    }
}
