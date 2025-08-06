package Common.Network.Received.Handlers;


import Common.Network.Received.HandleMessage;
import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.HoeUsedMessage;
import Common.Network.Send.MessageTypes.PickaxeUsedMessage;
import Common.Models.App;
import Common.Models.Game;
import Common.Models.Tile;


public class ToolHandler extends HandleMessage {

    @Override
    public void handle(Message message) {
        if (message instanceof HoeUsedMessage) {
            handleHoe((HoeUsedMessage) message);
        } else if (message instanceof PickaxeUsedMessage) {
            handlePickaxe((PickaxeUsedMessage) message);
        }
    }

    public void handleHoe(HoeUsedMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Tile tile = game.getGameMap().getMap()[message.getX()][message.getY()];

        if (tile != null) {
            tile.setPlow(message.isPlowed());
        }

        // Optionally update energy or XP if included
        if (message.getNewEnergy() != -1) {
            game.getCurrentPlayer().getEnergy().setEnergyAmount(message.getNewEnergy());
        }
    }

    public void handlePickaxe(PickaxeUsedMessage message) {
        Game game = App.getInstance().getCurrentGame();
        Tile tile = game.getGameMap().getMap()[message.getX()][message.getY()];

        if (tile != null) {
            tile.setItem(null); // Mineral removed
            tile.setPlow(message.isPlowed()); // Optional
        }

        // Update player stats if provided
        if (message.getNewEnergy() != -1) {
            game.getCurrentPlayer().getEnergy().setEnergyAmount(message.getNewEnergy());
        }

        if (message.getNewXP() != -1) {
            game.getCurrentPlayer().setMiningAbility(message.getNewXP());
        }
    }
}
