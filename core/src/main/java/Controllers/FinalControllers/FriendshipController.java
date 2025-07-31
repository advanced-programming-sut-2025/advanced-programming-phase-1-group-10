package Controllers.FinalControllers;

import Assets.SlotAsset;
import Models.App;
import Models.FriendShip.Friendship;
import Models.FriendShip.Message;
import Models.Item;
import Models.PlayerStuff.Gender;
import Models.PlayerStuff.Player;
import com.Fianl.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;
import java.util.List;

public class FriendshipController {

    private final Stage stage;

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

    // UI widgets
    private final TextField messageField;
    private final SelectBox<String> recipientBox;
    private final TextButton sendButton;

    public FriendshipController(Stage stage) {
        this.stage = stage;

        // Font setup
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 36;
        font = generator.generateFont(param);
        generator.dispose();

        // Gift slots init
        for (int i = 0; i < 3; i++) {
            giftItems.add(null);
        }

        // Scene2D UI setup
        // Ensure input multiplexing if other input is used elsewhere; external code can wrap with InputMultiplexer.
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(new ScreenViewport());

        // Message field
        messageField = new TextField("", Main.getInstance().getSkin());
        messageField.setMessageText("Write message...");
        messageField.setMaxLength(200);
        messageField.setSize(500, 40);
        stage.addActor(messageField);

        // Recipient dropdown
        recipientBox = new SelectBox<>(Main.getInstance().getSkin());
        recipientBox.setSize(140, 40); // a bit wider for names
        stage.addActor(recipientBox);

        // Send button
        sendButton = new TextButton("Send", Main.getInstance().getSkin());
        sendButton.setSize(100, 40);
        stage.addActor(sendButton);

        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                attemptSendMessage();
            }
        });
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            showMenu = !showMenu;
        }
        if (!showMenu) return;

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Friendship> friendships = currentPlayer.getFriendships();
        if (friendships == null || friendships.isEmpty()) return;

        // Update recipient dropdown items
        String[] names = friendships.stream()
            .map(f -> f.getPlayer().getName())
            .toArray(String[]::new);
        recipientBox.setItems(names);

        int boxWidth = 1024;
        int boxHeight = 768;
        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        // Position UI widgets
        float msgRowY = centerY + 55;
        float msgBoxX = centerX + 140;
        messageField.setPosition(msgBoxX, msgRowY);
        recipientBox.setPosition(messageField.getX() + messageField.getWidth() + 10, msgRowY);
        sendButton.setPosition(recipientBox.getX() + recipientBox.getWidth() + 10, msgRowY);


        batch.draw(menuBox, centerX, centerY, boxWidth, boxHeight);

        int columns = 3;
        int rowHeight = boxHeight / 4;
        int columnWidth = boxWidth / columns;

        // Row 1: icons/names
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

        // Row 2: hearts
        for (int i = 0; i < friendships.size(); i++) {
            Friendship friendship = friendships.get(i);
            int level = friendship.getLevel();

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

        // Row 3: gift slots
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
                    if (giftItems.get(i) != null) {
                        Item toReturn = giftItems.get(i);
                        currentPlayer.getInventory().getBackPack().addItem(toReturn);
                        giftItems.set(i, null);
                    }
                }
            }

            if (i == selectedSlotIndex) {
                batch.draw(slotAsset.getSlotHover(), slotX, slotY, 80, 80);
            } else {
                batch.draw(slotAsset.getSlot(), slotX, slotY, 80, 80);
            }

            Item itemInSlot = giftItems.get(i);
            if (itemInSlot != null) {
                batch.draw(itemInSlot.show(), slotX + 8, slotY + 8, 64, 64);
            }

            int giftY = centerY + boxHeight - 3 * rowHeight + 40;
            giftIcon.setSize(48, 48);
            giftIcon.setPosition(colX + (columnWidth - 48) / 2f, giftY);
            giftIcon.draw(batch);
        }
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

    private void attemptSendMessage() {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Friendship> friendships = currentPlayer.getFriendships();
        if (friendships == null || friendships.isEmpty()) return;

        String text = messageField.getText().trim();
        if (text.isEmpty()) return;

        int selectedIndex = recipientBox.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= friendships.size()) return;

        Player receiver = friendships.get(selectedIndex).getPlayer();

        Message msg = new Message(
            currentPlayer,
            receiver,
            text,
            false
        );
        // deliver; assumes Player has receiveMessage(Message)
        receiver.getRecievedMessages().add(msg);

        // Clear after send
        messageField.setText("");
    }

    public Friendship getFriendship(Player player, Player goal){
        return player.getFriendships().stream().filter(f -> f.getPlayer().equals(goal)).findFirst().orElse(null);
    }

    public void addXpToPlayers(Player player, int xp){
        Friendship f1 = getFriendship(App.getInstance().getCurrentGame().getCurrentPlayer(), player);
        if (f1 != null) f1.setXp(f1.getXp() + xp);
        Friendship f2 = getFriendship(player, App.getInstance().getCurrentGame().getCurrentPlayer());
        if (f2 != null) f2.setXp(f2.getXp() + xp);
    }

}
