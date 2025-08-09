package Client.Controllers.FinalControllers;

import Common.Models.App;
import Common.Models.FriendShip.MessageFriend;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.List;

public class ChatHistoryController {

    private final Sprite chatBoxMenu = new Sprite(new Texture("chatBoxMenu/chatBoxMenu.png"));
    private boolean visible = false;
    private final BitmapFont font;

    // padding inside the chat box (doubled)
    private static final int PADDING_X = 40;
    private static final int PADDING_Y = 40;
    private static final int LINE_HEIGHT = 28; // adjusted for readability with bigger box

    public ChatHistoryController() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color  = Color.BLACK;
        param.size = 28;
        font = generator.generateFont(param);

    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(App.getInstance().getKeySetting().getChatHistoryMenu())) {
            visible = !visible;
        }

        if (!visible) return;

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();
        if (currentPlayer == null) return;

        // draw background + text; assumes batch.begin() has already been called
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();

        // original was 600x500, now doubled
        int boxWidth = 600 * 2;   // 1200
        int boxHeight = 500 * 2;  // 1000
        int x = (screenW - boxWidth) / 2;
        int y = (screenH - boxHeight) / 2;

        batch.draw(chatBoxMenu, x, y, boxWidth, boxHeight);

        List<MessageFriend> inbox = currentPlayer.getRecievedMessages();
        if (inbox == null) return;

        float textX = x + PADDING_X;
        float textStartY = y + boxHeight - PADDING_Y;
        int maxLines = (boxHeight - 2 * PADDING_Y) / LINE_HEIGHT;

        int lineCount = 0;
        for (int i = Math.max(0, inbox.size() - maxLines); i < inbox.size(); i++) {
            MessageFriend msg = inbox.get(i);
            String senderName = (msg.getSender() != null) ? msg.getSender().getName() : "Unknown";
            String line = senderName + ": " + msg.getMessage();

            float drawY = textStartY - lineCount * LINE_HEIGHT;
            font.draw(batch, line, textX, drawY);
            lineCount++;
            if (lineCount >= maxLines) break;
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        font.dispose();
        chatBoxMenu.getTexture().dispose();
    }
}
