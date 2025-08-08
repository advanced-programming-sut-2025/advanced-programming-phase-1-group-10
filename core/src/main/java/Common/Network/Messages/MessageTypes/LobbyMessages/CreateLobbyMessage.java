package Common.Network.Messages.MessageTypes.LobbyMessages;

import Common.Network.Messages.Message;

public class CreateLobbyMessage extends Message {
    private String lobbyName;
    private boolean isPrivate;
    private boolean isVisible;
    private String password;
    private String creatorUsername;

    public CreateLobbyMessage(String lobbyName, boolean isPrivate, boolean isVisible, String password, String creatorUsername) {
        super(MessageType.CREATE_LOBBY);
        this.lobbyName = lobbyName;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.password = password;
        this.creatorUsername = creatorUsername;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getPassword() {
        return password;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }
}
