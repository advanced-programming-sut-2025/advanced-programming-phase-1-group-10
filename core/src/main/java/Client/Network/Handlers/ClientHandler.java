package Client.Network.Handlers;

import Common.Models.App;
import Common.Models.FriendShip.Friendship;
import Common.Models.Game;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.*;

import java.util.ArrayList;

public class ClientHandler {
    public void movePlayerHandle(MovePlayerMessage movePlayerMsg) {
        Player player = App.getInstance().getCurrentGame().getPlayerByName(movePlayerMsg.getPlayerName());
        if (player == null) {
            System.err.println("There is not such a player named " + movePlayerMsg.getPlayerName());
        }
        assert player != null;
        player.setMoving(movePlayerMsg.isMoving());
        player.setX(movePlayerMsg.getX());
        player.setY(movePlayerMsg.getY());
        player.setDirection(movePlayerMsg.getDirection());
        player.setPosition(movePlayerMsg.getPosition());
    }


    public void handleHoe(HoeUsedMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Tile tile = game.getGameMap().getMap()[message.getX()][message.getY()];

        if (tile != null) {
            tile.setPlow(message.isPlowed());
        }

    }

    public void handlePickaxe(PickaxeUsedMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Tile tile = game.getGameMap().getMap()[message.getX()][message.getY()];

        if (tile != null) {
            tile.setItem(null); // Mineral removed
            tile.setPlow(message.isPlowed()); // Optional
        }
    }

    public void handleWateringCan(WateringCanUsedMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Tile tile = game.getGameMap().getMap()[message.getX()][message.getY()];
        if (tile != null) {
            tile.setWatered(message.isWatered());
        }
    }

    public void handleAddXp(AddXpMessage message) {
        ArrayList<Friendship> friendships = App.getInstance().getCurrentGame().getCurrentPlayer().getFriendships();
        for(Friendship friendship : friendships) {
            if(friendship.getPlayer().getName().equals(message.getSenderPlayer())){
                friendship.setXp(message.getXp() + friendship.getXp());
            }
        }
    }

}
