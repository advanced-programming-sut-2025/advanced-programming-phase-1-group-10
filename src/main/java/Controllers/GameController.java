package Controllers;

import Models.Animal.Animal;
import Models.*;
import Models.Crafting.CraftingType;
import Models.Place.Barn;
import Models.Place.Coop;
import Models.Place.Place;
import Models.Place.Store.CarpenterShop;
import Models.Place.Store.MarrineRanchStore;
import Models.Place.Store.Store;
import Models.PlayerStuff.Player;
import Models.Recipe.Recipe;
import Models.Tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameController {
    public Result showEnergy() {
        final double energy = App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().getEnergyAmount();
        String result;
        if(energy == Double.MAX_VALUE) result = "Infinite"; else result = Double.toString((int)energy);
        return new Result(true, "Player Energy: " + result);
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
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if(!(playerTile.getPlace() instanceof MarrineRanchStore)){
            return new Result(false, "you have to be inside marnie's ranch to buy animals.");
        }

        Animal animal = Animal.animalFactory(animalType, name);
        if (animal == null) {
            return new Result(false, "invalid animal type!");
        }

        for (Animal animal1 : App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals()) {
            if (animal1.getName().equals(name)) {
                return new Result(false, "each animal must have a unique name.");
            }
        }

        Place place = getPlaceByType(animal.getAnimalType().getEnclosures().toString());
        if(place == null){
            return new Result(false,"you don't have " + animal.getAnimalType().getEnclosures().toString() + "in your farm.");
        }

        Coop coop = null;
        Barn barn = null;
        if(place instanceof Coop) {
            coop = (Coop) place;
            if(coop.getAnimalCount() == coop.getCapacity()){
                return new Result(false, "not enough coop space to by this animal.");
            }
        }
        else {
            barn = (Barn) place;
            if(barn.getAnimalCount() == barn.getCapacity()) {
                return new Result(false, "not enough barn space to by this animal.");
            }
        }

        //TODO palce animal in map

        if(place instanceof Coop)
            coop.setAnimalCount(coop.getAnimalCount() + 1);
        else
            barn.setAnimalCount(barn.getAnimalCount() + 1);

        App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().add(animal);
        App.getInstance().getCurrentGame().getAnimals().put(name,animal);
        return new Result(true, "a new " + animalType + " named " + "has been bought.");
    }

    public Result petAnimals(String name){
        //TODO check the player is near animal
        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);
        if(animal == null){
            return new Result(false, "there is no animal with this name.");
        }
        animal.pet();
        return new Result(true,"you pet " + animal.getName() + ".");
    }

    public Result showAnimals(){
        if(App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().isEmpty()){
            return new Result(false,"you don't have any animals.");
        }
        StringBuilder animals = new StringBuilder();
        for(Animal animal : App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals()){
            animals.append("animal name : ".toUpperCase());
            animals.append(animal.getName());
            animals.append("|");
            animals.append("ANIMAL FRIENDSHIP : ");
            animals.append(animal.getFriendShip());
            animals.append("|");
            if(animal.isFed())
                animals.append("the animal has been fed.");
            else
                animals.append("the animal has not been fed.");
            animals.append("|");
            if(animal.isPetted())
                animals.append("the animal has been petted.");
            else
                animals.append("the animal has not been petted.");
            animals.append("\n");
        }
        return new Result(true, animals.toString());
    }

    public Place getPlaceByType(String placeType) {
        Player currentPlayer = App.getInstance().getCurrentGame().getCurrentPlayer();

        if (currentPlayer.getFarm() == null) {
            return null;
        }

        Farm playerFarm = currentPlayer.getFarm();
        for (Place place : playerFarm.getPlaces()) {
            if (place.getClass().getSimpleName().equalsIgnoreCase(placeType)) {
                return place;
            }
        }
        return null;
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
            if (requiredRecipe != null) {
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
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if(!(playerTile.getPlace() instanceof CarpenterShop)){
            return new Result(false, "you should go to Carpenter shop first!");
        }
        Coop newCoop = new Coop(coopPosition, 3, 3);
        if (!isPositionInPlayerFarm(coopPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 3, coopPosition, newCoop)) {
            return new Result(false, "can not build coop in this place.");
        }
        // TODO check the minerals amount of player to build coop
        App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getPlaces().add(newCoop);
        return new Result(true, "coop build successfully.");
    }

    public Result createBarn(Position barnPosition, Game game) {
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if(!(playerTile.getPlace() instanceof CarpenterShop)){
            return new Result(false, "you should go to Carpenter shop first!");
        }
        Barn newBarn = new Barn(barnPosition, 3, 3);
        if (!isPositionInPlayerFarm(barnPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 3, barnPosition, newBarn)) {
            return new Result(false, "can not build barn in this place.");
        }
        // TODO check the minerals amount of player to build barn
        App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getPlaces().add(newBarn);
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
        } else if (tile.getFarm() != null && tile.getFarm() != App.getInstance().getCurrentGame().getCurrentPlayer().getFarm()) {
            return new Result(false, "You cannot walk to the other player's farm!");
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

        // Begin walking with energy (step-by-step)
        double energy = player.getEnergy().getEnergyAmount();
        Position previous = startPosition;
        Integer lastDirection = null;
        int turnCount = 0;

        for (Position current : path) {
            int dxMove = current.getX() - previous.getX();
            int dyMove = current.getY() - previous.getY();

            int currentDirection;
            if (dxMove == -1) currentDirection = 0;
            else if (dxMove == 1) currentDirection = 1;
            else if (dyMove == -1) currentDirection = 2;
            else currentDirection = 3;

            boolean turnChanged = (lastDirection != null && currentDirection != lastDirection);
            if (turnChanged) {
                turnCount++;
            }
            lastDirection = currentDirection;

            double stepCost = (1 + (turnChanged ? 10.0 : 0)) / 20.0;

            if (stepCost > energy) {
                //TODO Next turn and set faint on
                return new Result(false, "You are faint!");
            }

            energy -= stepCost;
            player.getEnergy().setEnergyAmount(energy);

            // Move player
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
