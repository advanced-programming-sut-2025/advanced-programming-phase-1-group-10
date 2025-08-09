package Client.Controllers.FinalControllers;

import Common.KeySetting;
import Common.Models.App;
import Common.Models.Quest;
import Client.Controllers.MessageSystem;
import Common.Models.NPC.NPC;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.*;

public class QuestMenuController {

    private final Sprite questBox = new Sprite(new Texture("quest/questBox.png"));
    private final Texture buttonTexture = new Texture("quest/button.png");

    private final BitmapFont font;
    private boolean showMenu = false;
    private int selectedNPCIndex = 0;
    private boolean dropdownOpen = false;

    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 40;

    public QuestMenuController() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 24;
        param.color = Color.DARK_GRAY;
        font = generator.generateFont(param);
        generator.dispose();
    }

    public void update(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(App.getInstance().getKeySetting().getOpenQuestMenu())) {
            showMenu = !showMenu;
        }

        if (!showMenu) return;

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Quest currentQuest = player.getCurrentQuest();

        int boxWidth = (int) questBox.getWidth() * 2;
        int boxHeight = (int) questBox.getHeight() * 2;

        int centerX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        int centerY = (Gdx.graphics.getHeight() - boxHeight) / 2;

        batch.draw(questBox, centerX, centerY, boxWidth, boxHeight);

        int padding = 30;
        int currentY = centerY + boxHeight - padding;

        // --- Current Quest Section ---
        font.draw(batch, "Current Quest:", centerX + padding, currentY);
        currentY -= 35;
        if (currentQuest != null) {
            font.draw(batch, currentQuest.getExplanation(), centerX + padding, currentY);
        } else {
            font.draw(batch, "No active quest", centerX + padding, currentY);
        }

        // --- Cancel / Complete Buttons ---
        int cancelX = centerX + padding + 350;
        int completeX = cancelX + BUTTON_WIDTH + 30;
        drawButton(batch, "Cancel", cancelX, currentY, BUTTON_WIDTH, BUTTON_HEIGHT);
        drawButton(batch, "Complete", completeX, currentY, BUTTON_WIDTH, BUTTON_HEIGHT);

        handleButtonClicks(player, cancelX, completeX, currentY, currentQuest);

        // --- Dropdown Selector ---
        List<NPC> npcs = App.getInstance().getCurrentGame().getNpcs();
        NPC selectedNPC = npcs.get(selectedNPCIndex);

        int dropdownX = centerX + boxWidth - BUTTON_WIDTH - 100;
        int dropdownY = centerY + boxHeight - padding - 20;
        drawButton(batch, "NPC: " + selectedNPC.getName(), dropdownX + 15, dropdownY - 15, BUTTON_WIDTH + 50, BUTTON_HEIGHT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (inBounds(mouseX, mouseY, dropdownX, dropdownY, BUTTON_WIDTH + 80, BUTTON_HEIGHT)) {
                dropdownOpen = !dropdownOpen;
            }
        }

        if (dropdownOpen) {
            for (int i = 0; i < npcs.size(); i++) {
                int itemY = dropdownY - (i + 1) * (BUTTON_HEIGHT + 5);
                drawButton(batch, npcs.get(i).getName(), dropdownX + 205, itemY, BUTTON_WIDTH + 50, BUTTON_HEIGHT);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    int mouseX = Gdx.input.getX();
                    int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    if (inBounds(mouseX, mouseY, dropdownX + 205, itemY, BUTTON_WIDTH + 80, BUTTON_HEIGHT)) {
                        selectedNPCIndex = i;
                        dropdownOpen = false;
                    }
                }
            }
        }

        // --- NPC Quests ---
        List<Quest> npcQuests = selectedNPC.getQuests();
        int questStartY = centerY + boxHeight / 2;
        for (int i = 0; i < npcQuests.size(); i++) {
            Quest quest = npcQuests.get(i);
            int qY = questStartY - i * 70;

            if (quest.isCompleted()) {
                font.setColor(Color.LIGHT_GRAY); // ✅ Gray text for completed quests
            } else {
                font.setColor(Color.WHITE);
            }

            font.draw(batch, quest.getExplanation(), centerX + padding, qY + 30);
            font.setColor(Color.WHITE); // reset after draw

            int takeButtonX = centerX + boxWidth - BUTTON_WIDTH - 60;

            // Only show take button if quest is not completed
            if (!quest.isCompleted()) {
                drawButton(batch, "Take", takeButtonX, qY, BUTTON_WIDTH - 20, BUTTON_HEIGHT);

                // Handle take button click
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    int mouseX = Gdx.input.getX();
                    int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                    if (inBounds(mouseX, mouseY, takeButtonX, qY, BUTTON_WIDTH, BUTTON_HEIGHT)) {
                        if (player.getCurrentQuest() == null) {
                            player.setCurrentQuest(quest);
                            MessageSystem.showMessage("Quest accepted!", 2f, Color.GREEN);
                        } else {
                            MessageSystem.showMessage("You already have an active quest!", 2f, Color.RED);
                        }
                    }
                }
            }
        }
    }

    private void drawButton(SpriteBatch batch, String text, int x, int y, int width, int height) {
        batch.setColor(Color.BROWN);
        batch.draw(buttonTexture, x, y, width, height);
        batch.setColor(Color.WHITE);
        font.draw(batch, text, x + 10, y + 28);
    }

    private void handleButtonClicks(Player player, int cancelX, int completeX, int y, Quest currentQuest) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (inBounds(mouseX, mouseY, cancelX, y, BUTTON_WIDTH, BUTTON_HEIGHT)) {
                if (currentQuest != null) {
                    player.setCurrentQuest(null);
                    MessageSystem.showMessage("Quest canceled.", 2f, Color.YELLOW);
                }
            }

            if (inBounds(mouseX, mouseY, completeX, y, BUTTON_WIDTH, BUTTON_HEIGHT)) {
                if (currentQuest != null && player.getInventory().getBackPack().hasItem(currentQuest.getGivenItems(), 1)) {
                    player.getInventory().getBackPack().removeItemNumber(currentQuest.getGivenItems().getName(), 1);
                    player.getInventory().getBackPack().addItem(currentQuest.getItemAward());
                    player.addGold(currentQuest.getGoldAward());

                    currentQuest.setCompleted(true); // ✅ Mark quest as completed

                    // ✅ Remove from other players
                    for (Player p : App.getInstance().getCurrentGame().getPlayers()) {
                        if (p != player && currentQuest.equals(p.getCurrentQuest())) {
                            p.setCurrentQuest(null);
                        }
                    }

                    player.setCurrentQuest(null);
                    MessageSystem.showMessage("Quest completed!", 2.5f, Color.GREEN);
                } else {
                    MessageSystem.showMessage("Missing quest items!", 2f, Color.RED);
                }
            }
        }
    }

    private boolean inBounds(int x, int y, int bx, int by, int bw, int bh) {
        return x >= bx && x <= bx + bw && y >= by && y <= by + bh;
    }
}
