package Server.Network;

import Common.Models.Lobby;
import Common.Network.Send.MessageTypes.ListLobbiesResponseMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LobbyManager {
    private static LobbyManager instance;
    private final Map<String, Lobby> lobbies;
    private final Map<String, List<ServerToClientConnection>> lobbyConnections;

    private LobbyManager() {
        lobbies = new HashMap<>();
        lobbyConnections = new HashMap<>();
    }

    public static synchronized LobbyManager getInstance() {
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }

    public Lobby createLobby(String name, boolean isPrivate, boolean isVisible, String password) {
        String lobbyId = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lobbyId, name);
        lobby.setPrivate(isPrivate);
        lobby.setVisible(isVisible);
        if (isPrivate && password != null) {
            lobby.setPassword(password);
        }

        lobbies.put(lobbyId, lobby);
        lobbyConnections.put(lobbyId, new ArrayList<>());

        return lobby;
    }

    public List<ListLobbiesResponseMessage.LobbyInfo> getVisibleLobbies() {
        return lobbies.values().stream()
            .filter(Lobby::isVisible)
            .map(lobby -> new ListLobbiesResponseMessage.LobbyInfo(
                lobby.getLobbyId(),
                lobby.getName(),
                lobby.getPlayerNames().size(),
                lobby.isPrivate(),
                true
            ))
            .collect(Collectors.toList());
    }

    public Lobby getLobby(String lobbyId) {
        return lobbies.get(lobbyId);
    }


    public boolean addPlayerToLobby(String lobbyId, String username, ServerToClientConnection connection) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }

        lobby.getPlayerNames().add(username);
        lobby.getPlayersReadyStatus().put(username, false);

        List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
        connections.add(connection);

        return true;
    }


    public void removePlayerFromLobby(String lobbyId, String username, ServerToClientConnection connection) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.getPlayerNames().remove(username);
            lobby.getPlayersReadyStatus().remove(username);

            List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
            connections.remove(connection);

            if (lobby.getPlayerNames().isEmpty()) {

                lobbies.remove(lobbyId);
                lobbyConnections.remove(lobbyId);
            }
        }
    }


    public void setPlayerReady(String lobbyId, String username, boolean isReady) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.getPlayersReadyStatus().put(username, isReady);
        }
    }

    public boolean areAllPlayersReady(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            return !lobby.getPlayerNames().isEmpty() &&
                lobby.getPlayersReadyStatus().values().stream().allMatch(ready -> ready);
        }
        return false;
    }

    public void broadcastToLobby(String lobbyId, Common.Network.Send.Message message) {
        List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
        if (connections != null) {
            for (ServerToClientConnection connection : connections) {
                connection.sendMessage(message);
            }
        }
    }
}
