package Assets;

import Models.DateTime.Season;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GrassAssets {

    private static final Sprite springGrass = new Sprite(new Texture("grass/spring.png"));
    private static final Sprite summerGrass =   new Sprite(new Texture("grass/summer.png"));
    private static final Sprite fallGrass =   new Sprite(new Texture("grass/fall.png"));
    private static final Sprite winterGrass =   new Sprite(new Texture("grass/winter.png"));


    public static Sprite getGrassSprite(Season season) {
        return switch (season) {
            case WINTER -> winterGrass;
            case SUMMER -> summerGrass;
            case FALL -> fallGrass;
            case SPRING -> springGrass;
        };
    }

}
