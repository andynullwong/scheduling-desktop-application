package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;

public class FirstLevelDivisionDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> firstLevelDivisionList = FXCollections.observableArrayList();

    public FirstLevelDivisionDao() {
    }

    public static ObservableList<String> getFirstLevelDivisionList(String country) {
        String selectStatement = "SELECT * " +
                "FROM countries c " +
                "INNER JOIN first_level_divisions fld " +
                "ON c.Country_ID = fld.COUNTRY_ID " +
                "WHERE Country=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, country);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            firstLevelDivisionList.clear();

            while (resultSet.next()) {
                int id = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryId = resultSet.getInt("COUNTRY_ID");

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(id, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);

                firstLevelDivisionList.add(firstLevelDivision.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivisionList;
    }

    public static int getIdFromDivision(String division) {
        String selectStatement = "SELECT Division_ID " +
                "FROM first_level_divisions " +
                "WHERE Division=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, division);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getDivisionFromId(int id) {
        String selectStatement = "SELECT Division " +
                "FROM first_level_divisions " +
                "WHERE Division_ID=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Division");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * B. Write at least two different lambda expressions to improve your code.
     * Maps ObservableList<Customer> customerList to an ObservableList<String> to populate dropdown
     *
     * Allows for linear search for firstLevelDivisionNames without needing to query the database for redundant information
     *
     * @return firstLevelDivisionNameList
     */
    public static ObservableList<String> getFirstLevelDivisionNameList() {
        ObservableList<String> firstLevelDivisionNameList = FXCollections.observableArrayList();
        firstLevelDivisionList.forEach(fld -> firstLevelDivisionNameList.add(fld.toString()));
        return firstLevelDivisionNameList;
    }
}
