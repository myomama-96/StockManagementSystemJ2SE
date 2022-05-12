/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import stockmanagementsystem.dao.ProductDAO;

/**
 *
 * @author Hp
 */
public class MainController implements Initializable {

    @FXML
    private Button dashboardBtn;
    @FXML
    private Button productBtn;
    @FXML
    private HBox dashboardView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button inoutbtn;
    @FXML
    private Button lowStockBtn;
    @FXML
    private Button transactionBtn;
    @FXML
    private Label productLabel;
    @FXML
    private Label lowStockLabel;
    
    private ProductDAO productDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dashboardBtn.setStyle("-fx-background-color:#00acc1");
        
        productDAO = new ProductDAO();
        
        loadDashboardData();
        
    }    

    @FXML
    private void showDashboardView(ActionEvent event) {
        dashboardBtn.setStyle("-fx-background-color:#00acc1");
        inoutbtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        productBtn.setStyle("-fx-background-color:transparent");
        
        loadDashboardData();
        borderPane.setCenter(dashboardView);
    }

    @FXML
    private void showProductsView(ActionEvent event) throws IOException {
        dashboardBtn.setStyle("-fx-background-color:transparent");
        inoutbtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        productBtn.setStyle("-fx-background-color:#00acc1");
       
        Parent root = FXMLLoader.load(getClass().getResource("/stockmanagementsystem/views/products.fxml"));
        borderPane.setCenter(root);
    }

    @FXML
    private void shwointoutview(ActionEvent event) throws IOException {
        dashboardBtn.setStyle("-fx-background-color:transparent");
        inoutbtn.setStyle("-fx-background-color:#00acc1");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        productBtn.setStyle("-fx-background-color:transparent");
        
        Parent root = FXMLLoader.load(getClass().getResource("/stockmanagementsystem/views/inout.fxml"));
        borderPane.setCenter(root);
    }

    @FXML
    private void showLowStockView(ActionEvent event) throws IOException {
        dashboardBtn.setStyle("-fx-background-color:transparent");
        inoutbtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:#00acc1");
        transactionBtn.setStyle("-fx-background-color:transparent");
        productBtn.setStyle("-fx-background-color:transparent");
        
        Parent root = FXMLLoader.load(getClass().getResource("/stockmanagementsystem/views/lowstock.fxml"));
        borderPane.setCenter(root);
    }

    @FXML
    private void showTransactionView(ActionEvent event) throws IOException {
        dashboardBtn.setStyle("-fx-background-color:transparent");
        inoutbtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:#00acc1");
        productBtn.setStyle("-fx-background-color:transparent");
        
        Parent root = FXMLLoader.load(getClass().getResource("/stockmanagementsystem/views/transaction.fxml"));
        borderPane.setCenter(root);
    }

    private void loadDashboardData() {
        try {
            int productCount = productDAO.countProduct();
            int lowStockCount = productDAO.countLowStockProduct();
            
            productLabel.setText(String.valueOf(productCount));
            lowStockLabel.setText(String.valueOf(lowStockCount));
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
