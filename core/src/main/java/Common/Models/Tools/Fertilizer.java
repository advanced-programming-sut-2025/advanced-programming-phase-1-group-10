package Common.Models.Tools;

import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Item;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
