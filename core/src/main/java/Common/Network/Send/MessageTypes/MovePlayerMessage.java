package Common.Network.Send.MessageTypes;

import Common.Models.PlayerStuff.Player;
import Common.Network.Send.Message;


public class MovePlayerMessage extends Message {
    private final String playerId;
    private final float x;
    private final float y;
    private final Player.Direction direction;
    private final boolean moving;

    // Constructor
    public MovePlayerMessage(String playerId, float x, float y, Player.Direction direction, boolean moving) {
        super(Message.MessageType.MOVE_PLAYER);
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.moving = moving;
    }

    // Getters
    public String getPlayerId() {
        return playerId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Player.Direction getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return moving;
    }

    // Optional: toString() for logging
    @Override
    public String toString() {
        return "MovePlayerMessage{" +
            "playerId='" + playerId + '\'' +
            ", x=" + x +
            ", y=" + y +
            ", direction=" + direction +
            ", moving=" + moving +
            '}';
    }
}
