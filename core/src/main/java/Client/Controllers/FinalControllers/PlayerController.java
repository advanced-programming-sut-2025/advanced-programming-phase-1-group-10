package Client.Controllers.FinalControllers;

import Client.Assets.FaintAsset;
import Client.Assets.PlayerAsset;
import Client.Controllers.MessageSystem;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.Map;
import Common.Models.PlayerStuff.Player;
import Common.Models.Position;
import Common.Models.Tile;
import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.MovePlayerMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController {

    private int currentPlayerIndex = 0;
    private final FaintAsset faintAsset = new FaintAsset(0.1f); // adjust speed
    private final HashMap<Player,Float> faintStateTimes = new HashMap<>();

    public PlayerController(Map map) {
        this.map = map;
    }

    private final ArrayList<Player> players = App.getInstance().getCurrentGame().getPlayers();
    private final PlayerAsset movementAnimation = new PlayerAsset();
    private final Map map;




    public void updatePosition(Player player, float delta) {
        boolean moving = false;

        float currentX = player.getX();
        float currentY = player.getY();

        float nextX = currentX;
        float nextY = currentY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nextX -= player.getSpeed() * delta;
            player.setDirection(Player.Direction.LEFT);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nextX += player.getSpeed() * delta;
            player.setDirection(Player.Direction.RIGHT);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nextY += player.getSpeed() * delta;
            player.setDirection(Player.Direction.UP);
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nextY -= player.getSpeed() * delta;
            player.setDirection(Player.Direction.DOWN);
            moving = true;
        }

        // Check for walkable tile before applying the move
        if (moving && canMoveTo(nextX, nextY, map)) {
            player.setX(nextX);
            player.setY(nextY);
            player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - 0.01);
        } else {
            moving = false; // cancel animation if blocked
        }

        player.setMoving(moving);
        player.setStateTime(player.getStateTime() + delta);

        int row = (int) (player.getY() / Map.tileSize);
        int col = (int) (player.getX() / Map.tileSize);
        player.setPosition(new Position(row, col));


        if(App.getInstance().getCurrentGame().isOnline()){
            MovePlayerMessage movePlayerMessage = new MovePlayerMessage(
                App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                player.getX(),
                player.getY(),
                player.getDirection(),
                player.isMoving(),
                player.getPosition()
            );
            ClientNetworkManager.getInstance().sendMessage(movePlayerMessage);
        }

    }

    public void render(Player player, SpriteBatch batch) {
        TextureRegion currentFrame;
        PlayerAsset asset = movementAnimation;

        if (player.isMoving()) {
            currentFrame = switch (player.getDirection()) {
                case UP -> asset.getWalkUpAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                case DOWN -> asset.getWalkDownAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                case LEFT -> asset.getWalkLeftAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                case RIGHT -> asset.getWalkRightAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
            };
        } else {
            currentFrame = asset.getIdleFrame(player.getGender());
        }


        batch.draw(currentFrame, player.getX(), player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        System.out.println(player.getName() + " : " + player.getX() + " " + player.getY());
    }


    private boolean canMoveTo(float nextX, float nextY, Map map) {
        float width = Player.PLAYER_WIDTH;
        float height = Player.PLAYER_HEIGHT / 2f;

        //The height divided by 2 because there is no restriction for player head.


        int[] rows = {
            (int) (nextY / Map.tileSize),
            (int) ((nextY + height - 1) / Map.tileSize)
        };

        int[] cols = {
            (int) (nextX / Map.tileSize),
            (int) ((nextX + width - 1) / Map.tileSize)
        };

        for (int row : rows) {
            for (int col : cols) {
                if (row < 0 || row >= Map.mapHeight || col < 0 || col >= Map.mapWidth) {
                    return false;
                }
                Tile tile = map.getMap()[row][col];
                if (tile == null || !tile.isWalkable()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void handlePlayerSwitching() {

        if(App.getInstance().getCurrentGame().isOnline()){
            MessageSystem.showError("Cannont switch player in multiplayer" ,2f);
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) { // '.'
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            App.getInstance().getCurrentGame().setCurrentPlayer(players.get(currentPlayerIndex));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) { // ','
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
            App.getInstance().getCurrentGame().setCurrentPlayer(players.get(currentPlayerIndex));
        }
    }

    public void update(SpriteBatch batch) {
        handlePlayerSwitching();

        updatePosition(App.getInstance().getCurrentGame().getCurrentPlayer(),
                    Gdx.graphics.getDeltaTime());

        for (Player player : players) {
            render(player, batch);
            updateFaint(batch,player);
        }
    }

    public void updateFaint(SpriteBatch batch, Player player) {
        double energy = player.getEnergy().getEnergyAmount();

        // get or initialize this player's faint time
        float faintStateTime = faintStateTimes.getOrDefault(player, 0f);

        if (energy < 0) {
            // Advance animation time per player
            faintStateTime += Gdx.graphics.getDeltaTime();
            faintStateTimes.put(player, faintStateTime);

            // Prevent movement while fainted
            player.setMoving(false);

            // Draw current faint frame, forcing looping
            TextureRegion frame = faintAsset.getAnimation().getKeyFrame(faintStateTime, true);
            float drawX = player.getX();
            float drawY = player.getY() + Player.PLAYER_HEIGHT; // above head
            batch.draw(frame, drawX - 18, drawY - 10, 70, 20);
        } else {
            // Reset when not fainted
            faintStateTimes.put(player, 0f);
        }
    }


}
