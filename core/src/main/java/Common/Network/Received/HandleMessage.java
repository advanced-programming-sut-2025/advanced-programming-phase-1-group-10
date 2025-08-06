package Common.Network.Received;

import Common.Network.Send.Message;

public abstract class HandleMessage {
    public abstract void handle(Message message);
}
