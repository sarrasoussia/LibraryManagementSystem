package com.example.bibliotheque.Service;

import com.example.bibliotheque.Database.DatabaseConnector;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmpruntService {

    public boolean lecteurExists(long cin) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM lecteurs WHERE cin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, cin);
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean livreExists(long isbn) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM livres WHERE isbn = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1,isbn);
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void emprunterLivre(long cin, long isbn) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            LocalDate currentDate = LocalDate.now();
            String insertQuery = "INSERT INTO detailsemprunts (livre_isbn, date_emprunt, date_retour, lecteur_cin) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setLong(1, (isbn));
                preparedStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                preparedStatement.setDate(3, java.sql.Date.valueOf(currentDate.plusDays(7)));
                preparedStatement.setLong(4, cin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Successful book print!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "An error occured.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQLException: " + e.getMessage());
        }
    }

    public void retournerLivre(long cin, long isbn) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            LocalDate currentDate = LocalDate.now();
            String updateQuery = "UPDATE detailsEmprunts SET date_retour = ? WHERE livre_isbn = ? AND lecteur_cin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(currentDate));
                preparedStatement.setLong(2, isbn);
                preparedStatement.setLong(3, cin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book return successful!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Adherent hasnt borrow the book!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQLException: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
