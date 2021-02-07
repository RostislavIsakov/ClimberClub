package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    @FXML
    private Button btnSelectQuery;

    @FXML
    private Button btnStoredProcedure;

    @FXML
    private Button btnTrigger;

    @FXML
    private TextField textFieldStoredProcedure;

    @FXML
    private TextField textFieldTrigger;

    @FXML
    private Label lblQuery;

    @FXML
    private Label lblProcedure;

    @FXML
    private Label lblTrigger;

    @FXML
    private ComboBox<String> comboBoxSelectQuery;

    @FXML
    void executeSelectQuery(ActionEvent event) {
        try (Connection connection = Main.getMyConnection()) {
            Statement statement = connection.createStatement();

            int value = comboBoxSelectQuery.getSelectionModel().getSelectedIndex();

            if (value == 0) {
                String query = "SELECT FIO, Address, Phone FROM Climbers WHERE Category = 1 ORDER BY FIO"; // Запрос 1
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.println(rs.getString("FIO"));
                    System.out.println(rs.getString("Address"));
                    System.out.println(rs.getString("Phone"));
                }
            }

            if (value == 1) {
                String query = "SELECT climbers_in_groups.ID_Group, FIO, Date_S, Date_E, Leader FROM climbers_in_groups " +
                        "INNER JOIN climbers ON climbers.ID_Climber = climbers_in_groups.ID_Climber " +
                        "INNER JOIN team ON team.ID_Group = climbers_in_groups.ID_Group"; // Запрос 2
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.println(rs.getInt("climbers_in_groups.ID_Group"));
                    System.out.println(rs.getString("FIO"));
                    System.out.println(rs.getString("Date_S"));
                    System.out.println(rs.getString("Date_E"));
                    System.out.println(rs.getString("Leader"));
                }
            }

            if (value == 2) {
                String query = "SELECT Name, (SELECT COUNT(ID_Climber) FROM climbers_in_groups " +
                        "WHERE climbers_in_groups.ID_Group = climbing.ID_Group) AS Количество FROM climbing " +
                        "INNER JOIN mountains ON mountains.ID_Mountain = climbing.ID_Mountain ORDER BY Name"; // Запрос 3
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.println(rs.getString("Name"));
                    System.out.println(rs.getInt("Количество"));
                }
            }

            if (value == 3) {
                String query = "SELECT ID_Group, FIO FROM climbers_in_groups " +
                        "INNER JOIN climbers ON climbers.ID_Climber = climbers_in_groups.ID_Climber " +
                        "WHERE ID_Group IN (SELECT ID_Group FROM climbing " +
                        "WHERE Date_A BETWEEN '2012.01.01' AND '2013.01.01')"; // Запрос 4
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.println(rs.getInt("ID_Group"));
                    System.out.println(rs.getString("FIO"));
                }
            }

            if (value == 4) {
                String query = "SELECT * FROM climbers " +
                        "WHERE ID_Climber IN (SELECT climbers_in_groups.ID_Climber FROM climbers_in_groups " +
                        "WHERE climbers_in_groups.ID_Group IN (SELECT climbing.ID_Group FROM climbing " +
                        "INNER JOIN mountains ON mountains.ID_Mountain = climbing.ID_Mountain " +
                        "WHERE mountains.Country LIKE 'Индия'))"; // Запрос 5
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    System.out.println(rs.getInt("ID_Climber"));
                    System.out.println(rs.getString("FIO"));
                    System.out.println(rs.getString("Address"));
                    System.out.println(rs.getString("Phone"));
                    System.out.println(rs.getInt("Category"));
                    System.out.println(rs.getString("Sex"));
                }
            }
            makeTheNotification(lblQuery, "Выполнено!", Color.GREEN);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            makeTheNotification(lblQuery, "Ошибка!", Color.RED);
        }
    }

    @FXML
    void executeStoredProcedure(ActionEvent event) {
        try (Connection connection = Main.getMyConnection()) {
            Statement statement = connection.createStatement();

            String query = textFieldStoredProcedure.getText();

            statement.executeQuery(query);
            makeTheNotification(lblProcedure, "Выполнено!", Color.GREEN);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            makeTheNotification(lblProcedure, "Ошибка!", Color.RED);
        }
    }

    @FXML
    void executeTrigger(ActionEvent event) {
        try (Connection connection = Main.getMyConnection()) {
            Statement statement = connection.createStatement();

            String query = textFieldTrigger.getText();

            statement.executeUpdate(query);
            makeTheNotification(lblTrigger, "Выполнено!", Color.GREEN);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            makeTheNotification(lblTrigger, "Ошибка!", Color.RED);
        }
    }

    @FXML
    void initialize() {
        initComboBox();
    }

    private void initComboBox() {
        comboBoxSelectQuery.getItems().add("Запрос 1");
        comboBoxSelectQuery.getItems().add("Запрос 2");
        comboBoxSelectQuery.getItems().add("Запрос 3");
        comboBoxSelectQuery.getItems().add("Запрос 4");
        comboBoxSelectQuery.getItems().add("Запрос 5");
    }

    private void makeTheNotification(Label label, String text, Color color) {
        label.setText(text);
        label.setTextFill(color);
        label.setVisible(true);
    }
}
