package Client.Controllers.FinalControllers;

import Client.Assets.SlotAsset;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Item;
import Common.Models.NPC.NPC;
import Common.Models.NPCRelation;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

public class NpcMenuController {

    private final Sprite menuBox = new Sprite(new Texture("npcMenu/menuBox.png"));
    private final Sprite fullHeart = new Sprite(new Texture("npcMenu/fullHeart.png"));
    private final Sprite emptyHeart = new Sprite(new Texture("npcMenu/emptyHeart.png"));
    private final Sprite giftIcon = new Sprite(new Texture("npcMenu/giftIcon.png"));
    private final SlotAsset slotAsset = new SlotAsset();


    private final List<Item> giftItems = new ArrayList<>();


    private int hoveredSlotIndex = -1; // -1 means none is hovered


    private final BitmapFont font;
    private boolean showMenu = false;

    public NpcMenuController() {

        int npcCount = App.getInstance().getCurrentGame().getNpcs().size();
        for (int i = 0; i < npcCount; i++) {
            giftItems.add(null); // placeholder for each NPC slot
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 30;
        param.color = Color.BROWN;
        font = generator.generateFont(param);
        generator.dispose();
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(App.getInstance().getKeySetting().getOpenNPCMenu())) {
            showMenu = !showMenu;
        }

        if (!showMenu) return;

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<NPCRelation> npcRelations = player.getNpcRelations();

        int boxWidth = menuBox.getTexture().getWidth() * 2;
        int boxHeight = menuBox.getTexture().getHeight() * 2;

        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        batch.draw(menuBox, centerX, centerY, boxWidth, boxHeight);

        int rowStartX = centerX + 20;
        int rowStartY = centerY + boxHeight - 100;
        int rowHeight = 100;
        int iconSize = 100;

        // Handle mouse click (only once per click)
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // invert Y

            hoveredSlotIndex = -1; // reset hover

            for (int i = 0; i < npcRelations.size(); i++) {
                int rowY = rowStartY - i * (rowHeight + 35);
                int giftX = rowStartX + 600;
                int slotX = giftX + 120;
                int slotY = rowY - 5;

                int slotWidth = 64;
                int slotHeight = 64;

                if (mouseX >= slotX && mouseX <= slotX + slotWidth &&
                    mouseY >= slotY && mouseY <= slotY + slotHeight) {
                    hoveredSlotIndex = i;
                    break;
                }
            }
        }

        for (int i = 0; i < npcRelations.size(); i++) {
            NPCRelation relation = npcRelations.get(i);
            NPC npc = relation.getNpc();

            int padding = 35;
            int rowY = rowStartY - i * (rowHeight + padding);

            Sprite npcIcon = npc.show();
            if (npcIcon != null) {
                batch.draw(npcIcon, rowStartX, rowY, iconSize, iconSize);
            }

            font.draw(batch, npc.getName(), rowStartX + iconSize + 10, rowY + 40);

            int heartsX = rowStartX + 320;
            int level = relation.getFrinendShipLevel();
            for (int h = 0; h < NPCRelation.MAX_RELATION_LEVEL; h++) {
                Sprite heart = (h < level) ? fullHeart : emptyHeart;
                batch.draw(heart, heartsX + h * 40, rowY + 10, 32, 32);
            }

            int giftX = rowStartX + 600;
            batch.draw(giftIcon, giftX, rowY, 80, 80);

            // Detect mouse click on gift icon
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Y is flipped

                if (mouseX >= giftX && mouseX <= giftX + 80 &&
                    mouseY >= rowY && mouseY <= rowY + 80) {

                    Item giftItem = giftItems.get(i); // i = row index
                    if (giftItem != null) {
                        sendGift(npc, giftItem);
                        giftItems.set(i, null); // Clear gift slot
                    }
                }
            }


            int slotX = giftX + 120;
            int slotY = rowY - 5;

            batch.setColor(Color.LIGHT_GRAY);
            batch.draw(slotAsset.getSlot(), slotX, slotY, 64, 64);
            batch.setColor(Color.WHITE);

            // Draw hoverSlot if this one is hovered
            if (i == hoveredSlotIndex) {
                batch.draw(slotAsset.getSlotHover(), slotX, slotY, 64, 64);
            }

            Item gift = null;
            try {
                gift = giftItems.get(i);
            } catch (Exception ignored) {
            }
            if (gift != null) {
                batch.draw(gift.show(), slotX, slotY, 64, 64);
            }

        }

        if (showMenu && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (hoveredSlotIndex != -1) {
                int rowY = rowStartY - hoveredSlotIndex * (rowHeight + 35);
                int giftX = rowStartX + 600;
                int slotX = giftX + 120;
                int slotY = rowY - 5;

                if (mouseX >= slotX && mouseX <= slotX + 64 &&
                    mouseY >= slotY && mouseY <= slotY + 64) {

                    Item gift = removeGiftItemFromSlot();
                    if (gift != null) {
                        player.getInventory().getBackPack().addItem(gift);
                    }
                }
            }
        }

    }

    public boolean isMenuOpen() {
        return showMenu;
    }

    public int getHoveredSlotIndex() {
        return hoveredSlotIndex;
    }

    public boolean tryAddItemToSlot(Item item) {
        if (hoveredSlotIndex == -1) return false;
        if (giftItems.get(hoveredSlotIndex) != null) return false;
        giftItems.set(hoveredSlotIndex, item);
        return true;
    }

    public Item removeGiftItemFromSlot() {
        if (hoveredSlotIndex == -1) return null;
        Item item = giftItems.get(hoveredSlotIndex);
        giftItems.set(hoveredSlotIndex, null);
        return item;
    }


    public void sendGift(NPC npc, Item item) {

        if (npc == null) {
            MessageSystem.showMessage("NPC not found!", 2f, Color.RED);
            return;
        }

        NPCRelation relation = getNPCRealtion(npc);
        if (relation == null) {
            MessageSystem.showMessage("Talk to the NPC before giving a gift!", 2f, Color.ORANGE);
            return;
        }

        if (item instanceof Tool) {
            MessageSystem.showMessage("You cannot gift tools!", 2f, Color.RED);
            return;
        }

        boolean isFavorite = npc.getFavoriteItems().stream()
            .anyMatch(favItem -> favItem.getName().equals(item.getName()));

        if (isFavorite) {
            relation.setRelationPoint(relation.getRelationPoint() + 200);
            MessageSystem.showMessage("You gave " + npc.getName() + " a favorite item!", 3f, Color.GOLD);
        } else {
            relation.setRelationPoint(relation.getRelationPoint() + 50);
            MessageSystem.showMessage("You gave " + npc.getName() + " a gift.", 3f, Color.LIGHT_GRAY);
        }
    }


    public NPCRelation getNPCRealtion(NPC npc) {
        return App.getInstance().getCurrentGame().getCurrentPlayer().getNpcRelations().stream().filter(npcRelation -> npcRelation.getNpc().equals(npc)).findFirst().orElse(null);
    }


}
