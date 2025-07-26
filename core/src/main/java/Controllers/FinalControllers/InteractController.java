package Controllers.FinalControllers;

import Models.*;
import Models.PlayerStuff.Player;
import Models.Tools.Tool;

public class InteractController {

    private Item currentItem;
    private final Player player;
    private final Map map;


    public InteractController() {
        this.player = App.getInstance().getCurrentGame().getCurrentPlayer();
        this.map = App.getInstance().getCurrentGame().getGameMap();
    }

    public void update(){
        try{
            this.currentItem = player.getIventoryBarItems().get(player.getSelectedSlot());
        } catch (Exception ignored){}
    }

    public void useItemInDirection(String direction) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = player.getPosition();
        Position targetPos = new Position(playerPos.getX(), playerPos.getY());

        switch (direction) {
            case "UP":
                targetPos.setX(targetPos.getX() + 1);
                break;
            case "DOWN":
                targetPos.setX(targetPos.getX() - 1);
                break;
            case "LEFT":
                targetPos.setY(targetPos.getY() - 1);
                break;
            case "RIGHT":
                targetPos.setY(targetPos.getY() + 1);
                break;
        }

        // Then trigger useItem at targetPos
        useItemOnTile(targetPos);
    }

    public void useItemOnTile(Position pos) {
        int mapWidth = map.getMap().length;
        int mapHeight = map.getMap()[0].length;

        // Validate bounds
        if (pos.getX() < 0 || pos.getX() >= mapWidth ||
            pos.getY() < 0 || pos.getY() >= mapHeight) {
            return;
        }

        Tile tile = map.getMap()[pos.getX()][pos.getY()];

        if (currentItem == null) {
            return;
        }

        if (currentItem instanceof Tool) {
            ((Tool) currentItem).use(tile);
        }
    }



}
