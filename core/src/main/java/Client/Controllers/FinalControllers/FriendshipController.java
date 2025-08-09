package Client.Controllers.FinalControllers;

import Client.Assets.SlotAsset;
import Client.Network.ClientNetworkManager;
import Common.Models.FriendShip.Gift;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.FriendShip.Friendship;
import Common.Models.FriendShip.MessageFriend;
import Common.Models.Item;
import Common.Models.PlayerStuff.Gender;
import Common.Models.PlayerStuff.Player;
import Client.Main;
import Common.Network.Messages.MessageTypes.AddXpMessage;
import Common.Network.Messages.MessageTypes.MessageSendMessage;
import Common.Network.Messages.MessageTypes.SendGiftMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
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

    private boolean giftClickHandled = false;

    private final Stage stage;
    private InputProcessor previousInputProcessor;

    private final Sprite menuBox = new Sprite(new Texture("friendship/menuBox.png"));
    private final Sprite maleIcon = new Sprite(new Texture("friendship/male.png"));
    private final Sprite femaleIcon = new Sprite(new Texture("friendship/female.png"));
    private final Sprite fullHeart = new Sprite(new Texture("friendship/fullHeart.png"));
    private final Sprite emptyHeart = new Sprite(new Texture("friendship/emptyHeart.png"));
    private final Sprite giftIcon = new Sprite(new Texture("friendship/giftIcon.png"));

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

    private boolean fKeyHandled = false;

    public FriendshipController() {
        this.stage = new Stage(new ScreenViewport());

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

        // Message field
        messageField = new TextField("", Main.getInstance().getSkin());
        messageField.setMessageText("Write message...");
        messageField.setMaxLength(200);
        messageField.setSize(500, 60);
        stage.addActor(messageField);

        // Recipient dropdown
        recipientBox = new SelectBox<>(Main.getInstance().getSkin());
        recipientBox.setSize(200, 60); // a bit wider for names
        stage.addActor(recipientBox);

        // Send button
        sendButton = new TextButton("Send", Main.getInstance().getSkin());
        sendButton.setSize(120, 60);
        stage.addActor(sendButton);

        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                if(messageField.getText().isEmpty()) {
                    MessageSystem.showMessage("Message Box is empty.",2f, Color.RED);
                    return;
                }
                attemptSendMessage();
            }
        });
    }

    public void checkFKeyPress() {
        if (Gdx.input.isKeyJustPressed(App.getInstance().getKeySetting().getFriendshipMenu()) && !fKeyHandled) {
            fKeyHandled = true;
            toggleVisibility();
        } else if (!Gdx.input.isKeyPressed(App.getInstance().getKeySetting().getFriendshipMenu())) {
            fKeyHandled = false;
        }
    }

    public void render(float delta) {
        if (!showMenu) return;
        updateUIState();
        SpriteBatch batch = (SpriteBatch) stage.getBatch();
        batch.begin();
        renderSprites(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }


    public void toggleVisibility() {
        showMenu = !showMenu;
        if (showMenu) {
            previousInputProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(stage);
            updateUIState();
        } else {
            Gdx.input.setInputProcessor(previousInputProcessor);
        }
    }

    private void updateUIState() {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Friendship> friendships = currentPlayer.getFriendships();
        if (friendships == null || friendships.isEmpty()) return;

        String[] names = friendships.stream()
            .map(f -> f.getPlayer().getName())
            .toArray(String[]::new);
        recipientBox.setItems(names);

        int boxWidth = 1024;
        int boxHeight = 768;
        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        float msgRowY = centerY + 55;
        float msgBoxX = centerX + 140;

        messageField.setPosition(msgBoxX - 35, msgRowY );
        recipientBox.setPosition(messageField.getX() + messageField.getWidth() + 10, msgRowY );
        sendButton.setPosition(recipientBox.getX() + recipientBox.getWidth() + 10, msgRowY );
    }

    private void renderSprites(SpriteBatch batch) {
        if (!showMenu) return;

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Friendship> friendships = currentPlayer.getFriendships();
        if (friendships == null || friendships.isEmpty()) return;

        int boxWidth = 1024;
        int boxHeight = 768;
        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        batch.draw(menuBox, centerX, centerY, boxWidth, boxHeight);

        int columns = 3;
        int rowHeight = boxHeight / 4;
        int columnWidth = boxWidth / columns;

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
            int giftSize = 48;
            float giftX = colX + (columnWidth - giftSize) / 2f;

            giftIcon.setSize(giftSize, giftSize);
            giftIcon.setPosition(giftX, giftY);
            giftIcon.draw(batch);

            mouseX = Gdx.input.getX();
            mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            boolean giftHovered = mouseX >= giftX && mouseX <= giftX + giftSize &&
                mouseY >= giftY && mouseY <= giftY + giftSize;

            if (giftHovered) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    if (!giftClickHandled) {
                        giftClickHandled = true;
                        if (i < friendships.size()) {
                            Player receiver = friendships.get(i).getPlayer();
                            sendItem(i, receiver);
                        }
                    }
                } else {
                    giftClickHandled = false;
                }
            }
        }
    }


    private void sendItem(int slotIndex, Player receiver) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        if (slotIndex < 0 || slotIndex >= giftItems.size()) {
            MessageSystem.showMessage("Invalid slot.", 2f, Color.RED);
            return;
        }

        Item item = giftItems.get(slotIndex);
        if (item == null) {
            MessageSystem.showMessage("No item in that gift slot.", 2f, Color.RED);
            return;
        }

        if (receiver == null) {
            MessageSystem.showMessage("No recipient selected.", 2f, Color.RED);
            return;
        }

        // Create the Gift
        Gift gift = new Gift(
            currentPlayer,
            receiver,
            item.copyItem(item.getNumber()), // or copy what you intend to send; adjust quantity logic
            false
        );

        // Deliver: assume receiver has a list to receive gifts; adjust accessor if different
        try {
            if(App.getInstance().getCurrentGame().isOnline()){
                ClientNetworkManager.getInstance().sendMessage(new SendGiftMessage(
                    currentPlayer.getName(),
                    receiver.getName(),
                    item.getName(),
                    item.getNumber(),
                    gift.getSeed()
                ));
            }
            receiver.getRecievedGifts().add(gift);
            currentPlayer.getSendedGifts().add(gift);
        } catch (Exception e) {
            MessageSystem.showMessage("Failed to send gift.", 2f, Color.RED);
            return;
        }

        // Optionally mark notification / adjust friendship XP
        addXpToPlayers(receiver, 50); // example: give XP for sending a gift

        // Remove or decrement the item from the slot
        // If you only sent one unit:
        item.setNumber(item.getNumber() - 1);
        if (item.getNumber() <= 0) {
            giftItems.set(slotIndex, null);
        }

        MessageSystem.showMessage("Gift sent to " + receiver.getName() + "!", 2f, Color.GREEN);
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

        MessageFriend msg = new MessageFriend(
            currentPlayer,
            receiver,
            text,
            false
        );

        // deliver; Player has receiveMessage(Message)
        if(App.getInstance().getCurrentGame().isOnline()){
            ClientNetworkManager.getInstance().sendMessage(new MessageSendMessage(
                currentPlayer.getName(),
                receiver.getName(),
                text
                )
            );
        }
        receiver.getRecievedMessages().add(msg);

        // Clear after send
        messageField.setText("");

        //Add XPs:
        addXpToPlayers(receiver,20);
    }

    public Friendship getFriendship(Player player, Player goal){
        return player.getFriendships().stream().filter(f -> f.getPlayer().equals(goal)).findFirst().orElse(null);
    }

    public void addXpToPlayers(Player player, int xp) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        try {
            Friendship f1 = getFriendship(currentPlayer, player);
            if (f1 != null) f1.setXp(f1.getXp() + xp);
        } catch (Exception e) {
            System.err.println("Failed to add xp to player");
        }

        try {
            Friendship f2 = getFriendship(player, currentPlayer);
            if (f2 != null) f2.setXp(f2.getXp() + xp);
        } catch (Exception e) {
            System.err.println("Failed to add xp to player");
        }

        if(App.getInstance().getCurrentGame().isOnline()){
            ClientNetworkManager.getInstance().sendMessage(new AddXpMessage(
                currentPlayer.getName(),
                player.getName(),
                xp
            ));
        }
    }


    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

}
