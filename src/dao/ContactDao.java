package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> contactList = FXCollections.observableArrayList();

    public ContactDao() {
    }

    /**
     * GET Request for All Contacts from contacts table
     *
     * @return ObservableList<String> contactList
     */
    public static ObservableList<String> getContactList() {
        String selectStatement = "SELECT * FROM contacts";
        contactList.clear();
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                contactList.add(resultSet.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    /**
     * Row level fetch request for contactId from contactName
     *
     * @param name
     * @return
     */
    public static int getContactIdFromName(String name) {
        String selectStatement = "SELECT Contact_ID " +
                "FROM contacts " +
                "WHERE Contact_Name = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, name);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("Contact_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Row level fetch request for contactName from contactId
     *
     * @param id
     * @return
     */
    public static String getContactNameFromId(int id) {
        String selectStatement = "SELECT Contact_Name " +
                "FROM contacts " +
                "WHERE Contact_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Contact_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
