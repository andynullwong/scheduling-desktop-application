package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;

public class CountryDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> countryList = FXCollections.observableArrayList();

    public CountryDao() {
    }

    /**
     * GET Request for All Countries from countries table
     *
     * @return ObservableList<String> countryList
     */
    public static ObservableList<String> getCountryList() {
        String selectStatement = "SELECT * FROM countries";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            countryList.clear();

            while (resultSet.next()) {
                Country currentCountry = new Country();
                currentCountry.setId(resultSet.getInt("Country_ID"));
                currentCountry.setCountry(resultSet.getString("Country"));
                currentCountry.setCreateDate(resultSet.getTimestamp("Create_Date"));
                currentCountry.setCreatedBy(resultSet.getString("Created_By"));
                currentCountry.setCreateDate(resultSet.getTimestamp("Last_Update"));
                currentCountry.setCreatedBy(resultSet.getString("Last_Updated_By"));

                countryList.add(currentCountry.getCountry());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    /**
     * Row level fetch request for countryName from countryId
     *
     * @param id
     * @return
     */
    public static String getCountryFromId(int id) {
        String selectStatement = "SELECT Country " +
                "FROM countries " +
                "WHERE Country_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Row level fetch request for countryName from firstLevelDivisionId
     *
     * @param divisionId
     * @return
     */
    public static String getCountryFromFirstLevelDivisionId(int divisionId) {
        String selectStatement = "SELECT Country " +
                "FROM first_level_divisions fld " +
                "INNER JOIN countries c " +
                "ON fld.COUNTRY_ID = c.Country_ID " +
                "WHERE fld.Division_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, divisionId);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
