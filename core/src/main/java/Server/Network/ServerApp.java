package Server.Network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    public static final int MAX_PLAYERS = 4;

    // Stores usernames of joined players
    public static final List<String> playerUsernames = new ArrayList<>();

    // Stores their corresponding sockets (used to later create ServerToClientConnection)
    public static final List<Socket> playerSockets = new ArrayList<>();

    public static boolean gameStarted = false;
}

