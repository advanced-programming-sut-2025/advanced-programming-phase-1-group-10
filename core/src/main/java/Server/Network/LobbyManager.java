package Server.Network;

import Common.Models.Lobby;
import Common.Models.PlayerStuff.Player;
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
                lobby.getPlayers().size(),
                lobby.isPrivate(),
                true
            ))
            .collect(Collectors.toList());
    }

    public Lobby getLobby(String lobbyId) {
        return lobbies.get(lobbyId);
    }

    public boolean addPlayerToLobby(String lobbyId, Player player, ServerToClientConnection connection) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }

        lobby.getPlayers().add(player);
        lobby.getPlayersReadyStatus().put(player, false);

        List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
        connections.add(connection);

        return true;
    }

    public void removePlayerFromLobby(String lobbyId, Player player, ServerToClientConnection connection) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.getPlayers().remove(player);
            lobby.getPlayersReadyStatus().remove(player);

            List<ServerToClientConnection> connections = lobbyConnections.get(lobbyId);
            connections.remove(connection);

            if (lobby.getPlayers().isEmpty()) {
                // If lobby is empty, remove it
                lobbies.remove(lobbyId);
                lobbyConnections.remove(lobbyId);
            }
        }
    }

    public void setPlayerReady(String lobbyId, Player player, boolean isReady) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.getPlayersReadyStatus().put(player, isReady);
        }
    }

    public boolean areAllPlayersReady(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            return !lobby.getPlayers().isEmpty() &&
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
