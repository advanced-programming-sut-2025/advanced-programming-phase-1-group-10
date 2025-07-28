package Models;

import Assets.OtherAssets;
import Models.Animal.Animal;
import Models.Place.Place;
import Models.Planets.Seed;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.print.attribute.standard.MediaSize;

public class Tile {

    public Tile() {

    }

    private Person person;
    private Farm farm;
    private Place place;
    private Animal animal;

    private Position position;
    private TileType tileType;
    private Item item;

    private boolean plow = false;
    private boolean watered = false;
    private boolean fertilizer = false;

    private TextureRegion assetRegionOutside;
    private TextureRegion assetRegionInside;
    private boolean renderInside = false; // default to false

    private Seed plantedSeed;

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean getisPlow() {
        return plow;
    }

    public void setPlow(boolean plow) {
        this.plow = plow;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Position getPosition() {
        return position;
    }

    public void setPlantedSeed(Seed plantedSeed) {
        this.plantedSeed = plantedSeed;
    }

    public boolean isFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(boolean fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getTile() {
        return " ";
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void render(SpriteBatch batch, float x, float y) {
        TextureRegion regionToDraw = null;

        if (renderInside && assetRegionInside != null) {
            regionToDraw = assetRegionInside;
        } else if (!renderInside && assetRegionOutside != null) {
            regionToDraw = assetRegionOutside;
        }

        if (regionToDraw != null) {
            batch.draw(TileType.Grass.getSprite(), x,y);
            batch.draw(regionToDraw, x, y);
        } else if (tileType ==TileType.Wall){
            batch.draw(tileType.getSprite(), x, y);
            batch.draw(OtherAssets.bush, x, y);
        } else {
            batch.draw(tileType.getSprite(), x, y);
        }
    }

    public void setAssetRegionOutside(TextureRegion region) {
        this.assetRegionOutside = region;
    }

    public TextureRegion getAssetRegionOutside() {
        return assetRegionOutside;
    }

    public boolean isWalkable() {
        return tileType.isWalkable() && item == null && animal == null;
    }

    public void setAssetRegionInside(TextureRegion region) {
        this.assetRegionInside = region;
    }

    public TextureRegion getAssetRegionInside() {
        return assetRegionInside;
    }

    public void setRenderInside(boolean inside) {
        this.renderInside = inside;
    }

    public boolean isRenderInside() {
        return renderInside;
    }
}



