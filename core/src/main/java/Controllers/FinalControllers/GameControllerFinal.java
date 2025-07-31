package Controllers.FinalControllers;

import Assets.AnimalAsset;
import Controllers.MessageSystem;
import Models.App;
import Models.Place.Lake;
import Views.GameLauncherView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

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
    private WeatherController weatherController;
    private FishController fishController;
    private InventoryController inventoryController;
    private FishingMiniGameController fishingMiniGameController;
    private boolean fishingMiniGameActive = false;
    private DialogueController dialogueController;
    private NpcMenuController npcMenuController;
    private QuestMenuController questMenuController;
    private AnimalListController animalListController;
    private FriendshipController friendshipController;
    private NotificationController notificationController;
    private ChatHistoryController chatHistoryController;
    private AnimalMovementController animalMovementController;


    public void setView(GameLauncherView gameLauncherView) {
        this.gameLauncherView = gameLauncherView;
        this.mapController = new MapController();
        this.playerController = new PlayerController(App.getInstance().getCurrentGame().getGameMap());
        this.barController = new BarController();
        this.animalBuildingController = new AnimalBuildingController(new AnimalAsset());
        this.interactController = new InteractController();
        this.energyController = new EnergyController();
        this.timeController = new TimeController();
        this.cheatBoxController = new CheatBoxController();
        this.storeController = new StoreController();
        this.skillController = new SkillController();
        this.npcController = new NpcController();
        this.weatherController = new WeatherController();
        this.inventoryController = new InventoryController();
        this.dialogueController = new DialogueController();
        this.npcMenuController = new NpcMenuController();
        this.friendshipController = new FriendshipController();
        this.inventoryBarController = new InventoryBarController(npcMenuController, friendshipController);
        this.questMenuController = new QuestMenuController();
        this.animalListController = new AnimalListController(new AnimalAsset());
        this.notificationController = new NotificationController();
        this.chatHistoryController = new ChatHistoryController();
        this.animalMovementController = new AnimalMovementController(new AnimalAsset(), App.getInstance().getCurrentGame().getGameMap());



        List<Lake> allLakes = FishController.findAllLakes();
        if (!allLakes.isEmpty()) {
            this.fishController = new FishController(allLakes);
        }


        this.fishingMiniGameController = new FishingMiniGameController(this);
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

        if (fishController != null) {
            fishController.update(batch, delta);
        }
        animalMovementController.update(delta);
        animalMovementController.render(batch);
    }

    public void startFishingMiniGame() {
        fishingMiniGameActive = true;
        fishingMiniGameController.startGame();
    }

    public void updateSecondCamera(SpriteBatch batch, float delta, Viewport viewport) {
        weatherController.update(batch);
        barController.update(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        inventoryBarController.update(batch);
        energyController.update(batch);
        skillController.update(batch);
        inventoryController.update(batch);
        dialogueController.update(batch);
        npcMenuController.update(batch);
        questMenuController.update(batch);
        friendshipController.render(delta);
        notificationController.update();
        chatHistoryController.update(batch);
        MessageSystem.update(batch, viewport);
        if (fishingMiniGameActive) {
            fishingMiniGameController.update(batch, delta);
            return;
        }
        animalListController.update(batch);
    }

    public AnimalMovementController getAnimalMovementController() {
        return animalMovementController;
    }

    public void setFishingMiniGameActive(boolean active) {
        fishingMiniGameActive = active;
    }


    public boolean isFishingMiniGameActive() {
        return fishingMiniGameActive;
    }


    public FishingMiniGameController getFishingMiniGameController() {
        return fishingMiniGameController;
    }

    public FishController getFishController() {
        return fishController;
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

    public WeatherController getWeatherController() {
        return weatherController;
    }

    public DialogueController getNpcRelationController() {
        return dialogueController;
    }

    public AnimalListController getAnimalListController() {
        return animalListController;
    }

    public GameLauncherView getGameLauncherView() {
        return gameLauncherView;
    }

    public FriendshipController getFriendshipController() {
        return friendshipController;
    }
}
