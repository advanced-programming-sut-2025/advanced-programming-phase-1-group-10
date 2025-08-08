package Server.Network;

import Common.Models.Lobby;
import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.LobbyMessages.ListLobbiesResponseMessage;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyManager {

    private static LobbyManager instance;
    private final Map<String, Lobby> lobbies;
    private final Map<String, List<ServerToClientConnection>> lobbyConnections;

    private LobbyManager() {
        lobbies = new HashMap<>();
        lobbyConnections = new HashMap<>();
    }

    /* ---------------------- Singleton ---------------------- */
    public static synchronized LobbyManager getInstance() {
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }

    /* ---------------------- Lobby Management ---------------------- */

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

    public Lobby getLobby(String lobbyId) {
        return lobbies.get(lobbyId);
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

    /* ---------------------- Player Management ---------------------- */

    public boolean addPlayerToLobby(String lobbyId, String username, ServerToClientConnection connection) {
        Lobby lobby = getLobby(lobbyId);
        if (lobby == null) return false;

        lobby.getPlayerNames().add(username);
        lobby.getPlayersReadyStatus().put(username, false);
        lobbyConnections.get(lobbyId).add(connection);
        return true;
    }

    public void removePlayerFromLobby(String lobbyId, String username, ServerToClientConnection connection) {
        Lobby lobby = getLobby(lobbyId);
        if (lobby == null) return;

        lobby.getPlayerNames().remove(username);
        lobby.getPlayersReadyStatus().remove(username);
        lobbyConnections.getOrDefault(lobbyId, Collections.emptyList()).remove(connection);

        if (lobby.getPlayerNames().isEmpty()) {
            lobbies.remove(lobbyId);
            lobbyConnections.remove(lobbyId);
        }
    }

    public void setPlayerReady(String lobbyId, String username, boolean isReady) {
        Lobby lobby = getLobby(lobbyId);
        if (lobby != null) {
            lobby.getPlayersReadyStatus().put(username, isReady);
        }
    }

    public boolean areAllPlayersReady(String lobbyId) {
        Lobby lobby = getLobby(lobbyId);
        return lobby != null &&
            !lobby.getPlayerNames().isEmpty() &&
            lobby.getPlayersReadyStatus().values().stream().allMatch(Boolean::booleanValue);
    }

    /* ---------------------- Communication ---------------------- */

    public void broadcastToLobby(String lobbyId, Message message) {
        List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
        if (connections != null) {
            connections.forEach(conn -> conn.sendMessage(message));
        }
    }

    public List<ServerToClientConnection> getLobbyConnections(String lobbyId) {
        return lobbyConnections.getOrDefault(lobbyId, Collections.emptyList());
    }
}
