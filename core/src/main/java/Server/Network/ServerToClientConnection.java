package Server.Network;

import Common.Models.Lobby;
import Common.Models.PlayerStuff.Player;
import Common.Network.ConnectionThread;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.*;
import Client.Views.MainMenuView;
import Server.Main;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerToClientConnection extends ConnectionThread {

    private final String clientId;
    private String username;
    private String currentLobbyId;

    public ServerToClientConnection(Socket clientSocket, String clientId) throws IOException {
        super(clientSocket);
        this.clientId = clientId;
        this.username = clientId;
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

                this.username = createMsg.getCreatorUsername();

                Lobby newLobby = lobbyManager.createLobby(
                    createMsg.getLobbyName(),
                    createMsg.isPrivate(),
                    createMsg.isVisible(),
                    createMsg.getPassword()
                );

                lobbyManager.addPlayerToLobby(newLobby.getLobbyId(), username, this);
                currentLobbyId = newLobby.getLobbyId();

                JoinLobbyResponseMessage response = new JoinLobbyResponseMessage(
                    true, null, newLobby.getLobbyId(), newLobby.getName(),
                    newLobby.getPlayerNames(), true
                );
                sendMessage(response);
                return true;

            case LIST_LOBBIES_REQUEST:
                ListLobbiesRequestMessage listMsg = (ListLobbiesRequestMessage) message;

                if (this.username == null || this.username.equals(clientId)) {
                    this.username = listMsg.getUsername();
                }

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

                this.username = joinMsg.getUsername();

                boolean isAdmin = targetLobby.getPlayerNames().isEmpty();
                lobbyManager.addPlayerToLobby(targetLobby.getLobbyId(), username, this);
                currentLobbyId = targetLobby.getLobbyId();

                JoinLobbyResponseMessage joinResponse = new JoinLobbyResponseMessage(
                    true, null, targetLobby.getLobbyId(), targetLobby.getName(),
                    targetLobby.getPlayerNames(), isAdmin
                );
                sendMessage(joinResponse);


                LobbyUpdateMessage updateMessage = new LobbyUpdateMessage(
                    targetLobby.getLobbyId(), targetLobby.getPlayerNames(), targetLobby.getPlayersReadyStatus()
                );
                updateMessage.setFarmTypes(targetLobby.getPlayerFarmTypes());
                lobbyManager.broadcastToLobby(targetLobby.getLobbyId(), updateMessage);
                return true;

            case PLAYER_READY:
                PlayerReadyMessage readyMsg = (PlayerReadyMessage) message;
                if (currentLobbyId != null) {
                    lobbyManager.setPlayerReady(currentLobbyId, username, readyMsg.isReady());

                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);
                    LobbyUpdateMessage readyUpdateMsg = new LobbyUpdateMessage(
                        currentLobbyId, lobby.getPlayerNames(), lobby.getPlayersReadyStatus()
                    );
                    readyUpdateMsg.setFarmTypes(lobby.getPlayerFarmTypes());
                    lobbyManager.broadcastToLobby(currentLobbyId, readyUpdateMsg);
                }
                return true;

            case PLAYER_FARM_TYPE_UPDATE:
                PlayerFarmTypeUpdateMessage farmTypeUpdateMsg = (PlayerFarmTypeUpdateMessage) message;
                if (currentLobbyId != null) {

                    if (!username.equals(farmTypeUpdateMsg.getUsername())) {
                        ErrorMessage errorMsg = new ErrorMessage("Unauthorized farm type update for another player.");
                        sendMessage(errorMsg);
                        return true;
                    }

                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);
                    if (lobby != null) {
                        lobby.getPlayerFarmTypes().put(username, farmTypeUpdateMsg.getFarmType());
                        System.out.println(username + " changed farm type to: " + farmTypeUpdateMsg.getFarmType() + " in lobby: " + lobby.getName());


                        LobbyUpdateMessage updateMsg = new LobbyUpdateMessage(
                            currentLobbyId, lobby.getPlayerNames(), lobby.getPlayersReadyStatus()
                        );
                        updateMsg.setFarmTypes(lobby.getPlayerFarmTypes());
                        lobbyManager.broadcastToLobby(currentLobbyId, updateMsg);
                    }
                }
                return true;

            case LEAVE_LOBBY:
                if (currentLobbyId != null) {
                    lobbyManager.removePlayerFromLobby(currentLobbyId, username, this);

                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);
                    if (lobby != null) {
                        LobbyUpdateMessage leaveUpdateMsg = new LobbyUpdateMessage(
                            currentLobbyId, lobby.getPlayerNames(), lobby.getPlayersReadyStatus()
                        );
                        leaveUpdateMsg.setFarmTypes(lobby.getPlayerFarmTypes());
                        lobbyManager.broadcastToLobby(currentLobbyId, leaveUpdateMsg);
                    }

                    currentLobbyId = null;
                }
                return true;

            case START_GAME:
                StartGameMessage startMsg = (StartGameMessage) message;
                if (currentLobbyId != null) {
                    Lobby lobby = lobbyManager.getLobby(currentLobbyId);

                    if (!lobby.getPlayerNames().isEmpty() &&
                        lobby.getPlayerNames().get(0).equals(username)) {

                        if (lobbyManager.areAllPlayersReady(currentLobbyId)) {
                            lobbyManager.broadcastToLobby(currentLobbyId, message);
                            return true;
                        } else {
                            ErrorMessage errorMsg = new ErrorMessage(
                                "Not all players are ready to start the game."
                            );
                            sendMessage(errorMsg);
                            return true;
                        }
                    } else {
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

    @Override
    protected void onConnectionClosed() {
        System.out.println("Connection closed for client: " + username + " (" + clientId + ")");

        if (currentLobbyId != null) {
            LobbyManager lobbyManager = LobbyManager.getInstance();


            lobbyManager.removePlayerFromLobby(currentLobbyId, username, this);

            Lobby lobby = lobbyManager.getLobby(currentLobbyId);
            if (lobby != null) {

                LobbyUpdateMessage leaveUpdateMsg = new LobbyUpdateMessage(
                    currentLobbyId, lobby.getPlayerNames(), lobby.getPlayersReadyStatus()
                );
                leaveUpdateMsg.setFarmTypes(lobby.getPlayerFarmTypes());
                lobbyManager.broadcastToLobby(currentLobbyId, leaveUpdateMsg);
            }



            currentLobbyId = null;
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }
}
