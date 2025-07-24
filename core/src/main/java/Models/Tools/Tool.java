package Models.Tools;

import Assets.ToolAsset;
import Models.Item;
import Models.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Tool implements Item {
    private String name;
    private int energyUsage;
    private Quality quality;
    protected ToolAsset toolAsset;

    public Tool(Quality quality, int energyUsage) {
        this.quality = quality;
        this.energyUsage = energyUsage;
        this.toolAsset = new ToolAsset();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Sprite show() {
        return null;
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public void setNumber(int number) {
        //Must not be coded, because number of tool is constant (one per player);
    }

    @Override
    public Item copyItem(int number) {
        //Tool mustn't copy
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergyUsage() {
        return energyUsage - quality.getValue();
    }

    public void setEnergyUsage(int energyUsage) {
        this.energyUsage = energyUsage;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public void use(Tile tile) {

    }

}
