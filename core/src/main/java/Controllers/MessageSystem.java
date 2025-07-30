package Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MessageSystem {

    private static class Message {
        String text;
        long startTime;
        float duration;
        Color color;
        float alpha = 1.0f;
        float scale = 0.0f;

        public Message(String text, float duration, Color color) {
            this.text = text;
            this.duration = duration;
            this.startTime = TimeUtils.millis();
            this.color = new Color(color);
        }

        public boolean isExpired() {
            return TimeUtils.timeSinceMillis(startTime) > duration * 1000;
        }

        public void updateAlpha() {
            float elapsed = TimeUtils.timeSinceMillis(startTime) / 1000f;

            if (elapsed < 0.3f) {
                scale = MathUtils.lerp(0.0f, 1.0f, elapsed / 0.3f);
                alpha = MathUtils.lerp(0.0f, 1.0f, elapsed / 0.3f);
            }
            else if (elapsed > duration - 0.5f) {
                alpha = MathUtils.lerp(0.0f, 1.0f, (duration - elapsed) / 0.5f);
            }
            else {
                scale = 1.0f;
                alpha = 1.0f;
            }
        }
    }

    private static final Array<Message> activeMessages = new Array<>();
    private static BitmapFont font;
    private static BitmapFont largeFont;
    private static ShapeRenderer shapeRenderer;
    private static final int MAX_MESSAGES = 5;
    private static final int MESSAGE_HEIGHT = 60;
    private static final int MESSAGE_SPACING = 20;
    private static final int MAX_MESSAGE_WIDTH = 400;
    private static final int PADDING_X = 40;
    private static final int PADDING_Y = 40;
    private static final int BORDER_THICKNESS = 5;
    private static final Color BORDER_COLOR = new Color(52/255f,26/255f,58/255f,0);
    private static final Color ERROR_COLOR = new Color(1, 0.3f, 0.3f, 1);
    private static final Color INFO_COLOR = new Color(0.3f, 1, 0.3f, 1);
    private static final Color WARNING_COLOR = new Color(1, 0.8f, 0.2f, 1);
    private static OrthographicCamera hudCamera;


    public static void initialize() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter paramSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramSmall.size = 40;
        paramSmall.color = Color.BROWN;

        FreeTypeFontGenerator.FreeTypeFontParameter paramLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramLarge.size = 60;
        paramLarge.color = Color.BROWN;

        font = generator.generateFont(paramSmall);
        largeFont = generator.generateFont(paramLarge);


        generator.dispose();

        shapeRenderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static void showError(String text, float durationSeconds) {
        addMessage(text, durationSeconds, ERROR_COLOR);
    }

    public static void showInfo(String text, float durationSeconds) {
        addMessage(text, durationSeconds, INFO_COLOR);
    }

    public static void showWarning(String text, float durationSeconds) {
        addMessage(text, durationSeconds, WARNING_COLOR);
    }

    public static void showMessage(String text, float durationSeconds, Color color) {
        addMessage(text, durationSeconds, color);
    }

    private static void addMessage(String text, float durationSeconds, Color color) {
        if (activeMessages.size >= MAX_MESSAGES) {
            activeMessages.removeIndex(0);
        }

        activeMessages.add(new Message(text, durationSeconds, color));
    }

    public static void update(SpriteBatch batch, Viewport viewport) {

        hudCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        hudCamera.update();

        for (int i = activeMessages.size - 1; i >= 0; i--) {
            if (activeMessages.get(i).isExpired()) {
                activeMessages.removeIndex(i);
            }
        }

        for (Message message : activeMessages) {
            message.updateAlpha();
        }

        if (activeMessages.size == 0) {
            return;
        }

        renderMessages(batch, viewport);
    }

    private static void renderMessages(SpriteBatch batch, Viewport viewport) {
        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        float startY = screenHeight - PADDING_Y;


        Matrix4 originalProjection = batch.getProjectionMatrix().cpy();


        batch.end();


        shapeRenderer.setProjectionMatrix(hudCamera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);


        for (int i = 0; i < activeMessages.size; i++) {
            Message message = activeMessages.get(i);

            if (message.scale < 0.01f) continue;

            GlyphLayout layout = new GlyphLayout(font, message.text);
            float messageWidth = Math.min(layout.width + 40, MAX_MESSAGE_WIDTH);


            float verticalPosition = i * (MESSAGE_HEIGHT + MESSAGE_SPACING);
            float textY = startY - verticalPosition - MESSAGE_HEIGHT / 2;


            float boxX = PADDING_X;
            float boxY = textY - layout.height / 2 - 10;
            float boxWidth = 600;
            float boxHeight = 60;


            float scale = message.scale;
            float scaledWidth = boxWidth * scale;
            float scaledHeight = boxHeight * scale;


            float centerX = boxX + boxWidth / 2;
            float centerY = boxY + boxHeight / 2;


            float scaledX = centerX - scaledWidth / 2;
            float scaledY = centerY - scaledHeight / 2;


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(BORDER_COLOR.r, BORDER_COLOR.g, BORDER_COLOR.b, message.alpha));
            shapeRenderer.rect(
                scaledX - BORDER_THICKNESS,
                scaledY - BORDER_THICKNESS,
                scaledWidth + BORDER_THICKNESS * 2,
                scaledHeight + BORDER_THICKNESS * 2
            );
            shapeRenderer.end();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Color bgColor = new Color(251 / 255f, 214 / 255f, 110 / 255f, message.alpha * 0.8f);
            shapeRenderer.setColor(bgColor);
            shapeRenderer.rect(scaledX, scaledY, scaledWidth, scaledHeight);
            shapeRenderer.end();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Color stripColor = new Color(message.color.r, message.color.g, message.color.b, message.alpha);
            shapeRenderer.setColor(stripColor);
            shapeRenderer.rect(scaledX, scaledY, 10 * scale, scaledHeight);
            shapeRenderer.end();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.setProjectionMatrix(hudCamera.combined);

        for (int i = 0; i < activeMessages.size; i++) {
            Message message = activeMessages.get(i);

            if (message.scale < 0.01f) continue;

            float verticalPosition = i * (MESSAGE_HEIGHT + MESSAGE_SPACING);
            float textY = startY - verticalPosition + (float)MESSAGE_HEIGHT / 2 - 40;

            font.setColor(message.color.r, message.color.g, message.color.b, message.alpha);

            font.draw(batch, message.text, PADDING_X + 20, textY);

            // draw title if you want with large font

        }

        batch.setProjectionMatrix(originalProjection);
    }

    public static void dispose() {
        if (font != null) {
            font.dispose();
        }
        if (largeFont != null) {
            largeFont.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
