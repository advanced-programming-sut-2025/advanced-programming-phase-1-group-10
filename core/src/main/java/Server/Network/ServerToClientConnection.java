package Server.Network;

import Common.Models.Lobby;
import Common.Network.ConnectionThread;
import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.*;
import Common.Network.Messages.MessageTypes.LobbyMessages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ServerToClientConnection extends ConnectionThread {

    private final String clientId;
    private String username;
    private String currentLobbyId;
    private final String serverPassword;
    private final LobbyManager lobbyManager;

    public ServerToClientConnection(Socket clientSocket, String clientId, String serverPassword) throws IOException {
        super(clientSocket);
        this.clientId = clientId;
        this.username = clientId;
        this.serverPassword = serverPassword;
        this.lobbyManager = LobbyManager.getInstance();
        setOtherSideIP(clientSocket.getInetAddress().getHostAddress());
        setOtherSidePort(clientSocket.getPort());
    }

    /* ---------------------- Lifecycle ---------------------- */

    @Override
    public boolean initialHandshake() {
        System.out.println("Handshake started with client: " + clientId);
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        return switch (message.getType()) {
            case CREATE_LOBBY -> { handleCreateLobby((CreateLobbyMessage) message); yield true; }
            case LIST_LOBBIES_REQUEST -> { handleListLobbies((ListLobbiesRequestMessage) message); yield true; }
            case JOIN_LOBBY_REQUEST -> { handleJoinLobby((JoinLobbyRequestMessage) message); yield true; }
            case PLAYER_READY -> { handlePlayerReady((PlayerReadyMessage) message); yield true; }
            case PLAYER_FARM_TYPE_UPDATE -> { handleFarmTypeUpdate((PlayerFarmTypeUpdateMessage) message); yield true; }
            case LEAVE_LOBBY -> { handleLeaveLobby(); yield true; }
            case START_GAME -> { handleStartGame((StartGameMessage) message); yield true; }
            case MOVE_PLAYER -> {handleMovePlayer((MovePlayerMessage) message); yield true; }
            case HOE_USED, PICKAXE_USED,WATERING_CAN_USED ->  {handleToolUsage(message); yield true; }
            case ADD_XP -> {handleAddXp((AddXpMessage) message); yield true; }
            case SEND_TEXT_FRIEND -> {handleSendTextFromFriend((MessageSendMessage) message); yield true; }
            case SEND_GIFT -> {handleSendGift((SendGiftMessage) message); yield true; }
            case RATE_GIFT -> {hanldeRateGift((RateGiftMessage) message); yield true; }
            case GIVE_BOUQUET -> {handleGiveBouquet((GiveBouquetMessage) message); yield true; }
            case PUBLIC_CHAT -> {handlePublicChat((PublicChatMessage) message); yield true; }
            case ASK_MARARIAGE -> {handleAskMarriage((AskMarriageMessage) message); yield true; }
            case RESPONSE_MARARIAGE -> {handleResponseMarriage((ResponseMarriage)message ); yield true; }
            case TRADE_REQUEST -> {handleTradeRequest((TradeRequestMessage) message); yield true; }
            case TRADE_REQUEST_RESPONSE -> {handleTradeRequestResponse((TradeRequestResponseMessage) message); yield true; }
            default -> false;
        };
    }

    @Override
    protected void onConnectionClosed() {
        System.out.println("Connection closed for client: " + username + " (" + clientId + ")");
        handleLeaveLobby();
    }

    /* ---------------------- Centralized Send ---------------------- */

    public void sendMessage(Message msg) {
        try {
            super.sendMessage(msg);
        } catch (Exception e) {
            System.err.println("Error sending message to " + username + ": " + e.getMessage());
        }
    }

    /* ---------------------- Message Handlers ---------------------- */

    private void handleCreateLobby(CreateLobbyMessage msg) {
        this.username = msg.getCreatorUsername();
        Lobby newLobby = lobbyManager.createLobby(msg.getLobbyName(), msg.isPrivate(), msg.isVisible(), msg.getPassword());
        lobbyManager.addPlayerToLobby(newLobby.getLobbyId(), username, this);
        currentLobbyId = newLobby.getLobbyId();
        sendMessage(new JoinLobbyResponseMessage(true, null, newLobby.getLobbyId(), newLobby.getName(), newLobby.getPlayerNames(), true));
    }

    private void handleListLobbies(ListLobbiesRequestMessage msg) {
        if (username == null || username.equals(clientId)) {
            username = msg.getUsername();
        }
        sendMessage(new ListLobbiesResponseMessage(lobbyManager.getVisibleLobbies()));
    }

    private void handleJoinLobby(JoinLobbyRequestMessage msg) {
        Lobby targetLobby = lobbyManager.getLobby(msg.getLobbyId());

        if (targetLobby == null) {
            sendMessage(new JoinLobbyResponseMessage(false, "Lobby not found", null, null, null, false));
            return;
        }
        if (targetLobby.isPrivate() && !isPasswordCorrect(targetLobby, msg.getPassword())) {
            sendMessage(new JoinLobbyResponseMessage(false, "Incorrect password", null, null, null, false));
            return;
        }

        username = msg.getUsername();
        boolean isAdmin = targetLobby.getPlayerNames().isEmpty();

        lobbyManager.addPlayerToLobby(targetLobby.getLobbyId(), username, this);
        currentLobbyId = targetLobby.getLobbyId();

        sendMessage(new JoinLobbyResponseMessage(true, null, targetLobby.getLobbyId(), targetLobby.getName(), targetLobby.getPlayerNames(), isAdmin));
        broadcastLobbyUpdate(targetLobby);
    }

    private void handlePlayerReady(PlayerReadyMessage msg) {
        if (!isInLobby()) return;
        lobbyManager.setPlayerReady(currentLobbyId, username, msg.isReady());
        broadcastLobbyUpdate(lobbyManager.getLobby(currentLobbyId));
    }

    private void handleFarmTypeUpdate(PlayerFarmTypeUpdateMessage msg) {
        if (!isInLobby()) return;

        if (!username.equals(msg.getUsername())) {
            sendMessage(new ErrorMessage("Unauthorized farm type update for another player."));
            return;
        }
        Lobby lobby = lobbyManager.getLobby(currentLobbyId);
        if (lobby != null) {
            lobby.getPlayerFarmTypes().put(username, msg.getFarmType());
            broadcastLobbyUpdate(lobby);
        }
    }

    private void handleLeaveLobby() {
        if (!isInLobby()) return;
        lobbyManager.removePlayerFromLobby(currentLobbyId, username, this);
        broadcastLobbyUpdate(lobbyManager.getLobby(currentLobbyId));
        currentLobbyId = null;
    }

    private void handleStartGame(StartGameMessage msg) {
        if (!isInLobby()) return;

        Lobby lobby = lobbyManager.getLobby(currentLobbyId);
        if (!isLobbyAdmin(lobby)) {
            sendMessage(new ErrorMessage("Only the lobby admin can start the game."));
            return;
        }
        if (!lobbyManager.areAllPlayersReady(currentLobbyId)) {
            sendMessage(new ErrorMessage("Not all players are ready."));
            return;
        }

        Map<String, String> farmTypes = lobby.getPlayerFarmTypes();
        if (farmTypes == null || farmTypes.isEmpty()) {
            sendMessage(new ErrorMessage("No players in lobby to start the game."));
            return;
        }

        msg.setWorldSeed(ThreadLocalRandom.current().nextLong());
        msg.setPlayers(farmTypes);

        int index = 0;
        for (ServerToClientConnection connection : lobbyManager.getLobbyConnections(currentLobbyId)) {
            msg.setIndex(index++);
            connection.sendMessage(msg);
        }
    }


    public void handleMovePlayer(MovePlayerMessage msg) {
        if (!isInLobby()) return;
        for (ServerToClientConnection connection : lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(msg.getPlayerName())) {continue;}
            connection.sendMessage(msg);
        }
    }


    public void handleToolUsage(Message message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection : lobbyManager.getLobbyConnections(currentLobbyId)) {
            connection.sendMessage(message);
        }
    }

    public void handleAddXp(AddXpMessage message){
        if(!isInLobby()) return;
        String playerName = message.getGoalPlayer();
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(playerName)){
                connection.sendMessage(message);
            }
        }
    }

    public void handleSendTextFromFriend(MessageSendMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getReceiverPlayerName())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleSendGift(SendGiftMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getRecieverName())) {
                connection.sendMessage(message);
            }
        }
    }

    public void hanldeRateGift(RateGiftMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getTargetPlayerName())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleGiveBouquet(GiveBouquetMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getReceiverName())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleAskMarriage(AskMarriageMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getReceiver())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleResponseMarriage(ResponseMarriage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getReceiver())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleTradeRequest(TradeRequestMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getReceiver())){
                connection.sendMessage(message);
            }
        }
    }

    public void handleTradeRequestResponse(TradeRequestResponseMessage message){
        if(!isInLobby()) return;
        for(ServerToClientConnection connection: lobbyManager.getLobbyConnections(currentLobbyId)) {
            if(connection.getUsername().equals(message.getSender())){
                connection.sendMessage(message);
            }
        }
    }

    public void handlePublicChat(PublicChatMessage message) {
        if (!isInLobby()) return;
        lobbyManager.broadcastToLobby(currentLobbyId, message);
    }

    /* ---------------------- Utility ---------------------- */

    private boolean isPasswordCorrect(Lobby lobby, String providedPassword) {
        return providedPassword != null && providedPassword.equals(lobby.getPassword());
    }

    private boolean isInLobby() {
        return currentLobbyId != null;
    }

    private boolean isLobbyAdmin(Lobby lobby) {
        return lobby != null && !lobby.getPlayerNames().isEmpty() && lobby.getPlayerNames().get(0).equals(username);
    }

    private void broadcastLobbyUpdate(Lobby lobby) {
        if (lobby == null) return;
        LobbyUpdateMessage update = new LobbyUpdateMessage(lobby.getLobbyId(), lobby.getPlayerNames(), lobby.getPlayersReadyStatus());
        update.setFarmTypes(lobby.getPlayerFarmTypes());
        lobbyManager.broadcastToLobby(lobby.getLobbyId(), update);
    }

    /* ---------------------- Getters ---------------------- */

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }
}
