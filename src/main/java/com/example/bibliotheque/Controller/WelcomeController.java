package com.example.bibliotheque.Controller;

import com.example.bibliotheque.Service.EmpruntService;
import com.example.bibliotheque.Service.WelcomeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WelcomeController {

    @FXML
    private void openBookManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/books-view.fxml"));
            Parent booksView = loader.load();
            Scene scene = new Scene(booksView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEmpruntManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/emprunt-view.fxml"));
            Parent booksView = loader.load();
            Scene scene = new Scene(booksView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAdherentManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/adherent-view.fxml"));
            Parent booksView = loader.load();
            Scene scene = new Scene(booksView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openBookList(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/bookList-view.fxml"));
            Parent booksView = loader.load();
            Scene scene = new Scene(booksView);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final WelcomeService welcomeService = new WelcomeService();

    @FXML
    private void openAdherentList(ActionEvent event) {
        try {
            ResultSet resultSet = welcomeService.getAdherentList();
            StringBuilder adherentList = new StringBuilder();
            adherentList.append("Adherent List:\n\n");
            while (resultSet.next()) {
                long cin = resultSet.getLong("cin");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                adherentList.append(String.format("Prenom: %s, Nom: %s, CIN: %d\n\n", prenom, nom, cin));
            }
            showAlert(adherentList.toString());
        } catch (SQLException ex) {
            showAlert("Error retrieving books list.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Library Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/main-view.fxml"));
        Parent booksView = loader.load();
        Scene scene = new Scene(booksView);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


}