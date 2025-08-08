package Common.Network;

import Common.Network.Send.Message;
import Common.Network.Send.MessageTypes.LobbyMessages.LeavelobbyMessage;
import Common.Utilis.JsonUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

abstract public class ConnectionThread extends Thread {
    protected final DataInputStream dataInputStream;
    protected final DataOutputStream dataOutputStream;
    protected final BlockingQueue<Message> receivedMessagesQueue;
    protected String otherSideIP;
    protected int otherSidePort;
    protected Socket socket;
    protected AtomicBoolean end;
    protected boolean initialized = false;
    protected String username;
    protected String lobbyId;

    protected ConnectionThread(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
    }

    public void setUserAndLobby(String username, String lobbyId) {
        this.username = username;
        this.lobbyId = lobbyId;
    }

    abstract public boolean initialHandshake();

    abstract protected boolean handleMessage(Message message);

    public synchronized void sendMessage(Message message) {
        String JSONString = JsonUtils.toJson(message);

        try {
            dataOutputStream.writeUTF(JSONString);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        initialized = false;
        if (!initialHandshake()) {
            System.err.println("Initial Handshake failed with remote device.");
            safeLeaveLobbyBeforeClose();
            end();
            onConnectionClosed();
            return;
        }

        initialized = true;
        while (!end.get()) {
            try {
                String receivedStr = dataInputStream.readUTF();
                System.out.println("Received raw JSON: " + receivedStr);
                Message message = JsonUtils.fromJsonWithType(receivedStr);

                boolean handled = handleMessage(message);
                if (!handled) {
                    try {
                        receivedMessagesQueue.put(message);
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted while putting message into queue: " + e.getMessage());
                    }
                }
            } catch (EOFException | SocketException e) {
                System.out.println("Connection closed: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Exception in connection thread: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        safeLeaveLobbyBeforeClose();
        end();
        onConnectionClosed();
    }

    private void safeLeaveLobbyBeforeClose() {
        try {
            if (!socket.isClosed() && username != null && lobbyId != null) {
                Message leaveMessage = new LeavelobbyMessage(username, lobbyId);
                sendMessage(leaveMessage);
                System.out.println("LeaveLobbyMessage sent for user: " + username);
            }
        } catch (Exception ex) {
            System.err.println("Failed to send LeaveLobbyMessage: " + ex.getMessage());
        }
    }

    public String getOtherSideIP() {
        return otherSideIP;
    }

    public void setOtherSideIP(String otherSideIP) {
        this.otherSideIP = otherSideIP;
    }

    public int getOtherSidePort() {
        return otherSidePort;
    }

    public void setOtherSidePort(int otherSidePort) {
        this.otherSidePort = otherSidePort;
    }

    protected void onConnectionClosed() {
        // Override in subclasses if needed
    }

    public void end() {
        end.set(true);
        try {
            socket.shutdownInput();
        } catch (IOException ignored) {}
        try {
            socket.close();
        } catch (IOException ignored) {}
    }
}
