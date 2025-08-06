package Server.Controllers.FinalControllers;

import Common.Models.*;
import Common.Models.Planets.Seed;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tools.Fertilizer;
import Common.Models.Tools.Tool;

public class InteractController {

    private Item currentItem;
    private Player player;
    private final Map map;
    private final int INTERACTION_RANGE = 2; // you can change this to interact tiles

    public InteractController() {
        this.player = App.getInstance().getCurrentGame().getCurrentPlayer();
        this.map = App.getInstance().getCurrentGame().getGameMap();
    }

    public void update(){
        try{
            this.currentItem = player.getIventoryBarItems().get(player.getSelectedSlot());
        } catch (Exception exception){
            this.currentItem = null;
        }

        player = App.getInstance().getCurrentGame().getCurrentPlayer();
    }

    public void useItemInDirection(String direction) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = player.getPosition();

        switch (direction) {
            case "UP":
                for (int i = 1; i <= INTERACTION_RANGE; i++) {
                    Position targetPos = new Position(playerPos.getX() + i, playerPos.getY());
                    if(isValidPosition(targetPos))
                        useItemOnTile(targetPos);
                }
                break;
            case "DOWN":
                for (int i = 1; i <= INTERACTION_RANGE; i++) {
                    Position targetPos = new Position(playerPos.getX() - i, playerPos.getY());
                    if(isValidPosition(targetPos))
                        useItemOnTile(targetPos);
                }
                break;
            case "LEFT":
                for (int i = 1; i <= INTERACTION_RANGE; i++) {
                    Position targetPos = new Position(playerPos.getX(), playerPos.getY() - i);
                    if(isValidPosition(targetPos))
                        useItemOnTile(targetPos);
                }
                break;
            case "RIGHT":
                for (int i = 1; i <= INTERACTION_RANGE; i++) {
                    Position targetPos = new Position(playerPos.getX(), playerPos.getY() + i);
                    if(isValidPosition(targetPos))
                        useItemOnTile(targetPos);
                }
                break;
        }
    }

    private boolean isValidPosition(Position pos) {
        int mapWidth = map.getMap().length;
        int mapHeight = map.getMap()[0].length;

        return pos.getX() >= 0 && pos.getX() < mapWidth &&
            pos.getY() >= 0 && pos.getY() < mapHeight;
    }

    private void useItemOnTile(Position pos) {
        Tile tile = map.getMap()[pos.getX()][pos.getY()];
        tile.setPosition(pos);

        if (currentItem instanceof Tool) {
            ((Tool) currentItem).use(tile);
        } else if (currentItem instanceof Seed){
            ((Seed) currentItem).use(tile);
        } else if (currentItem instanceof Fertilizer){
            ((Fertilizer) currentItem).use(tile);
        }
    }
}
