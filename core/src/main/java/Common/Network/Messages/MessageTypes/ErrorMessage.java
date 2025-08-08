package Common.Network.Messages.MessageTypes;

import Common.Network.Messages.Message;

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
