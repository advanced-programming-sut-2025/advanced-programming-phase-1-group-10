package Controllers.FinalControllers;

import Models.App;
import Models.DateTime.DateTime;
import Models.DateTime.Season;
import Models.Map;
import Models.Planets.Tree;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeController {

    private final List<Tree> placedTrees;
    private DateTime lastUpdatedDate;

    public TreeController() {
        this.placedTrees = new ArrayList<>();

        this.lastUpdatedDate = new DateTime(Season.SPRING, 1990, 1, 1, 9);
    }

    public void addTree(Tree tree) {
        placedTrees.add(tree);
    }

    public void update(float delta) {
        DateTime currentTime = App.getInstance().getCurrentGame().getGameTime();


        if (!isSameDate(currentTime, lastUpdatedDate)) {
            for (Tree tree : placedTrees) {
                tree.updateGrowth();
            }

            removeChoppedTrees();

            lastUpdatedDate = copyDateTime(currentTime);

            System.out.println("trees updated at date : " +
                currentTime.getYear() + "/" + currentTime.getMonth() + "/" + currentTime.getDay());
        }
    }

    public void removeChoppedTrees() {
        Iterator<Tree> iterator = placedTrees.iterator();
        while (iterator.hasNext()) {
            Tree tree = iterator.next();
            if (tree.isChoped()) {
                iterator.remove();
            }
        }
    }



    public static boolean isSameDate(DateTime date1, DateTime date2) {
        return date1.getYear() == date2.getYear() &&
            date1.getMonth() == date2.getMonth() &&
            date1.getDay() == date2.getDay();
    }

    public static DateTime copyDateTime(DateTime original) {
        return new DateTime(
            original.getSeason(),
            original.getYear(),
            original.getMonth(),
            original.getDay(),
            original.getHour()
        );
    }

    public void render(SpriteBatch batch) {
        Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
        for (Tree tree : placedTrees) {
            Sprite treeSprite = tree.getSprite(currentSeason);
            if (treeSprite != null) {
                float x = tree.getPosition().getX();
                float y = tree.getPosition().getY();
                x *= Map.tileSize;
                y *= Map.tileSize;
                if(tree != null && !tree.isChoped()) {
                    batch.draw(treeSprite, y - 32, x, treeSprite.getWidth(), treeSprite.getHeight());
                }
            }
        }
    }

    public List<Tree> getPlacedTrees() {
        return placedTrees;
    }
}
