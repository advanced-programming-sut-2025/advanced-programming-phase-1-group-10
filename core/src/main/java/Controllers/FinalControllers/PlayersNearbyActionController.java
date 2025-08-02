package Controllers.FinalControllers;

import Controllers.MessageSystem;
import Models.App;
import Models.FriendShip.Friendship;
import Models.Item;
import Models.PlayerStuff.Gender;
import Models.PlayerStuff.Player;
import Models.Result;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.List;
import java.util.function.Consumer;

public class PlayersNearbyActionController {

    private final Sprite buttonSprite;
    private final BitmapFont font;

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 40;
    private static final int PADDING = 10;
    private static final float NEARBY_THRESHOLD_MULTIPLIER = 1.5f; // in tiles

    // Callbacks (can be set externally)
    private Consumer<Player> onHug = this::hugPlayer;
    private Consumer<Player> onBouquet = this::giveBouquet;
    private Consumer<Player> onMarry = this::proposeMarriage;

    public PlayersNearbyActionController() {
        this.buttonSprite = new Sprite(new Texture("friendship/button.png"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/mainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 22;
        param.color = Color.BLACK;
        font = generator.generateFont(param);
        generator.dispose();
    }

    public void setOnHug(Consumer<Player> onHug) {
        this.onHug = onHug;
    }

    public void setOnBouquet(Consumer<Player> onBouquet) {
        this.onBouquet = onBouquet;
    }

    public void setOnMarry(Consumer<Player> onMarry) {
        this.onMarry = onMarry;
    }

    public void update(SpriteBatch batch) {
        Player current = App.getInstance().getCurrentGame().getCurrentPlayer();
        List<Player> allPlayers = App.getInstance().getCurrentGame().getPlayers();

        // Find nearby other player by real position (distance)
        // Current nearby target
        Player targetPlayer = null;
        float threshold = Models.Map.tileSize * NEARBY_THRESHOLD_MULTIPLIER;
        float thresholdSq = threshold * threshold;

        for (Player other : allPlayers) {
            if (other == current) continue;

            float dx = other.getX() - current.getX();
            float dy = other.getY() - current.getY();
            float distSq = dx * dx + dy * dy;
            if (distSq <= thresholdSq) {
                targetPlayer = other;
                break;
            }
        }

        // Buttons position: bottom-left of screen
        float baseX = 20f;
        float baseY = 40f;

        String[] labels = {"Hug", "Bouquet", "Marry Me"};
        Consumer<Player>[] actions = new Consumer[]{onHug, onBouquet, onMarry};

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // flipped for UI

        boolean hasTarget = targetPlayer != null;

        for (int i = 0; i < labels.length; i++) {
            float bx = baseX + i * (BUTTON_WIDTH + PADDING);
            float by = baseY;

            float textX = bx + 10;
            float textY = by + BUTTON_HEIGHT - 12;

            if (hasTarget) {
                batch.draw(buttonSprite, bx, by, BUTTON_WIDTH, BUTTON_HEIGHT);
                font.setColor(Color.WHITE);
                font.draw(batch, labels[i], textX, textY);
            } else {
                // draw disabled look
                batch.setColor(0.5f, 0.5f, 0.5f, 1f);
                batch.draw(buttonSprite, bx, by, BUTTON_WIDTH, BUTTON_HEIGHT);
                batch.setColor(1f, 1f, 1f, 1f);
                font.setColor(Color.DARK_GRAY);
                font.draw(batch, labels[i], textX, textY);
            }

            boolean hovered = mouseX >= bx && mouseX <= bx + BUTTON_WIDTH &&
                mouseY >= by && mouseY <= by + BUTTON_HEIGHT;

            if (hasTarget && hovered && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                actions[i].accept(targetPlayer);
            }
        }

        // Indicate target
        if (hasTarget) {
            String info = "Target: " + targetPlayer.getName();
            font.setColor(Color.GOLD);
            font.draw(batch, info, baseX, baseY + BUTTON_HEIGHT + 20);
        }
    }

    private void hugPlayer(Player other) {
        addXpToPlayers(other, 50);
        MessageSystem.showMessage("You hugged " + other.getName(), 2f, Color.GREEN);
    }

    private void giveBouquet(Player other) {

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        Item flower = null;
        for (Item item : currentPlayer.getInventory().getBackPack().getItems()) {
            if (item.getName().equals("Bouquet")) {
                flower = item;
                break;
            }
        }

        if (flower == null) {
            MessageSystem.showMessage("You don't have any Bouquet", 2f, Color.RED);
            return;
        }

        Friendship f1 = getFriendship(currentPlayer, other);
        Friendship f2 = getFriendship(other, currentPlayer);

        if (f1.getLevel() < 2 || f2.getLevel() < 2) {
            MessageSystem.showMessage("Friendship level is not enough to gift a flower!", 2f, Color.RED);
            return;
        }

        other.getInventory().getBackPack().addItem(flower);
        currentPlayer.getInventory().getBackPack().removeItemNumber(flower.getName(), 1);

        f1.setFlowerGiven(true);
        f2.setFlowerGiven(true);

        MessageSystem.showMessage("You gave " + other.getName() + " a flower! What's next?", 2f, Color.GREEN);
    }

    private void proposeMarriage(Player other) {

        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        if(currentPlayer.getGender() != Gender.Male){
            MessageSystem.showMessage("Only a male can ask for marriage!",2f,Color.RED);
            return;
        }


        if(currentPlayer.getGender() == other.getGender()){
            MessageSystem.showMessage( "Same-sex marriage is illegal at the moment.", 2f, Color.RED);
            return;
        }

        if(currentPlayer.getCouple() != null || other.getCouple() != null){
           MessageSystem.showMessage("Both Players need to be single", 2f, Color.RED);
           return;
        }

        Friendship fs1 = getFriendship(currentPlayer, other);
        Friendship fs2 = getFriendship(other, currentPlayer);

        if (fs1.getLevel() != 3 || fs2.getLevel() != 3){
            MessageSystem.showMessage("In this friendship level, marriage is not allowed!",2f,Color.RED);
            return;
        }


        Item ring = null;
        for(Item item: currentPlayer.getInventory().getBackPack().getItems()){
            if(item.getName().equals("Ring")){
                ring = item;
            }
        }

        if(ring == null){
            MessageSystem.showMessage("No ring found!", 2f, Color.RED);
            return;
        }

        //TODO send a message for other player to ask for marrriage.
        currentPlayer.setCouple(other);
        other.setCouple(currentPlayer);
        currentPlayer.getInventory().getBackPack().removeItemNumber(ring.getName(), 1);
        other.getInventory().getBackPack().addItem(ring);
        fs1.setMarried(true); fs2.setMarried(true);
        MessageSystem.showMessage("Happy your Marriage!", 2f, Color.GREEN);


        /*
        *     fs1.setXp(-fs1.getXp());
        *     fs2.setXp(-fs2.getXp());
        *     return new Result(true, "Inshallah next time!");
        *
        * */

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
    }

    public Friendship getFriendship(Player player, Player goal){
        return player.getFriendships().stream().filter(f -> f.getPlayer().equals(goal)).findFirst().orElse(null);
    }
}
