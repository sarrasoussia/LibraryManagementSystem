package com.example.bibliotheque.Service;

import com.example.bibliotheque.App.Livre;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LivreService {

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(String.valueOf(title));
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void ajouterLivre(Connection connection, Livre livre) throws SQLException {
        if (livreExists(connection, livre.getIsbn())) {
            showAlert(Alert.AlertType.WARNING, "Duplicate ISBN", "A book with this ISBN already exists.");
            return;
        }

        String insertQuery = "INSERT INTO livres (titre, auteur, isbn) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setLong(3, livre.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book.");
            }
        }
    }
    private boolean livreExists(Connection connection, long isbn) throws SQLException {
        String selectQuery = "SELECT * FROM livres WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, isbn);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean supprimerLivre(Connection connection, Livre livre) throws SQLException {
        String deleteQuery = "DELETE FROM Livres WHERE titre = ? AND auteur = ? AND isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setLong(3, livre.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public List<Livre> searchBooks(Connection connection, String title, String author, String firstLetters)
            throws SQLException {
        String selectQuery = "SELECT * FROM Livres";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            List<Livre> livres = new ArrayList<>();
            while (resultSet.next()) {
                Livre livre = new Livre(resultSet.getString("titre"), resultSet.getString("auteur"),
                        resultSet.getLong("isbn"));
                livres.add(livre);
            }

            return livres.stream().filter(livre -> title == null || livre.getTitre().toLowerCase().contains(title.toLowerCase()))
                    .filter(livre -> author == null || livre.getAuteur().toLowerCase().contains(author.toLowerCase()))
                    .filter(livre -> firstLetters == null || livre.getTitre().toLowerCase().startsWith(firstLetters.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

}
