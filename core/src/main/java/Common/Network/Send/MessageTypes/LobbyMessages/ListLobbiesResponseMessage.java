package Common.Network.Send.MessageTypes.LobbyMessages;

import Common.Network.Send.Message;

import java.util.List;

public class ListLobbiesResponseMessage extends Message {
    private List<LobbyInfo> lobbies;

    public ListLobbiesResponseMessage(List<LobbyInfo> lobbies) {
        super(MessageType.LIST_LOBBIES_RESPONSE);
        this.lobbies = lobbies;
    }

    public List<LobbyInfo> getLobbies() {
        return lobbies;
    }

    public static class LobbyInfo {
        private String lobbyId;
        private String name;
        private int playerCount;
        private boolean isPrivate;
        private boolean isVisible;

        public LobbyInfo(String lobbyId, String name, int playerCount, boolean isPrivate, boolean isVisible) {
            this.lobbyId = lobbyId;
            this.name = name;
            this.playerCount = playerCount;
            this.isPrivate = isPrivate;
            this.isVisible = isVisible;
        }

        public String getLobbyId() {
            return lobbyId;
        }

        public String getName() {
            return name;
        }

        public int getPlayerCount() {
            return playerCount;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public boolean isVisible() {
            return isVisible;
        }
    }
}
