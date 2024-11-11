package com.mycompany.finalproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SecondaryController implements Initializable{

    @FXML
    private ListView<HotelReservation> lstOutput;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = HotelDB.GetConnection();
        System.out.println(conn);
        try {
            List<HotelReservation> reservations = new ArrayList<>();
            reservations = HotelDB.GetReservations(HotelDB.GetAllRecords(conn));
                lstOutput.getItems().addAll(reservations);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            HotelDB.Disconnect(conn);
        }
    }
        @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}