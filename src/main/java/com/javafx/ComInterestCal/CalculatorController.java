package com.javafx.ComInterestCal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private TableView myTable;

    @FXML
    private TextField principal;

    @FXML
    private TextField rate;

    @FXML
    private ComboBox frequency;

    @FXML
    private TextField numOfYears;

    @FXML
    private Button calculateButton;

    @FXML
    private BarChart barChart;

    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        
        TableColumn year = new TableColumn<>("Year");
        TableColumn open = new TableColumn<>("Opening Balance");
        TableColumn interest = new TableColumn<>("Interest");
        TableColumn close = new TableColumn<>("Closing Balance");

        /**
         Format column type using currency type*/
        open.setCellFactory(column -> new TableCell<Calculation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });
        interest.setCellFactory(column -> new TableCell<Calculation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });
        close.setCellFactory(column -> new TableCell<Calculation, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });

        myTable.getColumns().addAll(year, open, interest, close);

        open.prefWidthProperty().bind(myTable.widthProperty().multiply(0.3));
        close.prefWidthProperty().bind(myTable.widthProperty().multiply(0.3));

        // add all frequency needed for this ComboBox
        frequency.getItems().addAll(
                "monthly", "quarterly", "semi-annually", "annually"
        );
                
        // Define data in observable list and add data that to be showed inside table
        final ObservableList<Calculation> calculationList = FXCollections.observableArrayList();

        calculateButton.setOnAction(actionEvent -> {
            /** First, error handling */
            String prin = principal.getText();
            String interestStr = rate.getText();
            String numStr = numOfYears.getText();

            if(prin.isEmpty() || interestStr.isEmpty() || numStr.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Alert Dialog");
                alert.setContentText("Some fields are not entered! Please Check");
                alert.showAndWait();
            }
            /** error check is done*/

            /** if everything is entered, then the program runs correectly*/
            int totalYear = Integer.parseInt(numOfYears.getText());
            int frequencyNum = 0;
            double openingBalance = Integer.parseInt(principal.getText());

            double interestRate = Integer.parseInt(rate.getText())/100.00;
            double interestPerYear = 0;
            double totalPrincipal = 0;

            if (frequency.getValue() == "monthly") frequencyNum = 12;
            if (frequency.getValue() == "quarterly") frequencyNum = 4;
            if (frequency.getValue() == "semi-annually") frequencyNum = 2;
            if (frequency.getValue() == "annually") frequencyNum = 1;

            for(int i = 1; i <= totalYear; i++) {
                totalPrincipal = openingBalance * (Math.pow((1 + (interestRate/frequencyNum)),frequencyNum));
                interestPerYear = totalPrincipal - openingBalance;

                DecimalFormat df = new DecimalFormat("#.##");
                openingBalance = Double.valueOf(df.format(openingBalance));
                totalPrincipal = Double.valueOf(df.format(totalPrincipal));
                interestPerYear = Double.valueOf(df.format(interestPerYear));

                Calculation calculation = new Calculation(openingBalance, interestPerYear, i, totalPrincipal );
                calculationList.add(calculation);
                openingBalance = totalPrincipal;

                year.setCellValueFactory((new PropertyValueFactory<>("yearNum")));
                open.setCellValueFactory(new PropertyValueFactory<>("principal"));
                interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
                close.setCellValueFactory(new PropertyValueFactory<>("closingBalance"));
            }

            // add all the information to the barChart
            XYChart.Series series1 = new XYChart.Series();
            for (Calculation cal : calculationList) {
                series1.getData().add(new XYChart.Data(Integer.toString(cal.getYearNum()), cal.getClosingBalance()));
            }

            barChart.getData().addAll(series1);
            // exit the button Click
        });
        myTable.setItems(calculationList); //display data in the tableView
    }
}