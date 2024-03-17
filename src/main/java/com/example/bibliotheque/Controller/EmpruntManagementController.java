package com.example.bibliotheque.Controller;

import com.example.bibliotheque.Service.EmpruntService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;  // Import the TextField class
import javafx.stage.Stage;

import java.io.IOException;

public class EmpruntManagementController {

    @FXML
    private TextField cinField;

    @FXML
    private TextField isbnField;

    private final EmpruntService empruntService = new EmpruntService();

    @FXML
    private void empruntAction(ActionEvent e) {
        try {
           long cin = Long.parseLong(cinField.getText());
           long isbn = Long.parseLong(isbnField.getText());

            if (empruntService.lecteurExists(cin)) {
                if (empruntService.livreExists(isbn)) {
                    empruntService.emprunterLivre(cin, isbn);
                } else {
                    System.out.println("Livre non trouvé dans la base de données.");
                    showAlert(Alert.AlertType.ERROR, "Error", "Inexistant book.");
                }
            } else {
                System.out.println("Lecteur non trouvé dans la base de données.");
                showAlert(Alert.AlertType.ERROR, "Error", "Inexistant adherent.");

            }
        } catch (NumberFormatException ex) {
            System.out.println("Veuillez entrer un numéro de CIN valide.");
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid CIN!");

        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void retournerAction(ActionEvent e) {
        try {
            int cin = Integer.parseInt(cinField.getText());
            int isbn = Integer.parseInt(isbnField.getText());

            if (empruntService.lecteurExists(cin)) {
                if (empruntService.livreExists(isbn)) {
                    empruntService.retournerLivre(cin, isbn);
                } else {
                    System.out.println("Livre non trouvé dans la base de données.");
                    showAlert(Alert.AlertType.ERROR, "Error", "Inexistant book.");
                }
            } else {
                System.out.println("Lecteur non trouvé dans la base de données.");
                showAlert(Alert.AlertType.ERROR, "Error", "Inexistant adherent.");

            }
        } catch (NumberFormatException ex) {
            System.out.println("Veuillez entrer un numéro de CIN valide.");
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid CIN!");

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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
