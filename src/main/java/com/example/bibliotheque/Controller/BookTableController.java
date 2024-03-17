package com.example.bibliotheque.Controller;

import com.example.bibliotheque.App.Livre;
import com.example.bibliotheque.Database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTableController {
    @FXML
    private TableView<Livre> bookTableView;

    public void initialize() {
        bookTableView.getItems().clear();
        String selectQuery = "SELECT titre, auteur, isbn FROM livres";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<Livre> bookList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String title = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                long isbn = resultSet.getLong("isbn");

                Livre book = new Livre(title, author, isbn);
                bookList.add(book);
            }

            bookTableView.setItems(bookList);

        } catch (SQLException e) {
            e.printStackTrace();
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
}
