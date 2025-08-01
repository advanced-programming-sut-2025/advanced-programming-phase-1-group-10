package Models.Animal;

import Assets.AnimalProductAsset;
import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimalProduct implements Item {

    private AnimalProductType animalProductType;
    private int numberOfCooking;
    private ProductQuality productQuality;
    private int price;
    private AnimalProductAsset animalProductAsset;

    public AnimalProduct(AnimalProductType animalProductType, int numberOfCooking) {
        this.animalProductType = animalProductType;
        this.numberOfCooking = numberOfCooking;
        this.animalProductAsset = new AnimalProductAsset();
    }

    public AnimalProduct(AnimalProductType animalProductType, ProductQuality productQuality,int numberOfCooking) {
        this.animalProductType = animalProductType;
        this.productQuality = productQuality;
        this.price = animalProductType.getPrice();
        this.numberOfCooking = numberOfCooking;
        this.animalProductAsset = new AnimalProductAsset();
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }
    public ProductQuality getProductQuality() {
        return productQuality;
    }

    public int getPrice() {
        return switch (productQuality) {
            case NORMAL -> price;
            case SILVER -> (int) (1.25 * price);
            case GOLD -> (int) (1.5 * price);
            case IRIDIUM -> 2 * price;
        };
    }

    @Override
    public String getName() {
        return animalProductType.getName();
    }

    @Override
    public Sprite show() {
        return animalProductAsset.getSprite(animalProductType.getName());
    }

    @Override
    public void setNumber(int number) {
        this.numberOfCooking = number;
    }

    @Override
    public Item copyItem(int number) {
        return new AnimalProduct(animalProductType, number);
    }

    @Override
    public int getNumber() {
        return this.numberOfCooking;
    }
}
