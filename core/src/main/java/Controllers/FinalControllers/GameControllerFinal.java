package Controllers.FinalControllers;

import Views.GameLauncherView;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameControllerFinal {

    GameLauncherView gameLauncherView;

    MapController mapController;


    public void setView(GameLauncherView gameLauncherView) {
        this.gameLauncherView = gameLauncherView;
        this.mapController = new MapController();
    }

    public void update(SpriteBatch batch) {
        mapController.update(batch);
    }

}
