package Client.Network.Handlers;

import Client.Controllers.ChatManager;
import Client.Controllers.DialogSystem;
import Client.Controllers.MessageSystem;
import Client.Controllers.Utils.ItemUtility;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.FriendShip.Friendship;
import Common.Models.FriendShip.Gift;
import Common.Models.FriendShip.MessageFriend;
import Common.Models.Game;
import Common.Models.Item;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Network.Messages.MessageTypes.*;
import Common.Network.Messages.MessageTypes.LobbyMessages.AskMarriageMessage;
import Common.Network.Messages.MessageTypes.LobbyMessages.ResponseMarriage;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void handleGiveBouquet(GiveBouquetMessage message) {
        Item flower = ItemUtility.createItem("Bouquet",1);
        App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(flower);
        MessageSystem.showInfo("You recieved a bouquet from " + message.getSenderName(),2f);
        for(Friendship friendship: App.getInstance().getCurrentGame().getCurrentPlayer().getFriendships()) {
            if(friendship.getPlayer().getName().equals(message.getSenderName())){
                friendship.setFlowerGiven(true);
            }
        }
    }


    public void handlePrivateChat(MessageSendMessage message) {
        ChatManager.getInstance().handleMessageSendMessage(message);
    }

    public void handlePublicChat(PublicChatMessage message) {
        ChatManager.getInstance().handlePublicChatMessage(message);
    }

    public void handleAskMarriage(AskMarriageMessage message) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        Player other = App.getInstance().getCurrentGame().getPlayerByName(message.getSender());
        Friendship fs1 = getFriendship(currentPlayer, other);
        Friendship fs2 = getFriendship(other, currentPlayer);
        Item ring = ItemUtility.createItem("Ring",1);

        AtomicBoolean isAccepted = new AtomicBoolean(false);

        DialogSystem.show("Player: " + message.getSender() + " wants to marry. Do you agree?",
            () ->{
                currentPlayer.setCouple(other);
                other.setCouple(currentPlayer);
                currentPlayer.getInventory().getBackPack().removeItemNumber(ring.getName(), 1);
                other.getInventory().getBackPack().addItem(ring);
                fs1.setMarried(true);
                fs2.setMarried(true);
                MessageSystem.showInfo("Happy your Marriage!", 2f);
                isAccepted.set(true);
            },
            () ->{
                fs1.setXp(-fs1.getXp());
                fs2.setXp(-fs2.getXp());
                MessageSystem.showInfo("You saved your life.",2f);
                isAccepted.set(false);
            }
            );

        ClientNetworkManager.getInstance().sendMessage(new ResponseMarriage(
            message.getReceiver(),
            message.getSender(),
            isAccepted.get())
        );
    }

    public Friendship getFriendship(Player player, Player goal) {
        return player.getFriendships().stream().filter(f -> f.getPlayer().equals(goal)).findFirst().orElse(null);
    }

    public void handleResponseMarriage(ResponseMarriage message) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        Player other = App.getInstance().getCurrentGame().getPlayerByName(message.getSender());
        Friendship fs1 = getFriendship(currentPlayer, other);
        Friendship fs2 = getFriendship(other, currentPlayer);
        Item ring = ItemUtility.createItem("Ring",1);

        if(message.isAccepted()){
            currentPlayer.setCouple(other);
            other.setCouple(currentPlayer);
            currentPlayer.getInventory().getBackPack().removeItemNumber(ring.getName(), 1);
            other.getInventory().getBackPack().addItem(ring);
            fs1.setMarried(true);
            fs2.setMarried(true);
            MessageSystem.showInfo("Happy your Marriage!", 2f);
        } else {
            fs1.setXp(-fs1.getXp());
            fs2.setXp(-fs2.getXp());
            MessageSystem.showInfo("You saved your life.",2f);
        }
    }

    public void handleTradeRequest(TradeRequestMessage message) {
        AtomicBoolean isAccepted = new AtomicBoolean(false);
        DialogSystem.show("Player: " + message.getSender() + " wants to start trade, accept?",
            () -> {
                isAccepted.set(true);
                App.getInstance().getGameControllerFinal().getFriendshipController().setShowMenu(false);
                App.getInstance().getGameControllerFinal().getTradeController().setShown(true);
            },

            () -> {
                isAccepted.set(false);
            }
            );
        ClientNetworkManager.getInstance().sendMessage(new TradeRequestResponseMessage(
            message.getSender(),
            message.getReceiver(),
            isAccepted.get()
        ));
    }

    public void handleTradeRequestResponse(TradeRequestResponseMessage message) {
        if(message.isAccepted()){
            MessageSystem.showInfo("Trade request accepted your trade!", 2f);
            App.getInstance().getGameControllerFinal().getFriendshipController().setShowMenu(false);
            App.getInstance().getGameControllerFinal().getTradeController().setShown(true);
        } else {
            MessageSystem.showMessage("Trade request did not accepted!",2f, Color.RED);
        }
    }
}
