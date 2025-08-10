package Client.Controllers;

import Common.Models.App;
import Common.Models.Game;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class EmotionDisplaySystem {

    private static class ActiveEmotion {
        String playerName;
        String text;
        boolean isEmoji;
        int emojiIndex;
        long startTime;
        float duration = 10.0f;

        public ActiveEmotion(String playerName, String text, boolean isEmoji, int emojiIndex) {
            this.playerName = playerName;
            this.text = text;
            this.isEmoji = isEmoji;
            this.emojiIndex = emojiIndex;
            this.startTime = TimeUtils.millis();
        }

        public boolean isExpired() {
            return TimeUtils.timeSinceMillis(startTime) > duration * 1000;
        }

        public float getAlpha() {
            float elapsed = TimeUtils.timeSinceMillis(startTime) / 1000f;

            if (elapsed < 0.5f) {

                return elapsed / 0.5f;
            } else if (elapsed > duration - 0.5f) {

                return (duration - elapsed) / 0.5f;
            }

            return 1.0f;
        }
    }

    private static final Map<String, ActiveEmotion> activeEmotions = new HashMap<>();
    private static BitmapFont font;
    private static Texture[] emojiTextures;

    public static void initialize() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.5f);


        String[] emojiPaths = new String[] {
            "emoji/clap.png",
            "emoji/heart.png",
            "emoji/smile.png",
            "emoji/thumbsup.png"
        };

        emojiTextures = new Texture[emojiPaths.length];
        for (int i = 0; i < emojiPaths.length; i++) {
            emojiTextures[i] = new Texture(Gdx.files.internal(emojiPaths[i]));
        }
    }

    public static void addEmotion(String playerName, String text, boolean isEmoji, int emojiIndex) {
        activeEmotions.put(playerName, new ActiveEmotion(playerName, text, isEmoji, emojiIndex));
    }

    public static void update(SpriteBatch batch) {

        Array<String> expiredKeys = new Array<>();
        for (Map.Entry<String, ActiveEmotion> entry : activeEmotions.entrySet()) {
            if (entry.getValue().isExpired()) {
                expiredKeys.add(entry.getKey());
            }
        }

        for (String key : expiredKeys) {
            activeEmotions.remove(key);
        }


        Game currentGame = App.getInstance().getCurrentGame();
        if (currentGame == null) return;

        for (Map.Entry<String, ActiveEmotion> entry : activeEmotions.entrySet()) {
            String playerName = entry.getKey();
            ActiveEmotion emotion = entry.getValue();

            Player player = currentGame.getPlayerByName(playerName);
            if (player == null) continue;

            float alpha = emotion.getAlpha();


            float x = player.getX();
            float y = player.getY() + 70;

            if (emotion.isEmoji && emotion.emojiIndex >= 0 && emotion.emojiIndex < emojiTextures.length) {

                Texture emojiTexture = emojiTextures[emotion.emojiIndex];
                batch.setColor(1, 1, 1, alpha);
                batch.draw(emojiTexture, x - 16, y - 16, 32, 32);
                batch.setColor(1, 1, 1, 1);
            } else {

                font.setColor(1, 1, 1, alpha);
                GlyphLayout layout = new GlyphLayout(font, emotion.text);
                font.draw(batch, emotion.text, x - layout.width / 2, y + layout.height / 2);
            }
        }
    }

    public static void dispose() {
        if (font != null) font.dispose();

        if (emojiTextures != null) {
            for (Texture texture : emojiTextures) {
                if (texture != null) texture.dispose();
            }
        }
    }
}
