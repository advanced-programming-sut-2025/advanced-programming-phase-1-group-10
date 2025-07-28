package Controllers.FinalControllers;

import Models.App;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SkillController {

    private final Texture backgroundTexture;
    private final Texture fillTexture;

    private boolean showSkills = false;

    public SkillController() {
        this.backgroundTexture = new Texture("skill/skills.png");
        this.fillTexture = new Texture("skill/fill.png");
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            showSkills = !showSkills;
        }

        if (!showSkills) return;

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        int[] levels = {
            player.getFarmingLevel(),
            player.getMiningLevel(),
            player.getForagingLevel(),
            player.getFishingLevel()
        };

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Panel size = 60% of screen
        int panelWidth = (int) (screenWidth * 0.6f);
        int panelHeight = (int) (screenHeight * 0.6f);

        int panelX = (screenWidth - panelWidth) / 2;
        int panelY = (screenHeight - panelHeight) / 2;

        // Draw background panel
        batch.draw(backgroundTexture, panelX, panelY, panelWidth, panelHeight);

        // Dynamic fill size: ~16% width, ~25% height of panel (adjust as needed)
        int iconSizeWidth = (int) (panelWidth * 0.06f);
        int iconSizeHeight = (int) (panelHeight * 0.135f);

        // Padding between fills
        int fillPaddingX = (int) (panelWidth * 0.0197f);
        int fillPaddingY = (int) (panelHeight * 0.053f);

        // Starting point inside panel: adjust these % to position grid
        int startX = panelX + (int) (panelWidth * 0.513f); // ~55% across panel
        int startY = panelY + (int) (panelHeight * 0.693f); // ~78% from bottom

        for (int row = 0; row < levels.length; row++) {
            int level = Math.min(levels[row], 5);
            for (int col = 0; col < level; col++) {
                int drawX = startX + col * (iconSizeWidth + fillPaddingX);
                int drawY = startY - row * (iconSizeHeight + fillPaddingY);
                batch.draw(fillTexture, drawX, drawY, iconSizeWidth, iconSizeHeight);
            }
        }
    }
}
