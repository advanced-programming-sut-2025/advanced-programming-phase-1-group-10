package Client.Network;

import Common.Models.Lobby;
import Common.Models.PlayerStuff.Player;
import Common.Network.ConnectionThread;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;

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
    private Runnable onGameStarted;

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

    public boolean connect(String host, int port, String username) {
        try {
            System.out.println("Connecting to " + host + ":" + port + " as " + username);
            Socket socket = new Socket(host, port);
            connection = new ClientToServerConnection(socket);
            this.username = username;
            connection.start();
            JoinRequestMessage joinRequest = new JoinRequestMessage(username, "");
            connection.sendMessage(joinRequest);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void requestLobbiesList() {
        if (connection != null) {
            ListLobbiesRequestMessage message = new ListLobbiesRequestMessage(username);
            connection.sendMessage(message);
        }
    }

    public void joinLobby(String lobbyId, String password) {
        if (connection != null) {
            JoinLobbyRequestMessage message = new JoinLobbyRequestMessage(
                lobbyId, username, password
            );
            connection.sendMessage(message);
        }
    }

    public void setPlayerReady(boolean isReady) {
        if (connection != null && currentLobbyId != null) {
            PlayerReadyMessage message = new PlayerReadyMessage(username, isReady);
            connection.sendMessage(message);
        }
    }

    public void leaveLobby() {
        if (connection != null && currentLobbyId != null) {
            LeavelobbyMessage message = new LeavelobbyMessage(username, currentLobbyId);
            connection.sendMessage(message);
            currentLobbyId = null;
        }
    }


    public void startGame() {
        if (connection != null && currentLobbyId != null && isAdmin) {
            StartGameMessage message = new StartGameMessage(username);
            connection.sendMessage(message);
        }
    }

    public boolean isInGame() {
        // if game started return true
        return false;
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


    // Getters and setters for callbacks
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

    public void setOnGameStarted(Runnable callback) {
        this.onGameStarted = callback;
    }

    // Inner class for client connection
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
                case LIST_LOBBIES_RESPONSE:
                    ListLobbiesResponseMessage listMsg = (ListLobbiesResponseMessage) message;
                    availableLobbies = listMsg.getLobbies();
                    if (onLobbiesListUpdated != null) {
                        onLobbiesListUpdated.accept(availableLobbies);
                    }
                    return true;

                case JOIN_LOBBY_RESPONSE:
                    JoinLobbyResponseMessage joinMsg = (JoinLobbyResponseMessage) message;
                    if (joinMsg.isSuccess()) {
                        currentLobbyId = joinMsg.getLobbyId();
                        lobbyPlayers = joinMsg.getPlayers();
                        isAdmin = joinMsg.isAdmin();
                    }

                    if (onLobbyJoined != null) {
                        onLobbyJoined.accept(joinMsg);
                    }
                    return true;

                case LOBBY_UPDATE:
                    LobbyUpdateMessage updateMsg = (LobbyUpdateMessage) message;
                    lobbyPlayers = updateMsg.getPlayers();
                    isAdmin = !lobbyPlayers.isEmpty() && lobbyPlayers.get(0).equals(username);

                    if (onLobbyUpdated != null) {
                        onLobbyUpdated.accept(updateMsg);
                    }
                    return true;

                case ERROR:
                    ErrorMessage errorMsg = (ErrorMessage) message;
                    if (onError != null) {
                        onError.accept(errorMsg.getErrorMessage());
                    }
                    return true;

                case START_GAME:
                    if (onGameStarted != null) {
                        onGameStarted.run();
                    }
                    return true;
                default:
                    System.out.println("Unhandled message type: " + message.getType());
                    return false;
            }
        }
    }

    public void createLobby(String lobbyName, boolean isPrivate, boolean isVisible, String password) {
        if (connection != null) {
            System.out.println("Sending CREATE_LOBBY message");
            CreateLobbyMessage message = new CreateLobbyMessage(
                lobbyName, isPrivate, isVisible, password, username
            );
            try{
                connection.sendMessage(message);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.err.println("Cannot create lobby: not connected to server");
            if (onError != null) {
                onError.accept("Not connected to server");
            }
        }
    }

    // Helper methods
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
