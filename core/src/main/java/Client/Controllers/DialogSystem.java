package Client.Controllers;

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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DialogSystem {

    private static boolean visible = false;
    private static String dialogText;
    private static Runnable yesAction;
    private static Runnable noAction;
    private static float alpha = 0f;

    private static BitmapFont font;
    private static ShapeRenderer shapeRenderer;
    private static OrthographicCamera hudCamera;

    // you can change size of box here
    private static float dialogWidth = 600;
    private static float dialogHeight = 300;

    private static float buttonWidth = 150;
    private static float buttonHeight = 60;

    private static float yesX, yesY, noX, noY;
    private static float dialogX, dialogY;

    public static void initialize() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 40;
        param.color = Color.BROWN;
        font = generator.generateFont(param);

        generator.dispose();

        shapeRenderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static void show(String text, Runnable yes, Runnable no) {
        dialogText = text;
        yesAction = yes;
        noAction = no;
        visible = true;
        alpha = 0f;
    }

    public static void hide() {
        visible = false;
    }

    public static void update(SpriteBatch batch, Viewport viewport) {
        if (!visible) return;

        hudCamera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        hudCamera.update();

        alpha = MathUtils.lerp(alpha, 1f, 0.1f);
        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();

        dialogX = (screenWidth - dialogWidth) / 2;
        dialogY = (screenHeight - dialogHeight) / 2;

        yesX = dialogX + 50;
        yesY = dialogY + 40;
        noX = dialogX + dialogWidth - buttonWidth - 50;
        noY = dialogY + 40;


        boolean batchWasDrawing = batch.isDrawing();
        if (batchWasDrawing) {
            batch.end();
        }

        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.4f * alpha));
        shapeRenderer.rect(0, 0, screenWidth, screenHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(251 / 255f, 214 / 255f, 110 / 255f, alpha));
        shapeRenderer.rect(dialogX, dialogY, dialogWidth, dialogHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.5f, 0.9f, 0.5f, alpha));
        shapeRenderer.rect(yesX, yesY, buttonWidth, buttonHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.9f, 0.5f, 0.5f, alpha));
        shapeRenderer.rect(noX, noY, buttonWidth, buttonHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0.2f, 0.6f, 0.2f, alpha));
        shapeRenderer.rect(yesX, yesY, buttonWidth, buttonHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0.6f, 0.2f, 0.2f, alpha));
        shapeRenderer.rect(noX, noY, buttonWidth, buttonHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.setProjectionMatrix(hudCamera.combined);

        font.setColor(0.1f, 0.1f, 0.1f, alpha);

        GlyphLayout layout = new GlyphLayout(font, dialogText);
        font.draw(batch, layout, dialogX + (dialogWidth - layout.width) / 2, dialogY + dialogHeight - 50);

        GlyphLayout yesLayout = new GlyphLayout(font, "Yes");
        font.setColor(0.1f, 0.1f, 0.1f, alpha);
        font.draw(batch, yesLayout,
            yesX + (buttonWidth - yesLayout.width) / 2,
            yesY + (buttonHeight + yesLayout.height) / 2);

        GlyphLayout noLayout = new GlyphLayout(font, "No");
        font.setColor(0.1f, 0.1f, 0.1f, alpha);
        font.draw(batch, noLayout,
            noX + (buttonWidth - noLayout.width) / 2,
            noY + (buttonHeight + noLayout.height) / 2);

        batch.end();


        if (batchWasDrawing) {
            batch.begin();
        }
        handleInput(viewport);
    }

    private static void handleInput(Viewport viewport) {
        if (!visible) return;

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            hudCamera.unproject(touchPos);


            if (touchPos.x >= yesX && touchPos.x <= yesX + buttonWidth &&
                touchPos.y >= yesY && touchPos.y <= yesY + buttonHeight) {
                if (yesAction != null) {
                    yesAction.run();
                }
                hide();
                return;
            }

            if (touchPos.x >= noX && touchPos.x <= noX + buttonWidth &&
                touchPos.y >= noY && touchPos.y <= noY + buttonHeight) {
                if (noAction != null) {
                    noAction.run();
                }
                hide();
                return;
            }
        }
    }

    public static void dispose() {
        if (font != null) font.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
