package Server.Network;

import Common.Models.Lobby;
import Common.Models.PlayerStuff.Player;
import Common.Network.ConnectionThread;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerToClientConnection extends ConnectionThread {

    private final String clientId;
    private Player player;
    private String currentLobbyId;

    public ServerToClientConnection(Socket clientSocket, String clientId) throws IOException {
        super(clientSocket);
        this.clientId = clientId;
        this.player = new Player(clientId,10);
        this.setOtherSideIP(clientSocket.getInetAddress().getHostAddress());
        this.setOtherSidePort(clientSocket.getPort());
    }

    @Override
    public boolean initialHandshake() {
        System.out.println("Handshake started with client: " + clientId);
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        LobbyManager lobbyManager = LobbyManager.getInstance();

        switch (message.getType()) {
            case CREATE_LOBBY:
                CreateLobbyMessage createMsg = (CreateLobbyMessage) message;
                Lobby newLobby = lobbyManager.createLobby(
                    createMsg.getLobbyName(),
                    createMsg.isPrivate(),
                    createMsg.isVisible(),
                    createMsg.getPassword()
                );

                // Add creator to the lobby
//                player = new Player(createMsg.getCreatorUsername(),10);
//                lobbyManager.addPlayerToLobby(newLobby.getLobbyId(), player, this);
                currentLobbyId = newLobby.getLobbyId();

                // Send response to client
                JoinLobbyResponseMessage response = new JoinLobbyResponseMessage(
                    true, null, newLobby.getLobbyId(), newLobby.getName(),
                    newLobby.getPlayers(), true
                );
                sendMessage(response);
                return true;

            case LIST_LOBBIES_REQUEST:
                ListLobbiesRequestMessage listMsg = (ListLobbiesRequestMessage) message;
                ListLobbiesResponseMessage listResponse = new ListLobbiesResponseMessage(
                    lobbyManager.getVisibleLobbies()
                );
                sendMessage(listResponse);
                return true;

            case JOIN_LOBBY_REQUEST:
                JoinLobbyRequestMessage joinMsg = (JoinLobbyRequestMessage) message;
                Lobby targetLobby = lobbyManager.getLobby(joinMsg.getLobbyId());

                if (targetLobby == null) {
                    sendMessage(new JoinLobbyResponseMessage(
                        false, "Lobby not found", null, null, null, false
                    ));
                    return true;
                }

                if (targetLobby.isPrivate() &&
                    (joinMsg.getPassword() == null ||
                        !joinMsg.getPassword().equals(targetLobby.getPassword()))) {
                    sendMessage(new JoinLobbyResponseMessage(
                        false, "Incorrect password", null, null, null, false
                    ));
                    return true;
                }

                player = new Player(joinMsg.getUsername(),10);
                boolean isAdmin = targetLobby.getPlayers().isEmpty();
                lobbyManager.addPlayerToLobby(targetLobby.getLobbyId(), player, this);
                currentLobbyId = targetLobby.getLobbyId();

                // Send response to the joining player
                JoinLobbyResponseMessage joinResponse = new JoinLobbyResponseMessage(
                    true, null, targetLobby.getLobbyId(), targetLobby.getName(),
                    targetLobby.getPlayers(), isAdmin
                );
                sendMessage(joinResponse);

                // Notify other players in the lobby about the new player
                LobbyUpdateMessage updateMessage = new LobbyUpdateMessage(
                    targetLobby.getLobbyId(), targetLobby.getPlayers(), targetLobby.getPlayersReadyStatus()
                );
                lobbyManager.broadcastToLobby(targetLobby.getLobbyId(), updateMessage);
                return true;

            case PLAYER_READY:
                PlayerReadyMessage readyMsg = (PlayerReadyMessage) message;
                if (currentLobbyId != null) {
                    lobbyManager.setPlayerReady(currentLobbyId, player, readyMsg.isReady());

                    // Notify all players about the ready status change
                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);
                    LobbyUpdateMessage readyUpdateMsg = new LobbyUpdateMessage(
                        currentLobbyId, lobby.getPlayers(), lobby.getPlayersReadyStatus()
                    );
                    lobbyManager.broadcastToLobby(currentLobbyId, readyUpdateMsg);
                }
                return true;

            case LEAVE_LOBBY:
                if (currentLobbyId != null) {
                    lobbyManager.removePlayerFromLobby(currentLobbyId, player, this);

                    // Notify remaining players
                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);
                    if (lobby != null) {
                        LobbyUpdateMessage leaveUpdateMsg = new LobbyUpdateMessage(
                            currentLobbyId, lobby.getPlayers(), lobby.getPlayersReadyStatus()
                        );
                        lobbyManager.broadcastToLobby(currentLobbyId, leaveUpdateMsg);
                    }

                    currentLobbyId = null;
                }
                return true;

            case START_GAME:
                StartGameMessage startMsg = (StartGameMessage) message;
                if (currentLobbyId != null) {
                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);

                    // Check if sender is admin (first player)
                    if (!lobby.getPlayers().isEmpty() &&
                        lobby.getPlayers().get(0).getName().equals(player.getName())) {

                        // Check if all players are ready
                        if (lobbyManager.areAllPlayersReady(currentLobbyId)) {
                            // Start game
                            lobbyManager.broadcastToLobby(currentLobbyId, message);
                            return true;
                        } else {
                            // Send error - not all players ready
                            ErrorMessage errorMsg = new ErrorMessage(
                                "Not all players are ready to start the game."
                            );
                            sendMessage(errorMsg);
                            return true;
                        }
                    } else {
                        // Send error - not admin
                        ErrorMessage errorMsg = new ErrorMessage(
                            "Only the lobby admin can start the game."
                        );
                        sendMessage(errorMsg);
                        return true;
                    }
                }
                return true;
        }

        return false;
    }

    public String getClientId() {
        return clientId;
    }

    public Player getPlayer() {
        return player;
    }
}
