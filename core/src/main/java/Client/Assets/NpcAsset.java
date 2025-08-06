package Client.Assets;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class NpcAsset {

    private static final String[] NPC_NAMES = {
        "abigel", "lia", "harvey", "sebastian", "robbin"
    };

    // Each NPC has 4 animations (0.png to 3.png per animation)
    private final HashMap<String, Animation<TextureRegion>[]> npcAnimations;

    public NpcAsset() {
        npcAnimations = new HashMap<>();
        loadAllNpcAnimations();
    }

    private void loadAllNpcAnimations() {
        for (String name : NPC_NAMES) {
            Animation<TextureRegion>[] animations = new Animation[4]; // 4 animations per NPC
            for (int i = 0; i < 4; i++) {
                String path = "npc/" + name + "/" + i + ".png";
                Texture texture = new Texture(path);
                TextureRegion frame = new TextureRegion(texture);

                // If each animation is a static frame, wrap it in a 1-frame Animation
                Array<TextureRegion> frames = new Array<>();
                frames.add(frame);
                animations[i] = new Animation<>(0.2f, frames);
            }
            npcAnimations.put(name, animations);
        }
    }

    public Animation<TextureRegion>[] getAnimationsFor(String npcName) {
        return npcAnimations.get(npcName.toLowerCase());
    }

    public Animation<TextureRegion> getAnimation(String npcName, int animationIndex) {
        Animation<TextureRegion>[] animations = npcAnimations.get(npcName.toLowerCase());
        if (animations != null && animationIndex >= 0 && animationIndex < animations.length) {
            return animations[animationIndex];
        }
        return null;
    }
}
