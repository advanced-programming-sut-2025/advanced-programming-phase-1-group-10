package Controllers.FinalControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapLayerController {

    private boolean isMapLayerShown = false;

    private final Sprite mapLayer = new Sprite(new Texture("map.png"));

    final int mapWidth = 1024;
    final int mapHeight = 768;


    public void update(SpriteBatch batch) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            isMapLayerShown = !isMapLayerShown;
        }

        if(isMapLayerShown){
            batch.draw(mapLayer, (Gdx.graphics.getWidth() - mapWidth) / 2f, (Gdx.graphics.getHeight() - mapHeight) / 2f, mapWidth, mapHeight);
        }

    }
}
