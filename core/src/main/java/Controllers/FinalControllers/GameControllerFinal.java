package Controllers.FinalControllers;

import Models.App;
import Views.GameLauncherView;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameControllerFinal {

    GameLauncherView gameLauncherView;

    private MapController mapController;
    private PlayerController playerController;


    public void setView(GameLauncherView gameLauncherView) {
        this.gameLauncherView = gameLauncherView;
        this.mapController = new MapController();
        this.playerController = new PlayerController(App.getInstance().getCurrentGame().getGameMap());
    }

    public void update(SpriteBatch batch) {
        mapController.update(batch);
        playerController.update(batch);
    }

}
