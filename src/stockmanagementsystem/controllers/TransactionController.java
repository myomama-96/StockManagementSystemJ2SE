/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stockmanagementsystem.dao.TransactionDAO;
import stockmanagementsystem.model.Transaction;
import stockmanagementsystem.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class TransactionController implements Initializable {
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private JFXButton submitBtn;
    
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, String> productNameColumn;
    @FXML
    private TableColumn<Transaction, Integer> quantityColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> remarkColumn;

    private TransactionDAO tranDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tranDAO = new TransactionDAO();
        initColumns();
    }    

    @FXML
    private void loadTransaction(ActionEvent event) throws SQLException {
        //Get start date and end date
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        //Validate
        if(startDate==null || endDate == null){
            MessageBox.showerrormessage("ERROR", "Select the start date and end date.");
            return;
        }
        
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate.plusDays(1));
        //get table data using start date and end date
         List<Transaction> trans = tranDAO.getTansactions(startSqlDate, endSqlDate);
         transactionTable.getItems().setAll(trans);
    }

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }

    
}
