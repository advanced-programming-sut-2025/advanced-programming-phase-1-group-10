package Models.Tools;

import Models.App;
import Models.Item;
import Models.Planets.Crop.Crop;
import Models.Planets.Seed;
import Models.PlayerStuff.Player;
import Models.Tile;
import Models.TileType;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SeedPlanter extends Tool {

    public SeedPlanter(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Sprite show() {
        // این متد باید Sprite مربوط به SeedPlanter را برگرداند.
        // toolAsset.show(this); // فرض می‌شود این متد وجود دارد
        return null; // فعلا null برگردانده می‌شود
    }

//    @Override
//    public void use(Tile tile) {
//        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
//        Item selectedItem = player.getInventory().getBackPack().getSelectedItem();
//
//        if (selectedItem instanceof Seed) {
//            Seed seed = (Seed) selectedItem;
//            // بررسی شرایط کاشت: کاشی باید شخم زده شده باشد و محصولی در آن نباشد.
//            if (tile.isPlowed() && tile.getCrop() == null) {
//                // محاسبه و کسر انرژی
//                int energyCost = (int) ((getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
//                if (player.getEnergy().getEnergyAmount() >= energyCost) {
//                    player.getEnergy().setEnergyAmount(player.getEnergy().getEnergyAmount() - energyCost);
//
//                    // ایجاد Crop جدید و قرار دادن آن روی کاشی
//                    Crop newCrop = new Crop(seed.getCropType());
//                    tile.setCrop(newCrop);
//
//                    // کاهش تعداد بذر در کوله‌پشتی
//                    if (seed.getNumber() > 1) {
//                        seed.setNumber(seed.getNumber() - 1);
//                    } else {
//                        player.getInventory().getBackPack().removeItem(seed);
//                    }
//                }
//            }
//        }
//    }
}
