package Server.Controllers.FinalControllers;

import Client.Assets.CropAsset;
import Common.Models.App;
import Common.Models.DateTime.DateTime;
import Common.Models.Game;
import Common.Models.Map;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.Tile;

import java.util.ArrayList;
import java.util.List;

public class CropController {
    private CropAsset cropAsset;
    private Map gameMap;
    private Game game;
    private DateTime lastUpdatedDate;

    public CropController() {
        this.cropAsset = new CropAsset();
        this.game = App.getInstance().getCurrentGame();
        this.gameMap = game.getGameMap();
        this.lastUpdatedDate = game.getGameTime().copy();
    }

    public void update(float delta) {
        DateTime currentTime = App.getInstance().getCurrentGame().getGameTime();


        if (!isSameDate(currentTime, lastUpdatedDate)) {
            updateAllCrops();

            lastUpdatedDate = copyDateTime(currentTime);

            System.out.println("crops updated at :" +
                currentTime.getYear() + "/" + currentTime.getMonth() + "/" + currentTime.getDay());
        }
    }

        public void updateAllCrops() {
        for (int y = 0; y < Map.mapHeight; y++) {
            for (int x = 0; x < Map.mapWidth; x++) {
                Tile tile = gameMap.getMap()[y][x];
                if (tile.getCrop() != null) {
                    tile.getCrop().updateGrowth();
                }
            }
        }
    }

        private boolean isSameDate(DateTime date1, DateTime date2) {
        return date1.getYear() == date2.getYear() &&
               date1.getMonth() == date2.getMonth() &&
               date1.getDay() == date2.getDay();
    }

        private DateTime copyDateTime(DateTime original) {
        return new DateTime(
            original.getSeason(),
            original.getYear(),
            original.getMonth(),
            original.getDay(),
            original.getHour()
        );
    }

        public Crop harvestCrop(int tileX, int tileY) {
        Tile tile = gameMap.getMap()[tileY][tileX];

        if (tile.getCrop() != null && tile.getCrop().isHarvestable()) {
            Crop harvestedCrop = tile.getCrop();
            tile.setCrop(null);

            System.out.println("crop " + harvestedCrop.getName() + " harvested.");
            return harvestedCrop;
        }

        return null;
    }

        public boolean plantCrop(int tileX, int tileY, CropTypeNormal cropType) {
        Tile tile = gameMap.getMap()[tileY][tileX];

        if (tile.getItem() == null && tile.getPlace() == null && tile.getCrop() == null && tile.getisPlow()) {
            Crop newCrop = new Crop(cropType, 1);
            newCrop.setWhenPlanted(game.getGameTime().copy());
            tile.setCrop(newCrop);

            System.out.println("crop " + cropType.getName() + " planted.");
            return true;
        }

        return false;
    }

        public boolean waterCrop(int tileX, int tileY) {
        Tile tile = gameMap.getMap()[tileY][tileX];

        if (tile.getCrop() != null) {
            tile.setWatered(true);
            tile.getCrop().setLastWateredDate(game.getGameTime().copy());

            System.out.println("crop " + tile.getCrop().getName() + " watered!");
            return true;
        }

        return false;
    }

        public boolean fertilizeCrop(int tileX, int tileY) {
        Tile tile = gameMap.getMap()[tileY][tileX];

        if (tile.getCrop() != null && !tile.getCrop().isFertilized()) {
            tile.getCrop().setFertilized(true);

            System.out.println("crop " + tile.getCrop().getName() + " fertilized.");
            return true;
        }

        return false;
    }

        public List<Crop> getAllCrops() {
        List<Crop> crops = new ArrayList<>();

        for (int y = 0; y < Map.mapHeight; y++) {
            for (int x = 0; x < Map.mapWidth; x++) {
                Tile tile = gameMap.getMap()[y][x];
                if (tile.getCrop() != null) {
                    crops.add(tile.getCrop());
                }
            }
        }

        return crops;
    }

        public void dispose() {
        if (cropAsset != null) {
            cropAsset.dispose();
        }
    }
}
