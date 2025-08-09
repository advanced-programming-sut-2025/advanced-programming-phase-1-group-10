package Client.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EmotionMenuSystem {

    private static boolean visible = false;
    private static float alpha = 0f;
    private static OrthographicCamera hudCamera;
    private static BitmapFont font;
    private static BitmapFont textFieldFont;
    private static ShapeRenderer shapeRenderer;

    private static float menuX, menuY;
    private static float menuWidth = 600;
    private static float menuHeight = 400;
    private static float itemSize = 90;
    private static float itemSpacing = 15;
    private static float itemsPerRow = 5;

    private static List<EmotionItem> emotionItems = new ArrayList<>();

    private static Consumer<EmotionItem> onItemSelected;

    private static List<EmotionItem> emojiItems = new ArrayList<>();
    private static Texture[] emojiTextures;

    private static float emojiItemSize = 50;


    private static String typedText = "";
    private static float textBoxX, textBoxY, textBoxWidth = 400, textBoxHeight = 40;
    private static float textPaddingLeft = 15;
    private static float sendButtonWidth = 80, sendButtonHeight = 40;
    private static boolean typingActive = false;


    private static long lastBlinkTime = 0;
    private static boolean showCursor = true;
    private static final long BLINK_INTERVAL = 500;

    public static void initialize() {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));


            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = 17;
            param.color = Color.BLACK;
            font = generator.generateFont(param);


            FreeTypeFontGenerator.FreeTypeFontParameter textFieldParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
            textFieldParam.size = 24;
            textFieldParam.color = Color.BLACK;
            textFieldFont = generator.generateFont(textFieldParam);

            generator.dispose();

            shapeRenderer = new ShapeRenderer();

            hudCamera = new OrthographicCamera();
            hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            initializeEmotionItems();
            initializeEmojiItems();
        } catch (Exception e) {
            System.err.println("Error initializing EmotionMenuSystem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeEmotionItems() {
        emotionItems.add(new EmotionItem("Hello!"));
        emotionItems.add(new EmotionItem("Goodbye!"));
        emotionItems.add(new EmotionItem("Good job!"));
        emotionItems.add(new EmotionItem("Let's go!"));
        emotionItems.add(new EmotionItem("Thank you!"));
        emotionItems.add(new EmotionItem("Sorry!"));
        emotionItems.add(new EmotionItem("Help me!"));
        emotionItems.add(new EmotionItem("Wait for me!"));
        emotionItems.add(new EmotionItem("I'm ready!"));
        emotionItems.add(new EmotionItem("Nice!"));
    }

    private static void initializeEmojiItems() {
        String[] emojiPaths = new String[] {
                "emoji/clap.png",
                "emoji/heart.png",
                "emoji/smile.png",
                "emoji/thumbsup.png"
        };

        emojiTextures = new Texture[emojiPaths.length];
        for (int i = 0; i < emojiPaths.length; i++) {
            try {
                emojiTextures[i] = new Texture(Gdx.files.internal(emojiPaths[i]));
            } catch (Exception e) {
                System.err.println("Error loading emoji texture: " + e.getMessage());

                Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.WHITE);
                pixmap.fill();
                emojiTextures[i] = new Texture(pixmap);
                pixmap.dispose();
            }
            emojiItems.add(new EmotionItem("emoji" + i));
        }
    }

    public static void show(Consumer<EmotionItem> callback) {
        visible = true;
        alpha = 0f;
        onItemSelected = callback;
        typedText = "";
        typingActive = false;

        menuX = (Gdx.graphics.getWidth() - menuWidth) / 2;
        menuY = (Gdx.graphics.getHeight() - menuHeight) / 2;


        textBoxX = menuX + 20;
        textBoxY = menuY + 70;


        lastBlinkTime = TimeUtils.millis();
        showCursor = true;
    }

    public static void hide() {
        visible = false;
        typingActive = false;
    }

    public static void update(SpriteBatch batch, Viewport viewport, float delta) {
        if (!visible) return;


        if (typingActive && TimeUtils.millis() - lastBlinkTime > BLINK_INTERVAL) {
            showCursor = !showCursor;
            lastBlinkTime = TimeUtils.millis();
        }

        hudCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        hudCamera.update();

        alpha = MathUtils.lerp(alpha, 1f, 0.1f);

        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();


        boolean batchWasDrawing = false;
        if (batch.isDrawing()) {
            batchWasDrawing = true;
            batch.end();
        }


        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f * alpha));
        shapeRenderer.rect(0, 0, screenWidth, screenHeight);
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(251 / 255f, 214 / 255f, 110 / 255f, alpha));
        shapeRenderer.rect(menuX, menuY, menuWidth, menuHeight);
        shapeRenderer.end();


        batch.begin();
        batch.setProjectionMatrix(hudCamera.combined);
        font.setColor(0.2f, 0.2f, 0.2f, alpha);
        GlyphLayout titleLayout = new GlyphLayout(font, "Quick Reactions");
        font.draw(batch, titleLayout, menuX + (menuWidth - titleLayout.width) / 2, menuY + menuHeight - 25);
        batch.end();


        int itemsCount = emotionItems.size();
        int rows = (int) Math.ceil(itemsCount / itemsPerRow);

        float startX = menuX + (menuWidth - (itemsPerRow * itemSize + (itemsPerRow - 1) * itemSpacing)) / 2;
        float startY = menuY + menuHeight - 60 - itemSize;

        for (int i = 0; i < itemsCount; i++) {
            EmotionItem item = emotionItems.get(i);
            int row = i / (int) itemsPerRow;
            int col = i % (int) itemsPerRow;

            float x = startX + col * (itemSize + itemSpacing);
            float y = startY - row * (itemSize + itemSpacing);


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1f, 1f, 1f, 0.8f * alpha));
            shapeRenderer.rect(x, y, itemSize, itemSize);
            shapeRenderer.end();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(0.7f, 0.7f, 0.7f, alpha));
            shapeRenderer.rect(x, y, itemSize, itemSize);
            shapeRenderer.end();


            batch.begin();
            font.setColor(0.2f, 0.2f, 0.2f, alpha);
            GlyphLayout textLayout = new GlyphLayout(font, item.getText());
            if (textLayout.width > itemSize - 10) {
                String shortenedText = item.getText().substring(0, Math.min(8, item.getText().length())) + "...";
                textLayout = new GlyphLayout(font, shortenedText);
            }
            font.draw(batch, textLayout, x + (itemSize - textLayout.width) / 2, y + itemSize / 2 + textLayout.height / 2);
            batch.end();

            item.setBounds(x, y, itemSize, itemSize);
        }


        int emojiCount = emojiItems.size();
        float emojiStartX = menuX + (menuWidth - (emojiCount * emojiItemSize + (emojiCount - 1) * itemSpacing)) / 2;
        float emojiY = menuY + 20;

        for (int i = 0; i < emojiCount; i++) {
            float x = emojiStartX + i * (emojiItemSize + itemSpacing);


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1f, 1f, 1f, 0.8f * alpha));
            shapeRenderer.rect(x, emojiY, emojiItemSize, emojiItemSize);
            shapeRenderer.end();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(0.7f, 0.7f, 0.7f, alpha));
            shapeRenderer.rect(x, emojiY, emojiItemSize, emojiItemSize);
            shapeRenderer.end();


            batch.begin();
            if (i < emojiTextures.length && emojiTextures[i] != null) {
                batch.draw(emojiTextures[i], x + 5, emojiY + 5, emojiItemSize - 10, emojiItemSize - 10);
            }
            batch.end();

            emojiItems.get(i).setBounds(x, emojiY, emojiItemSize, emojiItemSize);
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(typingActive ? new Color(0.95f, 0.95f, 1f, 0.9f * alpha) : new Color(1f, 1f, 1f, 0.9f * alpha));
        shapeRenderer.rect(textBoxX, textBoxY, textBoxWidth, textBoxHeight);
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(typingActive ? new Color(0.4f, 0.6f, 1f, alpha) : new Color(0.7f, 0.7f, 0.7f, alpha));
        shapeRenderer.rect(textBoxX, textBoxY, textBoxWidth, textBoxHeight);
        shapeRenderer.end();


        batch.begin();
        textFieldFont.setColor(0.1f, 0.1f, 0.1f, alpha);
        String displayText = typedText;
        if (typingActive && showCursor) {
            displayText += "|";
        }

        if (typedText.isEmpty() && !typingActive) {
            textFieldFont.setColor(0.5f, 0.5f, 0.5f, alpha);
            textFieldFont.draw(batch, "Type here...", textBoxX + textPaddingLeft, textBoxY + textBoxHeight / 2 + textFieldFont.getCapHeight() / 2);
        } else {
            textFieldFont.draw(batch, displayText, textBoxX + textPaddingLeft, textBoxY + textBoxHeight / 2 + textFieldFont.getCapHeight() / 2);
        }
        batch.end();


        float sendButtonX = textBoxX + textBoxWidth + 10;
        float sendButtonY = textBoxY;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.3f, 0.6f, 0.3f, alpha));
        shapeRenderer.rect(sendButtonX, sendButtonY, sendButtonWidth, sendButtonHeight);
        shapeRenderer.end();


        batch.begin();
        font.setColor(1f, 1f, 1f, alpha);
        GlyphLayout sendLayout = new GlyphLayout(font, "Send");
        font.draw(batch, sendLayout, sendButtonX + (sendButtonWidth - sendLayout.width) / 2,
                sendButtonY + (sendButtonHeight + sendLayout.height) / 2);
        batch.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.9f, 0.3f, 0.3f, alpha));
        float closeButtonSize = 30;
        float closeButtonX = menuX + menuWidth - closeButtonSize - 10;
        float closeButtonY = menuY + menuHeight - closeButtonSize - 10;
        shapeRenderer.rect(closeButtonX, closeButtonY, closeButtonSize, closeButtonSize);
        shapeRenderer.end();


        batch.begin();
        font.setColor(1f, 1f, 1f, alpha);
        GlyphLayout closeLayout = new GlyphLayout(font, "X");
        font.draw(batch, closeLayout, closeButtonX + (closeButtonSize - closeLayout.width) / 2,
                closeButtonY + (closeButtonSize + closeLayout.height) / 2);
        batch.end();


        if (batchWasDrawing) {
            batch.begin();
        }


        handleInput(viewport);
    }

    private static void handleInput(Viewport viewport) {
        if (!visible || !Gdx.input.justTouched()) return;

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        hudCamera.unproject(touchPos);

        float closeButtonSize = 30;
        float closeButtonX = menuX + menuWidth - closeButtonSize - 10;
        float closeButtonY = menuY + menuHeight - closeButtonSize - 10;


        if (touchPos.x >= closeButtonX && touchPos.x <= closeButtonX + closeButtonSize &&
                touchPos.y >= closeButtonY && touchPos.y <= closeButtonY + closeButtonSize) {
            hide();
            return;
        }


        if (touchPos.x >= textBoxX && touchPos.x <= textBoxX + textBoxWidth &&
                touchPos.y >= textBoxY && touchPos.y <= textBoxY + textBoxHeight) {
            typingActive = true;

            lastBlinkTime = TimeUtils.millis();
            showCursor = true;
            return;
        } else {

            typingActive = false;
        }


        float sendButtonX = textBoxX + textBoxWidth + 10;
        float sendButtonY = textBoxY;
        if (touchPos.x >= sendButtonX && touchPos.x <= sendButtonX + sendButtonWidth &&
                touchPos.y >= sendButtonY && touchPos.y <= sendButtonY + sendButtonHeight) {
            if (!typedText.isEmpty() && onItemSelected != null) {
                onItemSelected.accept(new EmotionItem(typedText));
                hide();
            }
            return;
        }


        for (EmotionItem item : emotionItems) {
            if (touchPos.x >= item.getX() && touchPos.x <= item.getX() + item.getWidth() &&
                    touchPos.y >= item.getY() && touchPos.y <= item.getY() + item.getHeight()) {

                if (onItemSelected != null) onItemSelected.accept(item);
                hide();
                return;
            }
        }


        for (EmotionItem item : emojiItems) {
            if (touchPos.x >= item.getX() && touchPos.x <= item.getX() + item.getWidth() &&
                    touchPos.y >= item.getY() && touchPos.y <= item.getY() + item.getHeight()) {

                if (onItemSelected != null) onItemSelected.accept(item);
                hide();
                return;
            }
        }


        if (touchPos.x < menuX || touchPos.x > menuX + menuWidth ||
                touchPos.y < menuY || touchPos.y > menuY + menuHeight) {
            hide();
        }
    }


    public static void handleKeyTyped(char character) {
        if (!visible || !typingActive) return;

        System.out.println("Key typed: " + (int)character);

        if (character == '\b') {

            if (typedText.length() > 0) {
                typedText = typedText.substring(0, typedText.length() - 1);
            }
        } else if (character == '\r' || character == '\n') {

            if (!typedText.isEmpty() && onItemSelected != null) {
                onItemSelected.accept(new EmotionItem(typedText));
                hide();
            }
        } else if (character >= 32 && character < 127) {

            typedText += character;
            if (typedText.length() > 10) {
                typedText = typedText.substring(0, 10);
                MessageSystem.showError("You can type 10 characters!", 2.0f);
            }
        }


        lastBlinkTime = TimeUtils.millis();
        showCursor = true;
    }

    public static boolean isVisible() {
        return visible;
    }

    public static boolean isTypingActive() {
        return typingActive;
    }

    public static void dispose() {
        if (font != null) font.dispose();
        if (textFieldFont != null) textFieldFont.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (emojiTextures != null) {
            for (Texture t : emojiTextures) {
                if (t != null) t.dispose();
            }
        }
    }

    public static class EmotionItem {
        private String text;
        private float x, y, width, height;

        public EmotionItem(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setBounds(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }
}
