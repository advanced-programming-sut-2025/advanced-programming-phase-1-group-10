package Controllers.FinalControllers;

import Assets.SlotAsset;
import Models.App;
import Models.FriendShip.Friendship;
import Models.Item;
import Models.PlayerStuff.Gender;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

public class FriendshipController {

    private final Sprite menuBox = new Sprite(new Texture("friendship/menuBox.png"));
    private final Sprite maleIcon = new Sprite(new Texture("friendship/male.png"));
    private final Sprite femaleIcon = new Sprite(new Texture("friendship/female.png"));
    private final Sprite fullHeart = new Sprite(new Texture("friendship/fullHeart.png"));
    private final Sprite emptyHeart = new Sprite(new Texture("friendship/emptyHeart.png"));
    private final Sprite giftIcon = new Sprite(new Texture("friendship/giftIcon.png"));
    private final Sprite button = new Sprite(new Texture("friendship/button.png"));

    private final ArrayList<Item> giftItems = new ArrayList<>();
    private final SlotAsset slotAsset = new SlotAsset();

    private final BitmapFont font;
    private boolean showMenu = false;

    private int hoveredSlotIndex = -1;
    private int selectedSlotIndex = -1;

    public FriendshipController() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 36;
        font = generator.generateFont(param);
        generator.dispose();

        for (int i = 0; i < 3; i++) {
            giftItems.add(null);
        }
    }

    public void update(SpriteBatch batch) {
        // Toggle UI with F key
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            showMenu = !showMenu;
        }

        if (!showMenu) return;

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Friendship> friendships = currentPlayer.getFriendships();
        if (friendships == null || friendships.isEmpty()) return;

        int boxWidth = 1024;
        int boxHeight = 768;
        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        // Draw background
        batch.draw(menuBox, centerX, centerY, boxWidth, boxHeight);

        int columns = 3;
        int rowHeight = boxHeight / 4;
        int columnWidth = boxWidth / columns;

        // --- Row 1: Icons + Names ---
        for (int i = 0; i < friendships.size(); i++) {
            Friendship friendship = friendships.get(i);
            Player friend = friendship.getPlayer();

            int colX = centerX + i * columnWidth - 30;
            int iconY = centerY + boxHeight - rowHeight + 30;

            Sprite icon = friend.getGender() == Gender.Male ? maleIcon : femaleIcon;
            icon.setSize(128, 128);
            icon.setPosition(colX + (columnWidth - 64) / 2f, iconY);
            icon.draw(batch);

            font.draw(batch, friend.getName(), colX + (columnWidth / 2f) - 10, iconY - 10);
        }

        // --- Row 2: Friendship Hearts ---
        for (int i = 0; i < friendships.size(); i++) {
            Friendship friendship = friendships.get(i);
            int level = friendship.getLevel(); // 0–4

            int colX = centerX + i * columnWidth;
            int heartY = centerY + boxHeight - 2 * rowHeight + 110;
            int heartSpacing = 10;
            int heartSize = 32;

            for (int h = 0; h < 4; h++) {
                Sprite heart = h < level ? fullHeart : emptyHeart;
                int heartX = colX + (columnWidth - (heartSize * 4 + heartSpacing * 3)) / 2 + h * (heartSize + heartSpacing);
                heart.setSize(heartSize, heartSize);
                heart.setPosition(heartX, heartY);
                heart.draw(batch);
            }
        }

        // --- Row 3: Gift Slots + Icons ---
        hoveredSlotIndex = -1;

        for (int i = 0; i < friendships.size(); i++) {
            int colX = centerX + i * columnWidth;
            int slotY = centerY + boxHeight - 3 * rowHeight + 140;
            float slotX = colX + (columnWidth - 80) / 2f;

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            boolean isHovered = mouseX >= slotX && mouseX <= slotX + 80 &&
                mouseY >= slotY && mouseY <= slotY + 80;

            if (isHovered) {
                hoveredSlotIndex = i;
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    selectedSlotIndex = i;
                }
                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                    // right click on selected slot returns its item to bar
                    if (giftItems.get(i) != null) {
                        Item toReturn = giftItems.get(i);
                        currentPlayer.getInventory().getBackPack().addItem(toReturn); // simple push-back, adjust if stacking logic needed
                        giftItems.set(i, null);
                    }
                }
            }

            // Highlight selected slot
            if (i == selectedSlotIndex) {
                batch.draw(slotAsset.getSlotHover(), slotX, slotY, 80, 80);
            } else {
                batch.draw(slotAsset.getSlot(), slotX, slotY, 80, 80);
            }

            // Draw item if exists
            Item itemInSlot = giftItems.get(i);
            if (itemInSlot != null) {
                batch.draw(itemInSlot.show(), slotX + 8, slotY + 8, 64, 64);
            }

            // Draw gift icon under slot
            int giftY = centerY + boxHeight - 3 * rowHeight + 40;
            giftIcon.setSize(48, 48);
            giftIcon.setPosition(colX + (columnWidth - 48) / 2f, giftY);
            giftIcon.draw(batch);
        }

        // --- Row 4: Message UI ---
        int msgRowY = centerY + 55;
        int msgBoxX = centerX + 140;
        int msgBoxWidth = 500;
        int dropdownX = msgBoxX + msgBoxWidth + 10;
        int buttonX = dropdownX + 110;

        batch.setColor(1f, 1f, 1f, 0.6f);
        batch.draw(menuBox.getTexture(), msgBoxX, msgRowY, msgBoxWidth, 50); // message box
        batch.draw(button.getTexture(), dropdownX, msgRowY, 100, 50);       // dropdown
        batch.draw(button.getTexture(), buttonX, msgRowY, 80, 50);          // send button
        batch.setColor(1f, 1f, 1f, 1f);

        font.draw(batch, "Write message...", msgBoxX + 10, msgRowY + 25);
        font.draw(batch, "To: ", dropdownX + 10, msgRowY + 25);
        font.draw(batch, "Send", buttonX + 15, msgRowY + 25);
    }

    public boolean isMenuOpen() {
        return showMenu;
    }

    public int getHoveredSlotIndex() {
        return hoveredSlotIndex;
    }

    public int getSelectedSlotIndex() {
        return selectedSlotIndex;
    }

    public boolean tryAddItemToSlot(Item item) {
        if (selectedSlotIndex == -1 || giftItems.get(selectedSlotIndex) != null) return false;
        giftItems.set(selectedSlotIndex, item);
        return true;
    }
}
