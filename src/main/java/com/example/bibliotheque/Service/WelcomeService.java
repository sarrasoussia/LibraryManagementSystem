package com.example.bibliotheque.Service;

import com.example.bibliotheque.Database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeService {
    public static ResultSet getAdherentList() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        String selectQuery = "SELECT * FROM lecteurs";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        return preparedStatement.executeQuery();
    }

}
