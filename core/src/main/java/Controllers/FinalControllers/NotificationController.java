package Controllers.FinalControllers;

import Controllers.MessageSystem;
import Models.App;
import Models.FriendShip.Gift;
import Models.FriendShip.Message;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.graphics.Color;

public class NotificationController {

    public void update() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        notifyUnseenMessages(player);
        notifyUnseenGifts(player);
    }

    private void notifyUnseenMessages(Player player) {
        for (Message msg : player.getRecievedMessages()) {
            if (!msg.isNotified()) {
                msg.setNotified(true);
                MessageSystem.showMessage(
                    "You have message from " + msg.getSender().getName(),
                    2f,
                    Color.GREEN
                );
            }
        }
    }

    private void notifyUnseenGifts(Player player) {
        for (Gift gift : player.getRecievedGifts()) {
            if (!gift.isNotified()) {
                gift.setNotified(true);
                MessageSystem.showMessage(
                    "You received gift from " + gift.getSender().getName(),
                    2f,
                    Color.GREEN
                );
            }
        }
    }
}
