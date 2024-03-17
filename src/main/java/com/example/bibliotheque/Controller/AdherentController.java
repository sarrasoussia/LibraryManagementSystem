package com.example.bibliotheque.Controller;
import javafx.scene.control.TableCell;

import com.example.bibliotheque.App.Lecteur;
import com.example.bibliotheque.Database.DatabaseConnector;
import com.example.bibliotheque.Service.LecteurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdherentController {
    @FXML
    public TextField adherentName;
    @FXML
    private TextField cinField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField feeField;

    private final LecteurService lecteurService = new LecteurService();
    private final ObservableList<Lecteur> adherentsList = FXCollections.observableArrayList();

    @FXML
    private void addAdherentAction(ActionEvent event) {
        try (Connection cnx = DatabaseConnector.getConnection()) {
            long cin = Long.parseLong(cinField.getText());
            boolean success = lecteurService.ajouterLecteur(new Lecteur(cin, nameField.getText(), surnameField.getText(), Integer.parseInt(ageField.getText()), 0) );

            if (success) {
                showAlert("Lecteur ajouté avec succès!");
                updateAdherentsTable();
            } else {
                showAlert("Un lecteur avec ce CIN existe déjà ou une erreur s'est produite.");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            showAlert( "Une erreur s'est produite. Veuillez réessayer.");
        }
    }

    @FXML
    private void removeAdherentAction(ActionEvent event) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            long cin = Long.parseLong(cinField.getText());

            boolean success = lecteurService.supprimerLecteur(connection, cin);

            if (success) {
                showAlert("Lecteur removed successfully!");
                updateAdherentsTable();
            } else {
                showAlert("Failed to remove Lecteur or Lecteur not found.");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            showAlert("An error occurred. Please try again.");
        }
    }

    @FXML
    private void goBackToAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotheque/welcome-view.fxml"));
            Parent welcomeView = loader.load();

            Scene scene = new Scene(welcomeView);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<Lecteur> adherentTableView;

    public void initialize() {
        TableColumn<Lecteur, Long> cinColumn = new TableColumn<>("CIN");
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));

        TableColumn<Lecteur, String> nameColumn = new TableColumn<>("Surname");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Lecteur, String> surnameColumn = new TableColumn<>("Name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Lecteur, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

//        TableColumn<Lecteur, Integer> feeColumn = new TableColumn<>("Subscription Fee");
//        feeColumn.setCellValueFactory(new PropertyValueFactory<>("abonnement_frais"));

        adherentTableView.getColumns().clear();
        adherentTableView.getColumns().addAll(cinColumn, nameColumn, surnameColumn,ageColumn);

        adherentTableView.setItems(adherentsList);
        loadAdherentsList();
    }

    private void loadAdherentsList() {
        try {
            ResultSet resultSet = LecteurService.getAdherentList();
            adherentsList.clear();

            while (resultSet.next()) {
                long cin = resultSet.getLong("cin");
                String name = resultSet.getString("nom");
                String surname = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
//                int fee = resultSet.getInt("abonnement_frais");
                Lecteur lecteur = new Lecteur(cin, surname, name, age, 0);
                adherentsList.add(lecteur);
            }
        } catch (SQLException ex) {
            showAlert("Error retrieving adherent list.");
        }
    }

    private void updateAdherentsTable() {
        loadAdherentsList();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Library Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void searchAdherent(ActionEvent event) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String name = adherentName.getText();
            List<Lecteur> searchResults = lecteurService.searchAdherentbyName(connection, name);

            if (searchResults.isEmpty()) {
                lecteurService.showAlert(Alert.AlertType.INFORMATION, "No Results", "No Adherent found.");
            } else {
                StringBuilder resultMessage = new StringBuilder("Search Results:\n\n");

                for (Lecteur lecteur : searchResults) {
                    resultMessage
                            .append("Name: ").append(lecteur.getNom())
                            .append("\n")
                            .append("Surname: ").append(lecteur.getPrenom())
                            .append("\n")
                            .append("Age: ").append(lecteur.getAge())
                            .append("\n")
                            .append("CIN: ").append(lecteur.getCin())
                            .append("\n");

                }
                lecteurService.showAlert(Alert.AlertType.INFORMATION, "Search Results", resultMessage.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            lecteurService.showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while searching for Adherents.");
        }
    }
}
