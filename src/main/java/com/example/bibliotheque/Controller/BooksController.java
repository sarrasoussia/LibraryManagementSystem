package com.example.bibliotheque.Controller;

import com.example.bibliotheque.App.Livre;
import com.example.bibliotheque.Database.DatabaseConnector;
import com.example.bibliotheque.Service.LivreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BooksController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField searchTitleField;

    @FXML
    private TextField searchAuthorField;

    @FXML
    private TextField searchFirstLettersField;

    private final LivreService livreService = new LivreService();

    @FXML
    private void addBookAction(ActionEvent event) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String title = titleField.getText();
            String author = authorField.getText();
            long isbn = Long.parseLong(isbnField.getText());

            Livre livre = new Livre(title, author, isbn);
            livreService.ajouterLivre(connection, livre);

        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            livreService.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred. Please try again.");
        }
    }

    @FXML
    private void removeBookAction(ActionEvent event) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String title = titleField.getText();
            String author = authorField.getText();
            long isbn = 0 ;
            boolean parsingSuccessful = true;

            try {
                isbn = Long.parseLong(isbnField.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                parsingSuccessful = false;
                livreService.showAlert( Alert.AlertType.ERROR,"Error", "Invalid ISBN format. Please enter a valid number.");
            }

            if (parsingSuccessful) {
                Livre livreToRemove = new Livre(title, author, isbn);

                boolean success = livreService.supprimerLivre(connection, livreToRemove);

                if (success) {
                    livreService.showAlert(Alert.AlertType.INFORMATION,"Book Removed", "Book removed successfully!");
                } else {
                    livreService.showAlert( Alert.AlertType.ERROR,"Error", "Failed to remove book.");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            livreService.showAlert( Alert.AlertType.ERROR, "Error", "An error occurred. Please try again.");
        }
    }

    @FXML
    private void goBackToAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/welcome-view.fxml"));
            Parent booksView = loader.load();

            Scene scene = new Scene(booksView);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchBookAction(ActionEvent event) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String title = searchTitleField.getText();
            String author = searchAuthorField.getText();
            String firstLetters = searchFirstLettersField.getText();

            List<Livre> searchResults = livreService.searchBooks(connection, title, author, firstLetters);

            if (searchResults.isEmpty()) {
                livreService.showAlert(Alert.AlertType.INFORMATION, "No Results", "No books found matching the criteria.");
            } else {
                StringBuilder resultMessage = new StringBuilder("Search Results:\n\n");

                for (Livre livre : searchResults) {
                    resultMessage.append("Title: ").append(livre.getTitre())
                            .append(", Author: ").append(livre.getAuteur())
                            .append(", ISBN: ").append(livre.getIsbn())
                            .append("\n");
                }
                livreService.showAlert(Alert.AlertType.INFORMATION, "Search Results", resultMessage.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            livreService.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while searching for books.");
        }
    }


}
