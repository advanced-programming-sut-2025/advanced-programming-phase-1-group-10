package Client.Controllers.FinalControllers;

import Client.Network.ClientNetworkManager;
import Common.Models.Item;
import Common.Models.Tools.Tool;
import Common.Models.App;
import Common.Network.Messages.MessageTypes.AceeptTradeMessage;
import Common.Network.Messages.MessageTypes.CancelTradeMessage;
import Common.Network.Messages.MessageTypes.ChangeItemTradeMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class TradeController {

    private final Sprite tradeMenu = new Sprite(new Texture("trade/tradeMenu.png"));
    private final Sprite button = new Sprite(new Texture("trade/button.png"));
    private final Sprite slot = new Sprite(new Texture("trade/slot.png"));

    private final BitmapFont font;
    private final BitmapFont indexFont;

    private boolean isShown = false;
    private String goalPlayerName;

    private final ArrayList<Item> myItems = new ArrayList<>();
    private final ArrayList<Item> hisItems = new ArrayList<>();

    public TradeController() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 36;
        font = generator.generateFont(param);
        generator.dispose();

        indexFont = new BitmapFont(); // consider caching instead of recreating/disposing
        indexFont.getData().setScale(1);
        indexFont.setColor(Color.WHITE);
    }

    public void update(SpriteBatch batch) {
        if (!isShown) return;

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isShown = false;
            for(Item item : myItems) {
                App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(item);
            }

            ClientNetworkManager.getInstance().sendMessage(new CancelTradeMessage(
                App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                goalPlayerName

            ));
            goalPlayerName = null;
            hisItems.clear();
            myItems.clear();
        }
        // Fixed menu size
        float menuWidth = 1024;
        float menuHeight = 768;

        // Center on screen
        float menuX = (Gdx.graphics.getWidth() - menuWidth) / 2f;
        float menuY = (Gdx.graphics.getHeight() - menuHeight) / 2f;

        // Draw menu box scaled
        tradeMenu.setBounds(menuX, menuY, menuWidth, menuHeight);
        tradeMenu.draw(batch);

        // Draw titles
        font.draw(batch, "My Items", menuX + 170, menuY + menuHeight - 50);
        font.draw(batch, "His Items", menuX + menuWidth - 300, menuY + menuHeight - 50);

        // Slot capacities
        final int myCapacity = 5;
        final int hisCapacity = 5;

        // Scale slots to fit nicely
        float slotScale = 2f;
        float slotW = slot.getWidth() * slotScale;
        float slotH = slot.getHeight() * slotScale;
        float slotSpacing = slotH + 10;

        // Handle right-click input for myItems slots before drawing (optional, or after draw)
        handleRightClick(menuX, menuY, menuHeight, slotW, slotH, slotSpacing);

        // Left column (My Items)
        for (int i = 0; i < myCapacity; i++) {
            float slotX = menuX + 220;
            float slotY = menuY + menuHeight - 160 - (i * slotSpacing) - slotH;

            batch.draw(slot, slotX, slotY, slotW, slotH);

            Item currentItem = null;
            try {
                currentItem = myItems.get(i);
            } catch (Exception ignored) {
            }

            if (currentItem != null) {
                batch.draw(currentItem.show(), slotX, slotY, slotW, slotH);

                if (currentItem.getNumber() > 1) {
                    float numberX = slotX + slotW - 12;
                    float numberY = slotY + 14;
                    indexFont.draw(batch, String.valueOf(currentItem.getNumber()), numberX, numberY);
                }
            }
        }

        // Right column (His Items)
        for (int i = 0; i < hisCapacity; i++) {
            float slotX = menuX + menuWidth - slotW - 220;
            float slotY = menuY + menuHeight - 160 - (i * slotSpacing) - slotH;

            batch.draw(slot, slotX, slotY, slotW, slotH);

            Item currentItem = null;
            try {
                currentItem = hisItems.get(i);
            } catch (Exception ignored) {
            }

            if (currentItem != null) {
                batch.draw(currentItem.show(), slotX, slotY, slotW, slotH);

                if (currentItem.getNumber() > 1) {
                    float numberX = slotX + slotW - 12;
                    float numberY = slotY + 14;
                    indexFont.draw(batch, String.valueOf(currentItem.getNumber()), numberX, numberY);
                }
            }
        }

        // Button size & position
        float buttonScale = 0.25f;
        float buttonW = button.getWidth() * buttonScale;
        float buttonH = button.getHeight() * buttonScale;
        float buttonX = menuX + (menuWidth - buttonW) / 2f;
        float buttonY = menuY + 30;
        batch.draw(button, buttonX, buttonY, buttonW, buttonH);

        // Button label centered (adjust as needed)
        float textX = buttonX + buttonW / 2f - font.getRegion().getRegionWidth() / 8f + 93;
        float textY = buttonY + buttonH / 2f + 10;
        font.draw(batch, "Send", textX, textY);

        // --- New Code for Button Click Detection ---
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y

            // Check if the click is within the button bounds
            if (mouseX >= buttonX && mouseX <= buttonX + buttonW &&
                mouseY >= buttonY && mouseY <= buttonY + buttonH) {
                ClientNetworkManager.getInstance().sendMessage(new AceeptTradeMessage(
                    App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                    goalPlayerName
                ));
            }
        }
        // --- End of New Code ---
    }

    private void handleRightClick(float menuX, float menuY, float menuHeight,
                                  float slotW, float slotH, float slotSpacing) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // invert Y

            final int myCapacity = 5;

            for (int i = 0; i < myCapacity; i++) {
                float slotX = menuX + 220;
                float slotY = menuY + menuHeight - 160 - (i * slotSpacing) - slotH;

                if (mouseX >= slotX && mouseX <= slotX + slotW &&
                    mouseY >= slotY && mouseY <= slotY + slotH) {

                    if (i >= myItems.size()) break;

                    Item selected = myItems.get(i);
                    if (selected == null) break;

                    Item oneItem = selected.copyItem(1);

                    // Try to add to inventory bar
                    boolean added = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(oneItem);
                    if(added & App.getInstance().getCurrentGame().isOnline()){
                        ClientNetworkManager.getInstance().sendMessage(new ChangeItemTradeMessage(
                            App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                            App.getInstance().getGameControllerFinal().getTradeController().goalPlayerName,
                            oneItem.getName(),
                            -oneItem.getNumber()
                        ));
                    }

                    if (added) {
                        // Decrease selected item count
                        selected.setNumber(selected.getNumber() - 1);

                        // Remove if count is zero or less
                        if (selected.getNumber() <= 0) {
                            myItems.remove(i);
                        }
                    }
                    break; // handled click, no need to check other slots
                }
            }
        }
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public String getGoalPlayerName() {
        return goalPlayerName;
    }

    public void setGoalPlayerName(String goalPlayerName) {
        this.goalPlayerName = goalPlayerName;
    }

    public ArrayList<Item> getMyItems() {
        return myItems;
    }

    public ArrayList<Item> getHisItems() {
        return hisItems;
    }

    public boolean addItem(Item item) {
        if (item instanceof Tool || myItems.size() >= 5) {
            return false;
        }
        for (Item myItem : myItems) {
            if (myItem.getName().equals(item.getName())) {
                myItem.setNumber(item.getNumber() + myItem.getNumber());
                return true;
            }
        }
        myItems.add(item);
        return true;
    }


}
