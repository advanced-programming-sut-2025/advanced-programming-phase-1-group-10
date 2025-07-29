package Models.Tools;

import Controllers.FinalControllers.FishingMiniGameController;
import Controllers.MessageSystem;
import Models.App;
import Models.Place.Lake;
import Models.PlayerStuff.Player;
import Models.Position;
import Models.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FishingPole extends Tool {

    public FishingPole(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Fishing Pole";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }

    @Override
    public void use(Tile tile) {
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
