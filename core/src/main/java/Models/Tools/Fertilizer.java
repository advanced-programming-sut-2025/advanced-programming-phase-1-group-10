package Models.Tools;

import Controllers.MessageSystem;
import Models.App;
import Models.Item;
import Models.Planets.Crop.Crop;
import Models.Planets.Tree;
import Models.PlayerStuff.Player;
import Models.Tile;
import Models.TileType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TideMapLoader;

public class Fertilizer implements Item {
    private String name;
    private int price;
    private final Sprite fertilizerSprite = new Sprite(new Texture("tools/Fertilizer.png"));
    private int numberOfFertilizer;

    public Fertilizer(int numberOfFertilizer){
        this.numberOfFertilizer = numberOfFertilizer;
        this.name = "Fertilizer";
        this.price = 500;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Sprite show() {
        return fertilizerSprite;
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public void setNumber(int number) {

    }

    @Override
    public Item copyItem(int number) {
        return null;
    }

    public int getPrice() {
        return price;
    }

    public void use(Tile tile){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(!tile.getisPlow()){
            MessageSystem.showWarning("You should plow this tile first!",3.0f);
        }
        else {
            tile.setFertilizer(true);
            numberOfFertilizer--;
            if(numberOfFertilizer <= 0){
                player.setFarmingAbility(player.getFarmingAbility() + 10);
                player.getInventory().getBackPack().removeItem(this);
                player.getIventoryBarItems().remove(this);
            }
        }
    }
}
