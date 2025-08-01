package Controllers.FinalControllers;

import Controllers.MessageSystem;
import Models.App;
import Models.FriendShip.Gift;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.List;

public class GiftHistoryController {

    private final Sprite background = new Sprite(new Texture("giftHistory/giftHistoryBox.png"));
    private final Sprite fullHeart = new Sprite(new Texture("giftHistory/fullHeart.png"));
    private final Sprite emptyHeart = new Sprite(new Texture("giftHistory/emptyHeart.png"));

    private final BitmapFont font;

    private boolean visible = false;

    // layout constants
    private static final int PADDING = 32;
    private static final int SECTION_SPACING = 16;
    private static final int LINE_HEIGHT = 26;
    private static final int HEART_SIZE = 30;
    private static final int HEART_SPACING = 4;
    private static final int MAX_HEARTS = 5;

    public GiftHistoryController() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 36;
        font = generator.generateFont(param);
        generator.dispose();
    }

    /**
     * Call from render loop. Assumes batch.begin() is already called.
     */
    public void update(SpriteBatch batch) {
        // Toggle with G
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            visible = !visible;
        }

        if (!visible) return;

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        if (currentPlayer == null) return;

        // Compute doubled size based on texture
        float origWidth = background.getWidth();
        float origHeight = background.getHeight();
        float boxWidth = origWidth * 2f;
        float boxHeight = origHeight * 2f;

        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        float x = (screenW - boxWidth) / 2f;
        float y = (screenH - boxHeight) / 2f;

        batch.draw(background, x, y, boxWidth, boxHeight);

        float cursorY = y + boxHeight - PADDING;

        // --- Sent Gifts Section ---
        font.draw(batch, "Sent Gifts:", x + PADDING, cursorY);
        cursorY -= LINE_HEIGHT;

        List<Gift> sent = currentPlayer.getSendedGifts();
        if (sent == null) sent = List.of();
        if (sent.isEmpty()) {
            font.draw(batch, "<none>", x + PADDING, cursorY);
            cursorY -= LINE_HEIGHT;
        } else {
            for (Gift g : sent) {
                String itemName = g.getItem() != null ? g.getItem().getName() : "Unknown";
                int quantity = (g.getItem() != null) ? g.getItem().getNumber() : 0;
                String line = itemName + " x" + quantity + " -> " + g.getReceiver().getName();
                font.draw(batch, line, x + PADDING, cursorY);

                // draw hearts reflecting rating (non-interactive for sender)
                float heartsStartX = x + boxWidth - PADDING - (MAX_HEARTS * (HEART_SIZE + HEART_SPACING));
                float heartY = cursorY - (HEART_SIZE / 2f) - 5;

                double rate = g.getRate();
                int filled = (int) Math.round(rate);
                if (rate > 0 && filled == 0) filled = 1;
                filled = Math.min(filled, MAX_HEARTS);

                for (int h = 0; h < MAX_HEARTS; h++) {
                    float heartX = heartsStartX + h * (HEART_SIZE + HEART_SPACING);
                    Sprite toDraw = (h < filled) ? fullHeart : emptyHeart;
                    toDraw.setSize(HEART_SIZE, HEART_SIZE);
                    toDraw.setPosition(heartX, heartY);
                    toDraw.draw(batch);
                }

                cursorY -= LINE_HEIGHT;
            }
        }

        cursorY -= SECTION_SPACING + 420; // adjust spacing before received section

        // --- Received Gifts Section ---
        font.draw(batch, "Received Gifts:", x + PADDING, cursorY);
        cursorY -= LINE_HEIGHT;

        List<Gift> received = currentPlayer.getRecievedGifts();
        if (received == null) received = List.of();
        if (received.isEmpty()) {
            font.draw(batch, "<none>", x + PADDING, cursorY);
            cursorY -= LINE_HEIGHT;
        } else {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            for (int idx = 0; idx < received.size(); idx++) {
                Gift g = received.get(idx);
                String senderName = (g.getSender() != null) ? g.getSender().getName() : "Unknown";
                String itemName = g.getItem() != null ? g.getItem().getName() : "Unknown";
                int quantity = (g.getItem() != null) ? g.getItem().getNumber() : 0;
                String line = senderName + " sent " + itemName + " x" + quantity;
                font.draw(batch, line, x + PADDING, cursorY);

                // hearts on right (interactive only if current player is receiver and rate == 0)
                float heartsStartX = x + boxWidth - PADDING - (MAX_HEARTS * (HEART_SIZE + HEART_SPACING));
                float heartY = cursorY - (HEART_SIZE / 2f) - 5;

                double rate = g.getRate();
                int filled = (int) Math.round(rate);
                if (rate > 0 && filled == 0) filled = 1;
                filled = Math.min(filled, MAX_HEARTS);

                for (int h = 0; h < MAX_HEARTS; h++) {
                    float heartX = heartsStartX + h * (HEART_SIZE + HEART_SPACING);
                    Sprite toDraw = (h < filled) ? fullHeart : emptyHeart;
                    toDraw.setSize(HEART_SIZE, HEART_SIZE);
                    toDraw.setPosition(heartX, heartY);
                    toDraw.draw(batch);

                    // only receiver rates, and only if not yet rated
                    if (g.getReceiver() != null
                        && g.getReceiver().equals(currentPlayer)
                        && g.getRate() <= 0) {

                        boolean hovered = mouseX >= heartX && mouseX <= heartX + HEART_SIZE &&
                            mouseY >= heartY && mouseY <= heartY + HEART_SIZE;
                        if (hovered && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                            int newRate = h + 1;
                            g.setRate(newRate);
                            propagateRatingToSender(g);
                            MessageSystem.showMessage("You rate a gift from" + senderName, 2f, Color.GREEN);// mirror into sender's sent gift
                        }
                    }
                }

                cursorY -= LINE_HEIGHT + 4;
            }
        }
    }

    private void propagateRatingToSender(Gift receivedGift) {
        if (receivedGift == null) return;
        Player sender = receivedGift.getSender();
        if (sender == null) return;

        List<Gift> sentBySender = sender.getSendedGifts();
        if (sentBySender == null) return;

        // Find the matching sent gift: same receiver, same item name, and not yet rated.
        for (Gift sent : sentBySender) {
            boolean sameReceiver = sent.getReceiver() != null && receivedGift.getReceiver() != null
                && sent.getReceiver().equals(receivedGift.getReceiver());
            boolean sameItem = sent.getItem() != null && receivedGift.getItem() != null
                && sent.getItem().getName().equals(receivedGift.getItem().getName())
                && sent.getItem().getNumber() == receivedGift.getItem().getNumber();
            if (sameReceiver && sameItem) {
                // Only propagate if sender's rate is still zero (avoid overwriting)
                if (sent.getRate() <= 0) {
                    sent.setRate(receivedGift.getRate());
                }
                break;
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        font.dispose();
        background.getTexture().dispose();
        fullHeart.getTexture().dispose();
        emptyHeart.getTexture().dispose();
    }
}
