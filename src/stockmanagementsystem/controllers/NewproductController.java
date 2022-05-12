/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import stockmanagementsystem.dao.ProductDAO;
import stockmanagementsystem.model.Product;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class NewproductController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField priceField;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    private ProductDAO productDAO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        productDAO = new ProductDAO();
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
        System.out.println("Close Window");
        Stage stage = (Stage)saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveNewProduct(ActionEvent event){
        //Getting Data
        String name = nameField.getText();
        String idStr = idField.getText();
        String priceStr = priceField.getText();
        
        //Validate
        if(name.isEmpty() || idStr.isEmpty() || priceStr.isEmpty()){
//            System.out.println("Please Fill Out of All Session.");
              Alert alert = new Alert(AlertType.WARNING);
              alert.setContentText("Please Fill Out of All Session.");
              alert.setHeaderText("");
              alert.show();
            return;
        }
        try{
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            
            Product product = new Product(id, name, price, 0);
            try {
                    productDAO.save(product);
                    fieldClear();
              
                    Stage stage = (Stage)saveBtn.getScene().getWindow();
                    stage.close();
            } catch (SQLException ex) {
//                System.out.println("Duplicate Id Error.");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Duplicate ID. Please Change Another ID.");
                alert.setHeaderText("");
                alert.show();
                Logger.getLogger(NewproductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(NumberFormatException nu){
//            System.out.println("Invalid Number.");
              Alert alert = new Alert(AlertType.ERROR);
              alert.setContentText("Invalid Number. Try Again.");
              alert.setHeaderText("");
              alert.show();
        }
        
        //Saving
        
    }

    private void fieldClear() {
        nameField.clear();
        idField.clear();
        priceField.clear();
    }
    
     
    
}
