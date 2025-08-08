package Common.Network.Messages.MessageTypes;

import Common.Models.PlayerStuff.Player;
import Common.Models.Position;
import Common.Network.Messages.Message;


public class MovePlayerMessage extends Message {
    private final String playerName;
    private final float x;
    private final float y;
    private final Player.Direction direction;
    private final Position position;
    private final boolean moving;

    // Constructor
    public MovePlayerMessage(String playerId, float x, float y, Player.Direction direction, boolean moving, Position pos) {
        super(Message.MessageType.MOVE_PLAYER);
        this.playerName = playerId;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.moving = moving;
        this.position = pos;
    }

    // Getters
    public String getPlayerName() {
        return playerName;
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

    public Position getPosition() {
        return position;
    }



    // Optional: toString() for logging
    @Override
    public String toString() {
        return "MovePlayerMessage{" +
            "playerName='" + playerName + '\'' +
            ", x=" + x +
            ", y=" + y +
            ", direction=" + direction +
            ", moving=" + moving +
            '}';
    }
}
