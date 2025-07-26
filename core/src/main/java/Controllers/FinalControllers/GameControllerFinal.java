package Controllers.FinalControllers;

import Models.App;
import Views.GameLauncherView;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameControllerFinal {

    GameLauncherView gameLauncherView;

    private MapController mapController;
    private PlayerController playerController;
    private BarController barController;
    private InventoryBarController inventoryBarController;
    private AnimalBuildingController animalBuildingController;
    private InteractController interactController;


    public void setView(GameLauncherView gameLauncherView) {
        this.gameLauncherView = gameLauncherView;
        this.mapController = new MapController();
        this.playerController = new PlayerController(App.getInstance().getCurrentGame().getGameMap());
        this.barController = new BarController();
        this.inventoryBarController = new InventoryBarController();
        this.animalBuildingController = new AnimalBuildingController();
        this.interactController = new InteractController();
    }

    public void update(SpriteBatch batch) {
        mapController.update(batch);
        playerController.update(batch);
        animalBuildingController.update(batch);
        interactController.update();
    }

    public MapController getMapController() {
        return mapController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public BarController getBarController() {
        return barController;
    }

    public InventoryBarController getInventoryBarController() {
        return inventoryBarController;
    }

    public AnimalBuildingController getAnimalBuildingController() {
        return animalBuildingController;
    }

    public InteractController getInteractController() {
        return interactController;
    }
}
