package Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SlotAsset {
    private final Sprite slot;
    private final Sprite slotHover;

    public SlotAsset() {
        this.slot = new Sprite(new Texture("slot/slot.png"));
        this.slotHover = new Sprite(new Texture("slot/slotHover.png"));
    }

    public Sprite getSlot() {
        return slot;
    }

    public Sprite getSlotHover() {
        return slotHover;
    }
}
