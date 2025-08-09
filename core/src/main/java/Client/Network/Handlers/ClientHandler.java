package Client.Network.Handlers;

import Client.Controllers.ChatManager;
import Client.Controllers.Utils.ItemUtility;
import Common.Models.App;
import Common.Models.FriendShip.Friendship;
import Common.Models.FriendShip.Gift;
import Common.Models.FriendShip.MessageFriend;
import Common.Models.Game;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
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

    public void hanldeMessageSendToFrieend(MessageSendMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Player reciever = App.getInstance().getCurrentGame().getCurrentPlayer();
        reciever.getRecievedMessages().add(new MessageFriend(
            game.getPlayerByName(message.getSenderPlayerName()),
            game.getPlayerByName(message.getReceiverPlayerName()),
            message.getText(),
            false
        ));
        // for private chats
        handlePrivateChat(message);
    }

    public void hanldeSendGift(SendGiftMessage message){
        Player reciever = App.getInstance().getCurrentGame().getCurrentPlayer();
        Player sender = App.getInstance().getCurrentGame().getPlayerByName(message.getSenderName());
        Gift gift = new Gift(sender,reciever, ItemUtility.createItem(message.getItemName(), message.getItemNumber()),false);
        gift.setSeed(message.getGiftSeed());
        reciever.getRecievedGifts().add(gift);
    }

    public void handldeRateGift(RateGiftMessage message) {
        Player reciever = App.getInstance().getCurrentGame().getCurrentPlayer();
        for(Gift gift : reciever.getSendedGifts()) {
            System.out.println(gift.getSeed());
            if(gift.getSeed() ==  message.getGiftSeed()){
                gift.setRate(message.getRate());
            }
        }
        System.out.println("FUCK");
    }


    public void handlePrivateChat(MessageSendMessage message) {
        ChatManager.getInstance().handleMessageSendMessage(message);
    }

    public void handlePublicChat(PublicChatMessage message) {
        ChatManager.getInstance().handlePublicChatMessage(message);
    }
}
