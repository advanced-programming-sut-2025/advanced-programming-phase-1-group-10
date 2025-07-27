package Controllers.FinalControllers;

import Models.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeController {

    private float timeAccumulator = 0f;
    private final Texture nightOverlay;

    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();  // Replace with actual screen height

    public TimeController() {
        nightOverlay = new Texture("ui/black.png"); // 1x1 black pixel texture
    }

    public void update(float delta, SpriteBatch batch) {
        // Update in-game time every 60 seconds of real time
        timeAccumulator += delta;
        while (timeAccumulator >= 60f) {
            App.getInstance().getCurrentGame().getGameTime().nextHour();
            timeAccumulator -= 60f;
        }

        // Get current in-game hour
        int hour = App.getInstance().getCurrentGame().getGameTime().getHour();

        // Apply night overlay between 18:00 and 22:00
        if (hour >= 18 && hour <= 22) {
            float alpha = getNightAlpha(hour);

            // Set color to black with calculated transparency
            batch.setColor(0f, 0f, 0f, alpha);
            batch.draw(nightOverlay, 0, 0, screenWidth, screenHeight);
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }

    private float getNightAlpha(int hour) {
        // Smooth transition from 18 (day) to 22 (night)
        if (hour < 18 || hour > 22) return 0f;
        return (hour - 18) / 4f; // 0.0 → 1.0 over 4 hours
    }

    public void dispose() {
        nightOverlay.dispose();
    }
}
