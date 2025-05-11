package Controllers;

import Models.*;
import Models.Animal.Animal;
import Models.Crafting.Crafting;
import Models.*;
import Models.Crafting.CraftingType;
import Models.Place.House;
import Models.Place.Place;
import Models.Place.Barn;
import Models.Place.Coop;
import Models.Place.Place;
import Models.Place.Store.CarpenterShop;
import Models.Place.Store.MarrineRanchStore;
import Models.Place.Store.Store;
import Models.Planets.Tree;
import Models.PlayerStuff.Player;
import Models.Recipe.Recipe;
import Models.Tools.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameController {
    public Result showEnergy() {
        final double energy = App.getInstance().getCurrentGame().getCurrentPlayer().getEnergy().getEnergyAmount();
        String result;
        if (energy == Double.MAX_VALUE) result = "Infinite";
        else result = Double.toString((int) energy);
        return new Result(true, "Player Energy: " + result);
    }

    public Result showInventory() {
        StringBuilder message = new StringBuilder();
        for (Item item : App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems()) {
            if (item.getNumber() == 1) {
                message.append(item.getName()).append("\n");
            } else {
                message.append(item.getName()).append(" ").append(item.getNumber()).append("X ").append("\n");
            }
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
                message.append(item.getName()).append("\n");
            }
        }
        return new Result(true, message.toString());
    }

    public Result buyAnimal(String animalType, String name) {
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if (!(playerTile.getPlace() instanceof MarrineRanchStore)) {
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
        if (place == null) {
            return new Result(false, "you don't have " + animal.getAnimalType().getEnclosures().toString() + "in your farm.");
        }

        Coop coop = null;
        Barn barn = null;
        if (place instanceof Coop) {
            coop = (Coop) place;
            if (coop.getAnimalCount() == coop.getCapacity()) {
                return new Result(false, "not enough coop space to by this animal.");
            }
        } else {
            barn = (Barn) place;
            if (barn.getAnimalCount() == barn.getCapacity()) {
                return new Result(false, "not enough barn space to by this animal.");
            }
        }


        Place enclosure = getPlaceByType(animal.getAnimalType().getEnclosures().toString());
        Position enclosurePos = enclosure.getPosition();
        int enclosureHeight = 0;
        int enclosureWidth = 0;

        if (enclosure instanceof Coop) {
            enclosureHeight = 3;
            enclosureWidth = 4;
        } else if (enclosure instanceof Barn) {
            enclosureHeight = 3;
            enclosureWidth = 4;
        }

        for (int i = 0; i < enclosureHeight; i++) {
            for (int j = 0; j < enclosureWidth; j++) {
                Position pos = new Position(enclosurePos.getX() + i, enclosurePos.getY() + j);
                if (placeAnimal(App.getInstance().getCurrentGame(), pos, animal)) {
                    if (place instanceof Coop)
                        coop.setAnimalCount(coop.getAnimalCount() + 1);
                    else
                        barn.setAnimalCount(barn.getAnimalCount() + 1);

                    App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().add(animal);
                    App.getInstance().getCurrentGame().getAnimals().put(name, animal);
                    return new Result(true, "a new " + animalType + " named " + name + " has been bought and placed in your " +
                            animal.getAnimalType().getEnclosures().toString() + ".");
                }
            }
        }

        return new Result(false, "not enough coop space to by this animal.");
    }

    public Result petAnimals(String name) {
        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);
        if (animal == null) {
            return new Result(false, "there is no animal with this name.");
        }
        if (!isPlayerAdjacentToTile(animal.getPosition(), null)) {
            return new Result(false, "you are not close enough to pet this animal.");
        }

        animal.pet();
        return new Result(true, "you pet " + animal.getName() + " (a " + animal.getAnimalType().getType() + ")" + ".");
    }

    public Result showAnimals() {
        if (App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().isEmpty()) {
            return new Result(false, "you don't have any animals.");
        }
        StringBuilder animals = new StringBuilder();
        for (Animal animal : App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals()) {
            animals.append("animal name : ".toUpperCase());
            animals.append(animal.getName());
            animals.append(" | ");
            animals.append("ANIMAL FRIENDSHIP : ");
            animals.append(animal.getFriendShip());
            animals.append(" | ");
            if (animal.isFed())
                animals.append("the animal has been fed.");
            else
                animals.append("the animal has not been fed.");
            animals.append(" | ");
            if (animal.isPetted())
                animals.append("the animal has been petted.");
            else
                animals.append("the animal has not been petted.");
            animals.append("\n");
        }
        return new Result(true, animals.toString());
    }

    public Result shepherdAnimals(String name, Position position) {
        if (!App.getInstance().getCurrentGame().getWeather().getName().equalsIgnoreCase("sunny")) {
            return new Result(false, "the animal can move only in SUNNY weather");
        }

        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);
        if (animal == null) {
            return new Result(false, "there is no animal with this name.");
        }

        Tile destinationTile = getTileByPosition(position);
        if (destinationTile == null) {
            return new Result(false, "invalid destination position.");
        }

        if (destinationTile.getPerson() != null || destinationTile.getItem() != null ||
                destinationTile.getAnimal() != null || destinationTile.getTileType() == TileType.Wall) {
            return new Result(false, "the destination tile is occupied.");
        }
        Tile currentTile = getTileByPosition(animal.getPosition());
        if (currentTile != null) {
            currentTile.setAnimal(null);
        }

        animal.setPosition(position);
        destinationTile.setAnimal(animal);

        return new Result(true, "you successfully moved " + animal.getName() + " (a " + animal.getAnimalType().getType() + ")" + " to the new location.");
    }

    public Result feedAnimalWithHay(String name){
        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);

        if(animal == null){
            return new Result(false, "there is no animal with this name.");
        }

        if(!isPlayerAdjacentToTile(animal.getPosition(),App.getInstance().getCurrentGame().getCurrentPlayer())){
            return new Result(false, "you are not close enough to pet this animal.");
        }

        animal.feed();
        return new Result(true, animal.getName() + " (a " +
                            animal.getAnimalType().getType() + ") " +
                        "fed with hay.");
    }

    public Result sellAnimal(String name){
        Animal animal = App.getInstance().getCurrentGame().getAnimals().get(name);

        if(animal == null){
            return new Result(false, "there is no animal with this name.");
        }

        if(!App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().contains(animal)){
            return new Result(false, "you can't sell this animal.");
        }

        Tile animalTile = getTileByPosition(animal.getPosition());
        App.getInstance().getCurrentGame().getAnimals().remove(name);
        App.getInstance().getCurrentGame().getCurrentPlayer().getPlayerAnimals().remove(animal);
        if(animalTile != null){
            animalTile.setAnimal(null);
        }
        App.getInstance().getCurrentGame().getCurrentPlayer().addGold(animal.getPrice());
        return new Result(true, animal.getName() + " has been sold with price : " + animal.getPrice() + ".");
    }

    public boolean placeAnimal(Game game, Position position, Animal animal) {
        if (position.getX() < 0 || position.getY() < 0 ||
                position.getX() >= Map.mapHeight ||
                position.getY() >= Map.mapWidth) {
            return false;
        }

        if (!isPositionInPlayerFarm(position, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return false;
        }

        Tile tile = getTileByPosition(position);

        if (tile.getPerson() != null || tile.getAnimal() != null ||
                tile.getItem() != null || tile.getTileType() == TileType.Wall) {
            return false;
        }

        Place enclosure = null;

        if (animal.getAnimalType().getEnclosures().toString().equalsIgnoreCase("Coop")) {
            enclosure = getPlaceByType("Coop");
        } else if (animal.getAnimalType().getEnclosures().toString().equalsIgnoreCase("Barn")) {
            enclosure = getPlaceByType("Barn");
        }

        if (enclosure == null) {
            return false;
        }

        boolean isInEnclosure = false;
        Position enclosurePos = enclosure.getPosition();
        int enclosureHeight = 0;
        int enclosureWidth = 0;

        if (enclosure instanceof Coop) {
            enclosureHeight = 3;
            enclosureWidth = 4;
        } else if (enclosure instanceof Barn) {
            enclosureHeight = 3;
            enclosureWidth = 4;
        }

        isInEnclosure = position.getX() >= enclosurePos.getX() &&
                position.getX() < enclosurePos.getX() + enclosureHeight &&
                position.getY() >= enclosurePos.getY() &&
                position.getY() < enclosurePos.getY() + enclosureWidth;

        if (!isInEnclosure) {
            return false;
        }

        animal.setPosition(position);
        tile.setAnimal(animal);

        return true;
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

    public boolean isPlayerAdjacentToTile(Position targetPosition, Player player) {
        if (player == null) {
            player = App.getInstance().getCurrentGame().getCurrentPlayer();
        }

        Position playerPosition = player.getPosition();

        int dx = Math.abs(playerPosition.getX() - targetPosition.getX());
        int dy = Math.abs(playerPosition.getY() - targetPosition.getY());

        return dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0);
    }

    public Result craftingShowRecipes() {

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Place place = getTileByPosition(player.getPosition()).getPlace();

        if (place instanceof House) {
            ArrayList<CraftingType> availableCraftings = new ArrayList<>();

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
                if (isCraftingAvailable(craftingtype, miningLevel, farmingLevel, foragingLevel, recipes))
                    availableCraftings.add(craftingtype);
            }

            StringBuilder massage = new StringBuilder();
            massage.append("available craftings: ").append("\n");
            for (CraftingType availableCrafting : availableCraftings) {
                massage.append(availableCrafting.getName()).append("\n");
            }
            String finalMassage = massage.toString();

            return new Result(true, finalMassage);
        }
        return new Result(false, "you should be at house.");
    }

    public Result craftingCraft(String itemName) {

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Item> items = player.getInventory().getBackPack().getItems();
        BackPack backPack = player.getInventory().getBackPack();
        Place place = getTileByPosition(player.getPosition()).getPlace();

        if (place instanceof House) {
            ArrayList<Recipe> recipes = new ArrayList<>();
            for (Item item : items) {
                if (item instanceof Recipe) {
                    recipes.add((Recipe) item);
                }
            }

            CraftingType craftingType = findCraftingName(itemName);
            if (craftingType == null) {
                return new Result(false, "there is no such crafting");
            }
            if (!isCraftingAvailable(craftingType, player.getMiningAbility(), player.getFarmingAbility(), player.getForagingAbility(), recipes)) {
                return new Result(false, "you don't know the recipe");
            }
            if (inventoryFreeSpace() == 0) {
                return new Result(false, "your inventory is full.");
            }
            for (Item ingredient : craftingType.getIngredients()) {
                int ingredientInventory = backPack.getItemNumber(ingredient);
                int ingredientNeed = ingredient.getNumber();

                if (ingredientNeed > ingredientInventory) {
                    return new Result(false, "you don't have enough ingredients.");
                }
            }

            for (Item ingredient : craftingType.getIngredients()) {
                int ingredientInventory = backPack.getItemNumber(ingredient);
                int ingredientNeed = ingredient.getNumber();

                if (ingredientNeed < ingredientInventory) {
                    backPack.setItemNumber(ingredient, ingredientInventory - ingredientNeed);
                }
                if (ingredientNeed == ingredientInventory) {
                    backPack.removeItem(ingredient);
                }

                //TODO Update Energy

                Crafting crafting = new Crafting(craftingType, 1);
                backPack.addItem(crafting);
                return new Result(true, "your crafting has been added.");

            }
        }
        return new Result(false, "you should be at house.");
    }

    public Boolean isCraftingAvailable(CraftingType craftingtype, int miningLevel, int farmingLevel, int foragingLevel, ArrayList<Recipe> recipes) {

        String abilityType = craftingtype.getAbilityType();
        int abilityLevel = craftingtype.getAbilityLevel();
        String requiredRecipe = craftingtype.getRequiredRecipe();

        if (abilityType.equals(null) && requiredRecipe.equals(null)) {
            return true;
        }
        if (abilityType.equals("mining")) {
            if (abilityLevel <= miningLevel) {
                return true;
            }
        }
        if (abilityType.equals("farming")) {
            if (abilityLevel <= farmingLevel) {
                return true;
            }
        }
        if (abilityType.equals("foraging")) {
            if (abilityLevel <= foragingLevel) {
                return true;
            }
        }
        if (!requiredRecipe.equals(null)) {
            for (Recipe recipe : recipes) {
                if (requiredRecipe.equals(recipe.getName()))
                    return true;
            }
        }
        return false;
    }

    public static CraftingType findCraftingName(String itemName) {
        for (CraftingType craftingType : CraftingType.values()) {
            if (itemName.equals(craftingType.getName())) {
                return craftingType;
            }
        }
        return null;
    }

    public int inventoryFreeSpace() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Item> items = player.getInventory().getBackPack().getItems();
        int capacity = player.getInventory().getBackPack().getBackpackType().getCapacity();

        return capacity - items.size();
    }

    public Result createCoop(Position coopPosition, Game game) {
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if (!(playerTile.getPlace() instanceof CarpenterShop)) {
            return new Result(false, "you should go to Carpenter shop first!");
        }
        Coop newCoop = new Coop(coopPosition, 3, 4);
        if (!isPositionInPlayerFarm(coopPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 4, coopPosition, newCoop)) {
            return new Result(false, "can not build coop in this place.");
        }
        // TODO check the minerals amount of player to build coop
        App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getPlaces().add(newCoop);
        return new Result(true, "coop build successfully.");
    }

    public Result createBarn(Position barnPosition, Game game) {
        Position playerPosition = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition();
        Tile playerTile = getTileByPosition(playerPosition);
        if (!(playerTile.getPlace() instanceof CarpenterShop)) {
            return new Result(false, "you should go to Carpenter shop first!");
        }
        Barn newBarn = new Barn(barnPosition, 3, 4);
        if (!isPositionInPlayerFarm(barnPosition, App.getInstance().getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "this position is not in your farm.");
        }
        if (!GameMenuControllers.setUpPlace(game, 3, 4, barnPosition, newBarn)) {
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

    public Item getItemInInventory(String itemName) {
        for(Item it: App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().getItems()){
            if(it.getName().equals(itemName)){
                return it;
            }
        }
        return null;
    }

    public Tile getTileByDirection(String direction) {
        int x = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getX();
        int y = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().getY();
        switch (direction) {
            case "up":
                return getTileByPosition(new Position(x - 1,y));
            case "down":
                return getTileByPosition(new Position(x + 1 ,y));
            case "left":
                return getTileByPosition(new Position(x,y - 1));
            case "right":
                return getTileByPosition(new Position(x,y + 1));
            case "up-left":
                return getTileByPosition(new Position(x - 1,y - 1));
            case "up-right":
                return getTileByPosition(new Position(x - 1,y + 1));
            case "down-left":
                return getTileByPosition(new Position(x + 1,y - 1));
            case "down-right":
                return getTileByPosition(new Position(x + 1,y + 1));
            default:
                return null;
        }
    }

    public Result useTool(String direction) {
        Tool tool = App.getInstance().getCurrentGame().getCurrentPlayer().getCurrentTool();
        Tile tile = getTileByDirection(direction);
        if(tool == null){
            return new Result(false, "Equip a tool before you use.!");
        } else if(tile == null){
            return new Result(false, "Invalid Tile!");
        } else if(tool instanceof Hoe){
            tool.use(tile);
            return new Result(true, "You used the Hoe.");
        } else if(tool instanceof Pickaxe){
            tool.use(tile);
            return new Result(true, "You used the Pickaxe.");
        } else if(tool instanceof Axe){
            tool.use(tile);
            return new Result(true, "You used the Axe.");
        } else if(tool instanceof WateringCan){
            tool.use(tile);
            return new Result(true, "You used the WateringCan.");
        }
        return new Result(false, "Incorrect Usage!");
    }

}
