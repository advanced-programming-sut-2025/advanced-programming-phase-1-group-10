package Server.Controllers.FinalControllers;

import Server.Controllers.Utils.InventoryUtils;
import Common.Models.App;
import Common.Models.Item;
import Common.Models.Tools.BackPack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class InventoryController {

    private final Texture trashCan = new Texture("inventoryTrash.png");
    private final int TRASH_SIZE = 64;
    private final int TRASH_MARGIN = 20;


    private final Texture slot = new Texture("slot/slot.png");
    private final int SLOT_SIZE = 64;
    private final int SLOTS_PER_ROW = 12;

    private final BitmapFont font;
    private boolean visible = false;

    public InventoryController() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
    }

    public void update(SpriteBatch batch) {
        // Toggle inventory with "I"
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            visible = !visible;
        }

        if (!visible) return;

        BackPack backPack = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack();
        List<Item> items = backPack.getItems();
        int capacity = backPack.getBackpackType().getCapacity();

        int rows = capacity / SLOTS_PER_ROW;
        int startX = (Gdx.graphics.getWidth() - SLOTS_PER_ROW * SLOT_SIZE) / 2;
        int startY = (Gdx.graphics.getHeight() - rows * SLOT_SIZE) / 2;

        for (int i = 0; i < capacity; i++) {
            int row = i / SLOTS_PER_ROW;
            int col = i % SLOTS_PER_ROW;

            int x = startX + col * SLOT_SIZE;
            int y = startY + (rows - 1 - row) * SLOT_SIZE;

            // Inside for-loop, after computing x and y
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y axis

            boolean isHovered = mouseX >= x && mouseX < x + SLOT_SIZE &&
                mouseY >= y && mouseY < y + SLOT_SIZE;

            if (isHovered && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                if (i < items.size()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                        // Ctrl + Right Click → remove item
                        items.remove(i);
                    } else {
                        // Regular Right Click → transfer item
                        InventoryUtils.transferItem(i, true); // true: from backpack
                    }
                    return;
                }
            }

            // Draw slot
            batch.draw(slot, x, y, SLOT_SIZE, SLOT_SIZE);

            // Draw item if available
            if (i < items.size()) {
                Item item = items.get(i);
                batch.draw(item.show(), x, y, SLOT_SIZE, SLOT_SIZE);
                if (item.getNumber() > 1) {
                    font.draw(batch, String.valueOf(item.getNumber()), x + SLOT_SIZE - 12, y + 14);
                }
            }
            // Position the trash can to the right of the grid
            int trashX = startX + SLOTS_PER_ROW * SLOT_SIZE + TRASH_MARGIN;
            int trashY = startY;

            // Draw the trash can
            batch.draw(trashCan, trashX, trashY, TRASH_SIZE, TRASH_SIZE);
        }
    }
}
