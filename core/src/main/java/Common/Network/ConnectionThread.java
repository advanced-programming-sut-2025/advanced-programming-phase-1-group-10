package Common.Network;

import Common.Network.Send.Message;
import Common.Utilis.JsonUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    protected ConnectionThread(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
    }

    abstract public boolean initialHandshake();

    abstract protected boolean handleMessage(Message message);

    public synchronized void sendMessage(Message message) {
        String JSONString = JsonUtils.toJson(message);

        try {
            dataOutputStream.writeUTF(JSONString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initialized = false;
        if (!initialHandshake()) {
            System.err.println("Initial Handshake failed with remote device.");
            end();
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
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.err.println("Exception in connection thread: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        end();
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

    public void end() {
        end.set(true);
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
