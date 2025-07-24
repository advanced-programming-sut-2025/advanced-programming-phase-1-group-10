package Controllers.FinalControllers;

import Assets.PlayerAsset;
import Models.App;
import Models.Map;
import Models.PlayerStuff.Player;
import Models.Position;
import Models.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class PlayerController {

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
        } else {
            moving = false; // cancel animation if blocked
        }

        player.setMoving(moving);
        player.setStateTime(player.getStateTime() + delta);

        int row = (int) (player.getY() / Map.tileSize);
        int col = (int) (player.getX() / Map.tileSize);
        player.setPosition(new Position(row, col));
    }

    public void render(Player player, SpriteBatch batch) {
        TextureRegion currentFrame;
        PlayerAsset asset = movementAnimation;

        if (player.isMoving()) {
            switch (player.getDirection()) {
                case UP:
                    currentFrame = asset.getWalkUpAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                    break;
                case DOWN:
                    currentFrame = asset.getWalkDownAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                    break;
                case LEFT:
                    currentFrame = asset.getWalkLeftAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                    break;
                case RIGHT:
                    currentFrame = asset.getWalkRightAnimation(player.getGender()).getKeyFrame(player.getStateTime(), true);
                    break;
                default:
                    currentFrame = asset.getIdleFrame(player.getGender());
            }
        } else {
            currentFrame = asset.getIdleFrame(player.getGender());
        }

        batch.draw(currentFrame, player.getX(), player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
    }

    private boolean canMoveTo(float nextX, float nextY, Map map) {
        float width  = Player.PLAYER_WIDTH;
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


    public void update(SpriteBatch batch) {
        for (Player player : players) {
            updatePosition(player, Gdx.graphics.getDeltaTime());
            render(player, batch);
        }
    }
}
