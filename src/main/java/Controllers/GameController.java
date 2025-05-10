package Controllers;

import Models.Animal.Animal;
import Models.*;
import Models.Crafting.CraftingType;
import Models.Place.Barn;
import Models.Place.Coop;
import Models.PlayerStuff.Player;
import Models.Recipe.Recipe;
import Models.Tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameController {
    public Result showEnergy() {
        final int energy = App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().getEnergyAmount();
        return new Result(true, "Player Energy: " + energy);
    }

    public Result showInventory() {
        StringBuilder message = new StringBuilder();
        for (Item item : App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems()) {
            message.append(item.getName()).append(": ").append(item.getNumber()).append("\n");
        }
        return new Result(true, message.toString());
    }

    public Result removeItemFromInventory(String itemName, String itemNumber) {
        int itemNumberInt;
        try {
            if (itemNumber == null || itemNumber.trim().isEmpty()) {
                itemNumberInt = -1;
            } else {
                itemNumberInt = Integer.parseInt(itemNumber.trim());
                if (itemNumberInt <= 0) {
                    return new Result(false, "Item number must be positive.");
                }
            }
        } catch (NumberFormatException exception) {
            return new Result(false, "Invalid item number.");
        }

        List<Item> items = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems();

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName.trim())) {
                if (itemNumberInt == -1 || itemNumberInt >= item.getNumber()) {
                    items.remove(item);
                    return new Result(true, item.getName() + " removed from inventory completely.");
                } else {
                    item.setNumber(item.getNumber() - itemNumberInt);
                    return new Result(true, itemNumberInt + " " + item.getName() + " removed from inventory.");
                }
            }
        }

        return new Result(false, "Item not found in inventory.");
    }

    public Result equipTool(String toolName) {
        for (Item item : App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems()) {
            if (item instanceof Tool && (item).getName().equalsIgnoreCase(toolName)) {
                App.getInstance().getCurrentGame().getCurrentPlayer().setCurrentTool((Tool) item);
                return new Result(true, item.getName() + " equipped.");
            }
        }
        return new Result(false, "Tool not found.");
    }

    public Result showCurrentTool() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if (player.getCurrentTool() == null) {
            return new Result(false, "No tool equipped.");
        } else {
            return new Result(true, player.getCurrentTool().getName() + " equipped.");
        }
    }

    public Result showAvaliableTools() {
        StringBuilder message = new StringBuilder();
        List<Item> items = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems();
        for (Item item : items) {
            if (item instanceof Tool) {
                message.append(item.getName()).append(" ");
            }
        }
        return new Result(true, message.toString());
    }

    public Result buyAnimal(String animalType, String name) {
        Animal animal = Animal.animalFactory(animalType, name);
        if (animal == null) {
            return new Result(false, "invalid animal type!");
        }

        for (Animal animal1 : App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals()) {
            if (animal1.getName().equals(name)) {
                return new Result(false, "each animal must have a unique name.");
            }
        }

        //TODO palce animal in map
        //TODO check capacity of coob

        App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().add(animal);
        return new Result(false, "a new " + animalType + " named " + "has been bought.");
    }


    public Result craftingShowRecipes() {
        ArrayList<CraftingType> availableCraftings = new ArrayList<>();

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        int miningLevel = player.getMiningAbility();
        int farmingLevel = player.getFarmingAbility();
        int foragingLevel = player.getForagingAbility();

        ArrayList<Item> items = player.getInventory().getBackPack().getItems();
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Recipe) {
                recipes.add((Recipe) item);
            }
        }

        for (CraftingType craftingtype : CraftingType.values()) {
            String abilityType = craftingtype.getAbilityType();
            int abilityLevel = craftingtype.getAbilityLevel();
            String requiredRecipe = craftingtype.getRequiredRecipe();

            if (abilityType.equals(null) && requiredRecipe.equals(null)) {
                availableCraftings.add(craftingtype);
            }
            if (abilityType.equals("mining")) {
                if (abilityLevel <= miningLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if (abilityType.equals("farming")) {
                if (abilityLevel <= farmingLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if (abilityType.equals("foraging")) {
                if (abilityLevel <= foragingLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if (!requiredRecipe.equals(null)) {
                for (Recipe recipe : recipes) {
                    if (requiredRecipe.equals(recipe.getName()))
                        availableCraftings.add(craftingtype);
                }
            }

            StringBuilder massage = new StringBuilder();
            for (CraftingType availableCrafting : availableCraftings) {
                massage.append(availableCrafting).append("\n");
            }
            String finalMassage = massage.toString();

            return new Result(true, finalMassage);
        }
        return new Result(false, "no crafting found.");
    }

    public Result craftingCraft(String itemName) {
        return null;
    }

    public Result createCoop(Position coopPosition, Game game) {
        Coop newCoop = new Coop(coopPosition, 3, 3);
        if (!isPositionInPlayerFarm(coopPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 3, coopPosition, newCoop)) {
            return new Result(false, "can not build coop in this place.");
        }
        // TODO check the minerals amount of player to build coop
        return new Result(true, "coop build successfully.");
    }

    public Result createBarn(Position barnPosition, Game game) {
        Barn newBarn = new Barn(barnPosition, 3, 3);
        if (!isPositionInPlayerFarm(barnPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 3, barnPosition, newBarn)) {
            return new Result(false, "can not build coop in this place.");
        }
        // TODO check the minerals amount of player to build barn
        return new Result(true, "barn build successfully.");
    }

    public boolean isPositionInPlayerFarm(Position position, Player player) {
        if (player.getFarm() == null) {
            return false;
        }

        Farm farm = player.getFarm();
        Position farmPosition = farm.getPosition();
        boolean isInXRange = position.getX() >= farmPosition.getX() &&
                position.getX() < farmPosition.getX() + Farm.farmHeight;

        boolean isInYRange = position.getY() >= farmPosition.getY() &&
                position.getY() < farmPosition.getY() + Farm.farmWidth;

        return isInXRange && isInYRange;
    }

    public Result walkPlayer(Position finalPosition) {
        Tile tile = getTileByPosition(finalPosition);
        if (tile == null) {
            return new Result(false, "Tile not found!");
        } else if (!isTileAvailableForWalk(finalPosition)) {
            return new Result(false, "Something else is placed on this tile!");
        } else if(tile.getFarm() != App.getInstance().getCurrentGame().getCurrentPlayer().getFarm()){
            return new Result(false, "You cannot walk to the other players's farm!");
        }

        Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position startPosition = player.getPosition();

        int rows = map.length;
        int cols = map[0].length;

        int[] dx = {-1, 1, 0, 0}; // up, down, left, right
        int[] dy = {0, 0, -1, 1};

        boolean[][] visited = new boolean[rows][cols];
        Position[][] parent = new Position[rows][cols];
        Queue<Position> queue = new LinkedList<>();

        queue.add(startPosition);
        visited[startPosition.getX()][startPosition.getY()] = true;

        boolean found = false;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.equals(finalPosition)) {
                found = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.getX() + dx[i];
                int ny = current.getY() + dy[i];

                if (nx >= 0 && ny >= 0 && nx < rows && ny < cols) {
                    Position next = new Position(nx, ny);
                    if (!visited[nx][ny] && (next.equals(finalPosition) || isTileAvailableForWalk(next))) {
                        visited[nx][ny] = true;
                        parent[nx][ny] = current;
                        queue.add(next);
                    }
                }
            }
        }

        if (!found) {
            return new Result(false, "No path found!");
        }

        // Reconstruct path
        LinkedList<Position> path = new LinkedList<>();
        Position step = finalPosition;
        while (!step.equals(startPosition)) {
            path.addFirst(step);
            step = parent[step.getX()][step.getY()];
        }

        // Begin walking with energy
        int energy = player.getEnergy().getEnergyAmount();
        Position previous = startPosition;
        Integer lastDirection = null;
        int turnCount = 0;
        int movedTiles = 0;

        for (Position current : path) {
            // Determine direction
            int dxMove = current.getX() - previous.getX();
            int dyMove = current.getY() - previous.getY();
            int currentDirection;
            if (dxMove == -1) currentDirection = 0;
            else if (dxMove == 1) currentDirection = 1;
            else if (dyMove == -1) currentDirection = 2;
            else currentDirection = 3;

            if (lastDirection != null && currentDirection != lastDirection) {
                turnCount++;
            }

            lastDirection = currentDirection;
            movedTiles++;

            // Calculate total energy cost so far
            double totalCost = (movedTiles + 10.0 * turnCount) / 20.0;
            int requiredEnergy = (int) Math.ceil(totalCost);

            if (requiredEnergy > player.getEnergy().getEnergyAmount()) {
                // Energy not enough for this step — stop here
                player.setEnergy(energy); // update with energy used so far
                return new Result(false, "You are faint!");
            }

            // Deduct energy progressively
            energy = player.getEnergy().getEnergyAmount() - requiredEnergy;
            player.setEnergy(energy);

            // Move player on map
            getTileByPosition(previous).setPerson(null);
            getTileByPosition(current).setPerson(player);
            player.setPosition(current);

            previous = current;
        }

        return new Result(true, "Player moved successfully.");
    }

    public boolean isTileAvailableForWalk(Position position) {
        Tile tile = getTileByPosition(position);
        if (tile == null) {
            return false; // out-of-bounds or invalid tile
        }
        return tile.getItem() == null && tile.getPerson() == null && tile.getTileType() != TileType.Wall;
    }


    public Tile getTileByPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();

        if (map == null || x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return null;
        }

        return map[x][y];
    }

    public Result printMap() {
        StringBuilder sb = new StringBuilder();
        Tile[][] map = App.getInstance().getCurrentGame().getGameMap().getMap();

        for (Tile[] row : map) {
            for (Tile tile : row) {
                sb.append(tile.getTile());
            }
            sb.append("\n");
        }

        return new Result(true, sb.toString());
    }

}
