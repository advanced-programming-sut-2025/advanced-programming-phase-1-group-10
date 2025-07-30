package Controllers.FinalControllers;

import Assets.SlotAsset;
import Models.App;
import Models.NPCRelation;
import Models.PlayerStuff.Player;
import Models.NPC.NPC;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class NpcMenuController {

    private final Sprite menuBox = new Sprite(new Texture("npcMenu/menuBox.png"));
    private final Sprite fullHeart = new Sprite(new Texture("npcMenu/fullHeart.png"));
    private final Sprite emptyHeart = new Sprite(new Texture("npcMenu/emptyHeart.png"));
    private final Sprite giftIcon = new Sprite(new Texture("npcMenu/giftIcon.png"));

    private final SlotAsset slotAsset = new SlotAsset();

    private final BitmapFont font;
    private boolean showMenu = false;

    public NpcMenuController() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 30;
        param.color = Color.BROWN;
        font = generator.generateFont(param);
        generator.dispose();
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            showMenu = !showMenu;
        }

        if (!showMenu) return;

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<NPCRelation> npcRelations = player.getNpcRelations();

        int boxWidth = menuBox.getTexture().getWidth() * 2;
        int boxHeight = menuBox.getTexture().getHeight() * 2;

        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        // Draw the menu box centered
        batch.draw(menuBox, centerX, centerY, boxWidth, boxHeight);

        int rowStartX = centerX + 20;
        int rowStartY = centerY + boxHeight - 100;
        int rowHeight = 100;
        int iconSize = 100;

        for (int i = 0; i < npcRelations.size(); i++) {
            NPCRelation relation = npcRelations.get(i);
            NPC npc = relation.getNpc();

            int padding = 35;
            int rowY = rowStartY - i * (rowHeight + padding);

            // Column 1: NPC icon and name
            Sprite npcIcon = npc.show(); // Requires NPC.getIcon()
            if (npcIcon != null) {
                batch.draw(npcIcon, rowStartX, rowY, iconSize, iconSize);
            }

            font.draw(batch, npc.getName(), rowStartX + iconSize + 10, rowY + 40);

            // Column 2: Friendship hearts
            int heartsX = rowStartX + 320;
            int level = relation.getFrinendShipLevel();
            for (int h = 0; h < NPCRelation.MAX_RELATION_LEVEL; h++) {
                Sprite heart = (h < level) ? fullHeart : emptyHeart;
                batch.draw(heart, heartsX + h * 40 , rowY + 10, 32, 32);
            }

            // Column 3: Gift icon + slot (gray box)
            int giftX = rowStartX + 600;
            batch.draw(giftIcon, giftX, rowY, 80, 80);

            int slotX = giftX + 120;
            batch.setColor(Color.LIGHT_GRAY);
            batch.draw(slotAsset.getSlot(), slotX, rowY - 5, 64, 64); // just a placeholder
            batch.setColor(Color.WHITE);
        }
    }
}
