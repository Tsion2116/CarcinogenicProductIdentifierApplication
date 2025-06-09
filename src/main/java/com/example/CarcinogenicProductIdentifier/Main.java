package com.example.CarcinogenicProductIdentifier;

import com.example.CarcinogenicProductIdentifier.database.DatabaseAccessor;
import com.example.CarcinogenicProductIdentifier.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ensure the database and tables are created
        DatabaseAccessor.createNewDatabase();
        DatabaseAccessor.createNewTables();

        // Run the Swing UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}