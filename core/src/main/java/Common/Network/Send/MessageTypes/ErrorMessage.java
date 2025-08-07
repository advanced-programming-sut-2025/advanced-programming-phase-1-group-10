package Common.Network.Send.MessageTypes;

import Common.Network.Send.Message;

public class ErrorMessage extends Message {
    private String errorMessage;

    public ErrorMessage(String errorMessage) {
        super(MessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
