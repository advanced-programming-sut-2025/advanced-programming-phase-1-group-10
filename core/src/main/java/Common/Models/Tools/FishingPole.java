package Common.Models.Tools;

import Client.Assets.ToolAsset;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Place.Lake;
import Common.Models.PlayerStuff.Player;
import Common.Models.Position;
import Common.Models.Tile;
import Common.Network.Messages.Message;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FishingPole extends Tool {

    public FishingPole(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }


    @Override
    public String getName() {
        return "Fishing Pole";
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }

    @Override
    public Message use(Tile tile) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();


        if (isPlayerNearLake()) {


            int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
            currentPlayer.getEnergy().setEnergyAmount(
                currentPlayer.getEnergy().getEnergyAmount() - energyCost
            );

            MessageSystem.showInfo("Starting fishing minigame...", 2.0f);
            App.getInstance().getGameControllerFinal().startFishingMiniGame();
        } else {
            MessageSystem.showError("You need to be near a lake to fish!", 3.0f);
        }
        return null;
    }

    private boolean isPlayerNearLake() {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = currentPlayer.getPosition();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Position adjacentPos = new Position(playerPos.getX() + dx, playerPos.getY() + dy);
                try {
                    Tile adjacentTile = App.getInstance().getCurrentGame().getGameMap().getMap()[adjacentPos.getX()][adjacentPos.getY()];

                    if (adjacentTile != null && adjacentTile.getPlace() != null &&
                        adjacentTile.getPlace() instanceof Lake) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }

        return false;
    }
}
