package Server.Network;

import Common.Network.ConnectionThread;
import Common.Network.Send.Message;

import java.io.IOException;
import java.net.Socket;

public class ServerToClientConnection extends ConnectionThread {

    private final String clientId;

    public ServerToClientConnection(Socket clientSocket, String clientId) throws IOException {
        super(clientSocket);
        this.clientId = clientId;
        this.setOtherSideIP(clientSocket.getInetAddress().getHostAddress());
        this.setOtherSidePort(clientSocket.getPort());
    }

    @Override
    public boolean initialHandshake() {
        // You can do something like sending a welcome message or version check
        System.out.println("Handshake started with client: " + clientId);
        return true;
    }

    @Override
    protected boolean handleMessage(Message message) {
        return false;
    }
}
