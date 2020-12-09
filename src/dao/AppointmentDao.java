package dao;

import controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;
import java.time.LocalDate;

public class AppointmentDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public AppointmentDao() {
    }

    public static ObservableList<Appointment> getAppointmentList(String selectedFilter) {
        LocalDate selectedDate = MainMenuController.getSelectedDate();
        String sqlStatement = "SELECT * FROM appointments";

        try {
            switch(selectedFilter) {
                case "View Month":
                    sqlStatement += " WHERE MONTH(Start) = ? AND YEAR(Start) = ?";
                    DBQuery.setPreparedStatement(connection, sqlStatement);
                    break;
                case "View Week":
                    sqlStatement += " WHERE WEEK(Start) = WEEK(?) AND YEAR(Start) = ?";
                    break;
            }

            DBQuery.setPreparedStatement(connection, sqlStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            switch(selectedFilter) {
                case "View Month":
                    preparedStatement.setInt(1, selectedDate.getMonthValue());
                    preparedStatement.setInt(2, selectedDate.getYear());
                    break;
                case "View Week":
                    preparedStatement.setString(1, selectedDate.toString());
                    preparedStatement.setInt(2, selectedDate.getYear());
                    break;
            }

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            appointmentList.clear();

            while (resultSet.next()) {
                Appointment currentAppointment = new Appointment();
                currentAppointment.setId(resultSet.getInt("Appointment_ID"));
                currentAppointment.setTitle(resultSet.getString("Title"));
                currentAppointment.setDescription(resultSet.getString("Description"));
                currentAppointment.setLocation(resultSet.getString("Location"));
                currentAppointment.setContactId(resultSet.getInt("Contact_ID"));
                currentAppointment.setType(resultSet.getString("Type"));
                currentAppointment.setStart(resultSet.getTimestamp("Start"));
                currentAppointment.setEnd(resultSet.getTimestamp("End"));
                currentAppointment.setCustomerId(resultSet.getInt("Customer_ID"));

                appointmentList.add(currentAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static Boolean addAppointment(Appointment appointment) {
        String insertStatement = "INSERT INTO appointments (" +
                "Title, " +
                "Description, " +
                "Location, " +
                "Contact_ID, " +
                "Type, " +
                "Start, " +
                "End, " +
                "Customer_ID) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            DBQuery.setPreparedStatement(connection, insertStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setInt(4, appointment.getContactId());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setTimestamp(6, appointment.getStart());
            preparedStatement.setTimestamp(7, appointment.getEnd());
            preparedStatement.setInt(8, appointment.getCustomerId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean updateAppointment(Appointment appointment) {
        String selectStatement = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ? " +
                "WHERE Appointment_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setInt(4, appointment.getContactId());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setTimestamp(6, appointment.getStart());
            preparedStatement.setTimestamp(7, appointment.getEnd());
            preparedStatement.setInt(8, appointment.getCustomerId());
            preparedStatement.setInt(9, appointment.getId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteAppointment(int id) {
        String selectStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
