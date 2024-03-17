package com.example.bibliotheque.Service;

import com.example.bibliotheque.App.Lecteur;
import com.example.bibliotheque.Database.DatabaseConnector;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LecteurService {

    public boolean ajouterLecteur(Lecteur lecteur) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            if (!lecteurExists(connection, lecteur.getCin())) {
                String insertQuery = "INSERT INTO lecteurs (cin, nom, prenom, age, abonnement_frais) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setLong(1, lecteur.getCin());
                    preparedStatement.setString(2, lecteur.getNom());
                    preparedStatement.setString(3, lecteur.getPrenom());
                    preparedStatement.setInt(4, lecteur.getAge());
                    preparedStatement.setInt(5, (int)lecteur.frais_Abonnement() );

                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerLecteur(Connection connection, long cin) throws SQLException {
        if (lecteurExists(connection, cin)) {
            String deleteDetailsempruntQuery = "DELETE FROM detailsemprunts WHERE lecteur_cin = ?";
            try (PreparedStatement detailsempruntStatement = connection.prepareStatement(deleteDetailsempruntQuery)) {
                detailsempruntStatement.setLong(1, cin);
                detailsempruntStatement.executeUpdate();
            }

            String deleteLecteurQuery = "DELETE FROM lecteurs WHERE cin = ?";
            try (PreparedStatement lecteurStatement = connection.prepareStatement(deleteLecteurQuery)) {
                lecteurStatement.setLong(1, cin);
                int rowsAffected = lecteurStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } else {
            return false;
        }
    }

    public boolean lecteurExists(Connection connection, long cin) throws SQLException {
        String selectQuery = "SELECT * FROM lecteurs WHERE cin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, cin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static ResultSet getAdherentList() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        String selectQuery = "SELECT cin,nom,prenom,abonnement_frais,age FROM lecteurs";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        return preparedStatement.executeQuery();
    }

    public List<Lecteur> searchAdherentbyName(Connection connection, String searchedName)
            throws SQLException {
        String selectQuery = "SELECT * FROM Lecteurs where nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, searchedName);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Lecteur> l = new ArrayList<>();
                while (resultSet.next()) {
                    Lecteur lecteur = new Lecteur(resultSet.getLong("cin"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("age"), resultSet.getDouble("abonnement_frais"));
                    l.add(lecteur);
                }

                return l.stream()
                        .filter(lecteur -> searchedName.isEmpty() || lecteur.getNom().toLowerCase().contains(searchedName.toLowerCase()))
                        .collect(Collectors.toList());


            }
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(String.valueOf(title));
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
