package Controllers.FinalControllers;

import Models.App;
import Models.NPC.NPC;
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
    private EnergyController energyController;
    private TimeController timeController;
    private CheatBoxController cheatBoxController;
    private StoreController storeController;
    private SkillController skillController;
    private NpcController npcController;

    public void setView(GameLauncherView gameLauncherView) {
        this.gameLauncherView = gameLauncherView;
        this.mapController = new MapController();
        this.playerController = new PlayerController(App.getInstance().getCurrentGame().getGameMap());
        this.barController = new BarController();
        this.inventoryBarController = new InventoryBarController();
        this.animalBuildingController = new AnimalBuildingController();
        this.interactController = new InteractController();
        this.energyController = new EnergyController();
        this.timeController = new TimeController();
        this.cheatBoxController = new CheatBoxController();
        this.storeController = new StoreController();
        this.skillController = new SkillController();
        this.npcController = new NpcController();
    }

    public void update(SpriteBatch batch, float delta) {

        cheatBoxController.update(delta);
        if (animalBuildingController.isShowingInterior()) {
            animalBuildingController.update(batch, delta);
            return;
        }

        mapController.update(batch);
        interactController.update();
        animalBuildingController.update(batch, delta);
        timeController.update(delta, batch);
        storeController.update(batch);
        npcController.update(batch);
        playerController.update(batch);
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

    public EnergyController getEnergyController() {
        return energyController;
    }

    public TimeController getTimeController() {
        return timeController;
    }

    public CheatBoxController getCheatBoxController() {
        return cheatBoxController;
    }

    public StoreController getStoreController() {
        return storeController;
    }

    public SkillController getSkillController() {
        return skillController;
    }

    public NpcController getNpcController() {
        return npcController;
    }
}
