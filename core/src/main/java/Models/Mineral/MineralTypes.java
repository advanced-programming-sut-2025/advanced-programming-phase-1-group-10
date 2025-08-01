package Models.Mineral;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum MineralTypes {

    QUARTZ("Quartz","A clear crystal commonly found in caves and mines.",25,true),
    EARTH_CRYSTAL("Earth Crystal","A resinous substance found near the surface.",50,true),
    FROZEN_TEAR("Frozen Tear","A crystal fabled to be the frozen tears of a yeti.",75,true),
    FIRE_QUARTZ("Fire Quartz","A glowing red crystal commonly found near hot lava.",100,true),
    EMERALD("Emerald","A precious stone with a brilliant green color.",250,true),
    AQUAMARINE("Aquamarine","A shimmery blue-green gem.",180,true),
    RUBY("Ruby","A precious stone that is sought after for its rich color and beautiful luster.",250,true),
    AMETHYST("Amethyst","A purple variant of quartz.",100,true),
    TOPAZ("Topaz","Fairly common but still prized for its beauty.",80,true),
    JADE("Jade","A pale green ornamental stone.",200,true),
    DIAMOND("Diamond","A rare and valuable gem.",750,true),
    PRISMATIC_SHARD("Prismatic Shard","A very rare and powerful substance with unknown origins.",2000,true),
    COPPER("Copper","A common ore that can be smelted into bars.",5,true),
    IRON("Iron","A fairly common ore that can be smelted into bars.",10,true),
    GOLD("Gold","A precious ore that can be smelted into bars.",25,true),
    IRIDIUM("Iridium","An exotic ore with many curious properties. Can be smelted into bars.",100,true),
    COAL("Coal","A combustible rock that is useful for crafting and smelting.",15,true),
    STONE("Stone","A useless mineral for useless things",1,true),
    ;

    private final String name;
    private final String description;
    private final int sellPrice;
    private final boolean isForaging;
    private final Sprite node;
    private final Sprite ore;

    MineralTypes(String name, String description, int sellPrice, boolean isForaging) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
        this.isForaging = isForaging;
        this.node = new Sprite(new Texture("node/" + name.replace(" ", "_").toLowerCase() + ".png"));
        this.ore = new Sprite(new Texture("ore/" + name.replace(" ", "_").toLowerCase() + ".png"));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public Sprite getNode() {
        return node;
    }

    public Sprite getOre() {
        return ore;
    }
}
