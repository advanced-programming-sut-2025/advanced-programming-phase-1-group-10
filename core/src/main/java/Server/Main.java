package Server;

import Server.Network.ListenerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    private static final int DEFAULT_PORT = 12345;

    private static JFrame frame;
    private static JTextField portField;
    private static JPasswordField passwordField;
    private static JTextArea logArea;
    private static JButton startButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the window on the screen

        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Port label and field
        JLabel portLabel = new JLabel("Port:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(portLabel, gbc);

        portField = new JTextField(String.valueOf(DEFAULT_PORT), 15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(portField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        // Start server button
        startButton = new JButton("Start Server");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(startButton, gbc);

        // Log display area
        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Action for start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int port = Integer.parseInt(portField.getText());
                    String password = new String(passwordField.getPassword());
                    if (password.isEmpty()) {
                        password = null;
                    }
                    startServer(port, password);
                } catch (NumberFormatException ex) {
                    logArea.append("Error: Invalid port number.\n");
                }
            }
        });

        frame.setVisible(true);
    }

    private static void startServer(int port, String password) {
        startButton.setEnabled(false);
        portField.setEnabled(false);
        passwordField.setEnabled(false);

        logArea.append("Attempting to start server on port " + port + "...\n");

        try {
            ListenerThread listener = new ListenerThread(port, password);
            listener.start();
            logArea.append("Server started successfully.\n");
        } catch (IOException e) {
            logArea.append("Error starting server: " + e.getMessage() + "\n");
            startButton.setEnabled(true);
            portField.setEnabled(true);
            passwordField.setEnabled(true);
        }
    }
}
