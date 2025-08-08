package Client.Network;

import Common.Network.ConnectionThread;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;
import Common.Network.Send.MessageTypes.LobbyMessages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClientNetworkManager {

    private static ClientNetworkManager instance;
    private ClientToServerConnection connection;
    private String username;
    private String currentLobbyId;
    private boolean isAdmin;
    private List<ListLobbiesResponseMessage.LobbyInfo> availableLobbies;
    private ArrayList<String> lobbyPlayers;

    // Callbacks
    private Consumer<List<ListLobbiesResponseMessage.LobbyInfo>> onLobbiesListUpdated;
    private Consumer<JoinLobbyResponseMessage> onLobbyJoined;
    private Consumer<LobbyUpdateMessage> onLobbyUpdated;
    private Consumer<String> onError;
    private Consumer<StartGameMessage> onGameStarted;

    private ClientNetworkManager() {
        availableLobbies = new ArrayList<>();
        lobbyPlayers = new ArrayList<>();
    }

    public static synchronized ClientNetworkManager getInstance() {
        if (instance == null) {
            instance = new ClientNetworkManager();
        }
        return instance;
    }

    /* ---------------------- Connection Management ---------------------- */

    public boolean connect(String host, int port, String username) {
        try {
            System.out.println("Connecting to " + host + ":" + port + " as " + username);
            Socket socket = new Socket(host, port);
            connection = new ClientToServerConnection(socket);
            this.username = username;
            connection.start();
            sendMessage(new JoinRequestMessage(username, ""));
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        if (connection != null) {
            if (currentLobbyId != null) {
                leaveLobby();
                currentLobbyId = null;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            connection.end();
            connection = null;
        }
    }

    /* ---------------------- Sending Messages ---------------------- */

    public void sendMessage(Message message) {
        if (connection != null) {
            try {
                connection.sendMessage(message);
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
                if (onError != null) {
                    onError.accept("Failed to send message: " + e.getMessage());
                }
            }
        } else {
            System.err.println("Cannot send message: not connected to server");
            if (onError != null) {
                onError.accept("Not connected to server");
            }
        }
    }

    /* ---------------------- Lobby Actions ---------------------- */

    public void requestLobbiesList() {
        sendMessage(new ListLobbiesRequestMessage(username));
    }

    public void joinLobby(String lobbyId, String password) {
        sendMessage(new JoinLobbyRequestMessage(lobbyId, username, password));
    }

    public void createLobby(String lobbyName, boolean isPrivate, boolean isVisible, String password) {
        System.out.println("Sending CREATE_LOBBY message");
        sendMessage(new CreateLobbyMessage(lobbyName, isPrivate, isVisible, password, username));
    }

    public void setPlayerReady(boolean isReady) {
        if (currentLobbyId != null) {
            sendMessage(new PlayerReadyMessage(username, isReady));
        }
    }

    public void leaveLobby() {
        if (currentLobbyId != null) {
            sendMessage(new LeavelobbyMessage(username, currentLobbyId));
            currentLobbyId = null;
        }
    }

    public void sendFarmTypeUpdate(String farmType) {
        if (currentLobbyId != null) {
            sendMessage(new PlayerFarmTypeUpdateMessage(username, farmType));
        }
    }

    public void startGame() {
        if (currentLobbyId != null) {
            sendMessage(new StartGameMessage(username));
        }
    }

    public boolean isInGame() {
        // if game started return true
        return false;
    }

    /* ---------------------- Callback Setters ---------------------- */

    public void setOnLobbiesListUpdated(Consumer<List<ListLobbiesResponseMessage.LobbyInfo>> callback) {
        this.onLobbiesListUpdated = callback;
    }

    public void setOnLobbyJoined(Consumer<JoinLobbyResponseMessage> callback) {
        this.onLobbyJoined = callback;
    }

    public void setOnLobbyUpdated(Consumer<LobbyUpdateMessage> callback) {
        this.onLobbyUpdated = callback;
    }

    public void setOnError(Consumer<String> callback) {
        this.onError = callback;
    }

    public void setOnGameStarted(Consumer<StartGameMessage> callback) {
        this.onGameStarted = callback;
    }

    /* ---------------------- Inner Connection Class ---------------------- */

    private class ClientToServerConnection extends ConnectionThread {
        public ClientToServerConnection(Socket socket) throws IOException {
            super(socket);
        }

        @Override
        public boolean initialHandshake() {
            return true;
        }

        @Override
        protected boolean handleMessage(Message message) {
            System.out.println("Received message from server: " + message.getType());
            switch (message.getType()) {
                case LIST_LOBBIES_RESPONSE -> {
                    ListLobbiesResponseMessage listMsg = (ListLobbiesResponseMessage) message;
                    availableLobbies = listMsg.getLobbies();
                    if (onLobbiesListUpdated != null) onLobbiesListUpdated.accept(availableLobbies);
                }
                case JOIN_LOBBY_RESPONSE -> {
                    JoinLobbyResponseMessage joinMsg = (JoinLobbyResponseMessage) message;
                    if (joinMsg.isSuccess()) {
                        currentLobbyId = joinMsg.getLobbyId();
                        lobbyPlayers = joinMsg.getPlayers();
                        isAdmin = joinMsg.isAdmin();
                    }
                    if (onLobbyJoined != null) onLobbyJoined.accept(joinMsg);
                }
                case LOBBY_UPDATE -> {
                    LobbyUpdateMessage updateMsg = (LobbyUpdateMessage) message;
                    lobbyPlayers = updateMsg.getPlayers();
                    isAdmin = !lobbyPlayers.isEmpty() && lobbyPlayers.get(0).equals(username);
                    if (onLobbyUpdated != null) onLobbyUpdated.accept(updateMsg);
                }
                case ERROR -> {
                    ErrorMessage errorMsg = (ErrorMessage) message;
                    if (onError != null) onError.accept(errorMsg.getErrorMessage());
                }
                case START_GAME -> {
                    StartGameMessage startGameMsg = (StartGameMessage) message;
                    if (onGameStarted != null) onGameStarted.accept(startGameMsg);
                }
                default -> {
                    System.out.println("Unhandled message type: " + message.getType());
                    return false;
                }
            }
            return true;
        }
    }

    /* ---------------------- Getters ---------------------- */

    public String getUsername() {
        return username;
    }

    public String getCurrentLobbyId() {
        return currentLobbyId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<ListLobbiesResponseMessage.LobbyInfo> getAvailableLobbies() {
        return availableLobbies;
    }

    public ArrayList<String> getLobbyPlayers() {
        return lobbyPlayers;
    }
}
