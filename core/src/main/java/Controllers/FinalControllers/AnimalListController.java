package Controllers.FinalControllers;

import Assets.AnimalAsset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

public class AnimalListController {

    private Texture backgroundTexture;
    private boolean isShowing;
    private AnimalAsset animalAsset;
    private BitmapFont animalNameFont;
    private List<AnimalDisplayData> animalsToDisplay;

    private float listX;
    private float listY;
    private float listWidth;
    private float listHeight;

    private static final int SLOTS_PER_ROW = 4;
    private static final int TOTAL_SLOTS = 8;

    public AnimalListController(AnimalAsset animalAsset) {
        this.animalAsset = animalAsset;
        this.isShowing = false;
        this.backgroundTexture = new Texture(Gdx.files.internal("Animals/list.png"));

        initializeFonts();
        updateListPosition();
        loadAnimalsToDisplay();
    }

    private void initializeFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 63;
        param.color = Color.BLACK;
        animalNameFont = generator.generateFont(param);
        generator.dispose();
    }

    private void updateListPosition() {
        float textureOriginalWidth = backgroundTexture.getWidth();
        float textureOriginalHeight = backgroundTexture.getHeight();

        listWidth = textureOriginalWidth;
        listHeight = textureOriginalHeight;

        listX = (Gdx.graphics.getWidth() - listWidth) / 2f;
        listY = (Gdx.graphics.getHeight() - listHeight) / 2f;
    }

    private void loadAnimalsToDisplay() {
        animalsToDisplay = new ArrayList<>();
        String[] animalNames = {
            "Chicken", "Duck", "Rabbit", "Dinosaur",
            "Cow", "Goat", "Sheep", "Pig"
        };

        for (String name : animalNames) {
            animalsToDisplay.add(new AnimalDisplayData(name));
        }
    }

    public void update(SpriteBatch batch) {
        if (!isShowing) return;

        batch.draw(backgroundTexture, listX, listY, listWidth, listHeight);

        float slotWidth = listWidth / SLOTS_PER_ROW;
        float slotHeight = listHeight / 2;

        for (int i = 0; i < TOTAL_SLOTS; i++) {
            int row = i / SLOTS_PER_ROW;
            int col = i % SLOTS_PER_ROW;

            float slotX = listX + col * slotWidth;
            float slotY = listY + (1 - row) * slotHeight;

            if (i < animalsToDisplay.size()) {
                String animalName = animalsToDisplay.get(i).name;
                Texture animalTexture = animalAsset.getSingleTexture(animalName);


                if (animalTexture != null) {
                    float imageMargin = slotWidth * 0.1f;
                    float imageWidth = slotWidth - 2 * imageMargin;
                    float imageHeight = slotHeight * 0.45f;
                    float imageX = slotX + imageMargin;
                    float imageY = slotY + slotHeight * 0.42f;

                    batch.draw(animalTexture, imageX, imageY, imageWidth, imageHeight);
                }


                GlyphLayout layout = new GlyphLayout(animalNameFont, animalName);
                float textX = slotX + (slotWidth - layout.width) / 2;
                float textY = slotY + slotHeight - 495;

                animalNameFont.draw(batch, animalName, textX, textY);
            }
        }
    }

    public void toggleVisibility() {
        isShowing = !isShowing;
        if (isShowing) {
            updateListPosition();
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void dispose() {
        backgroundTexture.dispose();
        animalNameFont.dispose();
    }

    private static class AnimalDisplayData {
        String name;

        public AnimalDisplayData(String name) {
            this.name = name;
        }
    }
}
