package Controllers;

import Models.*;
import Models.Animal.Animal;
import Models.Crafting.CraftingType;
import Models.Place.Barn;
import Models.Place.Coop;
import Models.PlayerStuff.Player;
import Models.Recipe.Recipe;
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

        for(CraftingType craftingtype : CraftingType.values()) {
            String abilityType = craftingtype.getAbilityType();
            int abilityLevel = craftingtype.getAbilityLevel();
            String requiredRecipe = craftingtype.getRequiredRecipe();

            if(abilityType.equals(null) && requiredRecipe.equals(null)) {
                availableCraftings.add(craftingtype);
            }
            if(abilityType.equals("mining")){
                if(abilityLevel <= miningLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if(abilityType.equals("farming")){
                if(abilityLevel <= farmingLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if(abilityType.equals("foraging")){
                if(abilityLevel <= foragingLevel) {
                    availableCraftings.add(craftingtype);
                }
            }
            if(!requiredRecipe.equals(null)) {
                for (Recipe recipe: recipes) {
                    if(requiredRecipe.equals(recipe.getName()))
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

    public Result createCoop(Position coopPosition, Game game){
        Coop newCoop = new Coop(coopPosition,3,3);
        if(!isPositionInPlayerFarm(coopPosition,App.getInstance().getCurrentGame().getCurrentPlayer())){
            return new Result(false, "this position is not in your farm.");
        }
        if(!GameMenuControllers.setUpPlace(game,3,3,coopPosition,newCoop)){
            return new Result(false,"can not build coop in this place.");
        }
        // TODO check the minerals amount of player to build coop
        return new Result(true, "coop build successfully.");
    }

    public Result createBarn(Position barnPosition, Game game){
        Barn newBarn = new Barn(barnPosition,3,3);
        if(!isPositionInPlayerFarm(barnPosition,App.getInstance().getCurrentGame().getCurrentPlayer())){
            return new Result(false, "this position is not in your farm.");
        }
        if(!GameMenuControllers.setUpPlace(game,3,3,barnPosition, newBarn)){
            return new Result(false,"can not build coop in this place.");
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
}
