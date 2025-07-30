package Controllers.FinalControllers;

import Models.*;
import Models.NPC.NPC;
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

            meetNPC();

            batch.draw(dialogueBox, x, y, boxWidth, boxHeight);
            batch.draw(npc.show(), x + 864, y + 130, 256, 256);

            String[] lines = wrapText(currentDialogue, 57);
            int lineHeight = 40; // Adjust as needed
            int startY = y + boxHeight - 60;

            for (int i = 0; i < lines.length; i++) {
                font.draw(batch, lines[i], x + 30, startY - i * lineHeight);
            }
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

    public void meetNPC() {
        NPC npc = getNearbyPerson(NPC.class);
        if (npc == null) return;
        NPCRelation relation = getNPCRealtion(npc);
        if (relation == null) {
            relation = new NPCRelation(npc, 0, false, false);
            App.getInstance().getCurrentGame().getCurrentPlayer().getNpcRelations().add(relation);

        }

        relation.setRelationPoint(relation.getRelationPoint() + 20);

    }

    public NPCRelation getNPCRealtion(NPC npc) {
        return App.getInstance().getCurrentGame().getCurrentPlayer().getNpcRelations().stream().filter(npcRelation -> npcRelation.getNpc().equals(npc)).findFirst().orElse(null);
    }

    private String[] wrapText(String text, int maxLineLength) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (line.length() + word.length() + 1 <= maxLineLength) {
                if (!line.isEmpty()) line.append(" ");
                line.append(word);
            } else {
                lines.add(line.toString());
                line = new StringBuilder(word);
            }
        }

        if (!line.isEmpty()) {
            lines.add(line.toString());
        }

        return lines.toArray(new String[0]);
    }


}
