package Controllers;

import Models.*;
import Models.Animal.Animal;
import Models.Crafting.Crafting;
import Models.Crafting.CraftingType;
import Models.Place.House;
import Models.Place.Place;
import Models.PlayerStuff.Player;
import Models.Recipe.Recipe;
import Models.Tools.BackPack;
import Models.Tools.Tool;

import java.util.ArrayList;
import java.util.List;

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

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Place place = getTileByPosition(player.getPosition()).getPlace();

        if(place instanceof House) {
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
                if(isCraftingAvailable(craftingtype, miningLevel, farmingLevel, foragingLevel, recipes))
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

        Player player =  App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Item> items = player.getInventory().getBackPack().getItems();
        BackPack backPack = player.getInventory().getBackPack();
        Place place = getTileByPosition(player.getPosition()).getPlace();

        if(place instanceof House) {
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

    public static CraftingType findCraftingName(String itemName){
        for (CraftingType craftingType : CraftingType.values()) {
            if(itemName.equals(craftingType.getName())){
                return craftingType;
            }
        }
        return null;
    }

    public int inventoryFreeSpace(){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Item> items = player.getInventory().getBackPack().getItems();
        int capacity = player.getInventory().getBackPack().getBackpackType().getCapacity();

        return capacity - items.size();
    }

    public Tile getTileByPosition(Position position) {
        return App.getInstance().getCurrentGame().getGameMap().getMap()[position.getX()][position.getY()];
    }
}
