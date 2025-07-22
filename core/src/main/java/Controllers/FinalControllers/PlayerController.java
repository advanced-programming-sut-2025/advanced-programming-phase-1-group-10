package Controllers.FinalControllers;

import Assets.PlayerAsset;
import Models.App;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class PlayerController {

    private final ArrayList<Player> players = App.getInstance().getCurrentGame().getPlayers();
    private final PlayerAsset movementAnimation = new PlayerAsset();

    public void update(SpriteBatch batch) {
        for (Player player : players) {
            player.update(Gdx.graphics.getDeltaTime());
            player.render(batch);
        }
    }
}
