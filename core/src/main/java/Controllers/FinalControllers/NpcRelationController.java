package Controllers.FinalControllers;

import Models.App;
import Models.NPC.NPC;
import Models.Person;
import Models.Position;
import Models.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class NpcRelationController {

    private final Texture dialogueBox = new Texture("dialogue/dialogueBox.png");
    private final Sprite dialogue = new Sprite(new Texture("dialogue/dialogue.png"));
    private final BitmapFont font;
    private final BitmapFont largeFont;

    private boolean showDialogue = false;
    private String currentDialogue = "";

    public NpcRelationController() {
        // Load custom font from TTF
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter paramSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramSmall.size = 30;
        paramSmall.color = Color.BROWN;

        FreeTypeFontGenerator.FreeTypeFontParameter paramLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramLarge.size = 60;
        paramLarge.color = Color.BROWN;

        font = generator.generateFont(paramSmall);       // Dialogue text font
        largeFont = generator.generateFont(paramLarge);  // NPC name font

        generator.dispose(); // Dispose after use to free memory
    }

    public void update(SpriteBatch batch) {
        NPC npc = getNearbyPerson(NPC.class);

        if (npc == null) {
            showDialogue = false;
            return;
        }

        // Always draw interaction prompt
        batch.draw(dialogue, 20, 50);

        // Toggle dialogue on T
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            showDialogue = !showDialogue;
            if (showDialogue) {
                currentDialogue = npc.talk(); // Assume this returns a String
            }
        }

        // Draw dialogue box if toggled
        if (showDialogue) {
            int boxWidth = 1246;
            int boxHeight = 421;
            int x = (Gdx.graphics.getWidth() - boxWidth) / 2;
            int y = 80;

            batch.draw(dialogueBox, x, y, boxWidth, boxHeight);
            batch.draw(npc.show(), x + 864, y + 130, 256, 256);

            font.draw(batch, currentDialogue, x + 30, y + boxHeight - 40);
            largeFont.draw(batch, npc.getName(), x + 920, y + 78);
        }
    }

    public <T extends Person> T getNearbyPerson(Class<T> type) {
        Position position = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();
        int mapWidth = map.length;
        int mapHeight = map[0].length;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            int newX = position.getX() + dx[i];
            int newY = position.getY() + dy[i];

            if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
                Person person = map[newX][newY].getPerson();
                if (type.isInstance(person)) {
                    return type.cast(person);
                }
            }
        }

        return null;
    }
}
