package Server.Controllers.FinalControllers;

import Common.Models.App;
import Common.Models.PlayerStuff.Energy;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnergyController {

    private final Texture emptyBar;
    private final Texture fullBar;
    private final TextureRegion fullBarRegion;

    private static final int barWidth = 36;
    private static final int barHeight = 215;
    private static final int barX = Gdx.graphics.getWidth() - 60;
    private static final int barY = Gdx.graphics.getHeight() - 480;

    // Height of the top portion with the "E" label in the texture, which we want to ignore
    private static final int fullBarYOffset = 40;

    public EnergyController() {
        emptyBar = new Texture("energy/empty.png");
        fullBar = new Texture("energy/full.png");

        // Define the region of fullBar we actually want to use (excluding the "E" at the top)
        fullBarRegion = new TextureRegion(fullBar, 0, fullBarYOffset, barWidth, barHeight - fullBarYOffset);
    }

    public void update(SpriteBatch batch) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        double current = player.getEnergy().getEnergyAmount();
        double max = Energy.MAX_ENERGY_AMOUNT;
        double percent = Math.max(0f, Math.min(1f, current / max));

        // Draw empty energy bar (background)
        batch.draw(emptyBar, barX, barY, barWidth, barHeight);

        // Calculate visible height of full bar based on energy percent
        int fullRegionHeight = fullBarRegion.getRegionHeight();
        int visibleHeight = (int)(fullRegionHeight * percent);

        if (visibleHeight > 0) {
            // Create a dynamic region from the bottom up
            TextureRegion visibleRegion = new TextureRegion(
                fullBarRegion,
                0,
                fullRegionHeight - visibleHeight,
                barWidth,
                visibleHeight
            );

            // Draw the visible part of the full bar
            batch.draw(visibleRegion, barX, barY, barWidth, visibleHeight);
        }
    }

    public void dispose() {
        emptyBar.dispose();
        fullBar.dispose();
    }
}
