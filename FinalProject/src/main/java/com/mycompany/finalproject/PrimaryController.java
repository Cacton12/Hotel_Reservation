package com.mycompany.finalproject;

import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtArrival;
    @FXML
    private TextField txtDeparture;
    @FXML
    private TextField txtNights;
    @FXML
    private TextField txtTotal;
    

    @FXML
    private Button Calculate;

    @FXML
    public void Calculate(ActionEvent event) {
        final int price = 120;
        String Arrival = txtArrival.getText();
        String Departure = txtDeparture.getText();
        if(valid(Arrival,Departure)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate arrival = LocalDate.parse(Arrival, formatter);
            LocalDate departure = LocalDate.parse(Departure, formatter);

        int arrivalDay = arrival.getDayOfMonth();
        int departureDay = departure.getDayOfMonth();

        int totalDays = departureDay - arrivalDay;

        double totalPrice = price * totalDays;

        txtNights.setText(Integer.toString(totalDays));
        txtTotal.setText(Double.toString(totalPrice));
        }
    }

    public void Exit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openNewStage(ActionEvent event) throws IOException {
       
    }

    private static Scene scene;

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysLater = currentDate.plusDays(3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        txtArrival.setText(currentDate.format(formatter));
        txtDeparture.setText(threeDaysLater.format(formatter));
    }

    private boolean isValidDate(String dateStr, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void Bookit(ActionEvent event) throws IOException {
        
        HotelDB hoteldb = new HotelDB();
        Connection conn = hoteldb.GetConnection();
        ResultSet rs = hoteldb.GetAllRecords(conn);
        
        final int price = 120;
        String Arrival = txtArrival.getText();
        String Departure = txtDeparture.getText();
        if(valid(Arrival,Departure)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate arrival = LocalDate.parse(Arrival, formatter);
            LocalDate departure = LocalDate.parse(Departure, formatter);

        int arrivalDay = arrival.getDayOfMonth();
        int departureDay = departure.getDayOfMonth();

        int totalDays = departureDay - arrivalDay;

        double totalPrice = price * totalDays;
        HotelReservation reservation = new HotelReservation(arrival, departure, totalDays, totalPrice);
        HotelDB.AddReservation(reservation, conn);
        }
        
        Stage newStage = new Stage();
        Parent root = loadFXML("Secondary");
        Scene scene = new Scene(root);

        newStage.setScene(scene);
        newStage.setTitle("Reservations");
        newStage.initModality(Modality.APPLICATION_MODAL);
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        newStage.initOwner(primaryStage);

        newStage.showAndWait();
        
    }

    public boolean valid(String Arrival, String Departure) {
        final int price = 120;
        Arrival = txtArrival.getText();
        Departure = txtDeparture.getText();

        if (!Arrival.isEmpty() && !Departure.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (isValidDate(Arrival, formatter) && isValidDate(Departure, formatter)) {
                LocalDate arrival = LocalDate.parse(Arrival, formatter);
                LocalDate departure = LocalDate.parse(Departure, formatter);

                if (arrival.isBefore(departure)) {
                    int arrivalDay = arrival.getDayOfMonth();
                    int departureDay = departure.getDayOfMonth();

                    int totalDays = departureDay - arrivalDay;

                    double totalPrice = price * totalDays;

                    txtNights.setText(Integer.toString(totalDays));
                    txtTotal.setText(Double.toString(totalPrice));
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Please make youre your arrival is before your departure");
                    alert.showAndWait();
                    return false;
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Please insure your date follows the format yyyy-mm-dd");
                alert.showAndWait();
                return false;
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Both arrival date and departure date must be filled out");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
