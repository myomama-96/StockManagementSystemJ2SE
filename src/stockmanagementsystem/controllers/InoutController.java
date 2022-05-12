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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import stockmanagementsystem.dao.ProductDAO;
import stockmanagementsystem.dao.TransactionDAO;
import stockmanagementsystem.model.Product;
import stockmanagementsystem.model.Transaction;
import stockmanagementsystem.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class InoutController implements Initializable {

    @FXML
    private JFXTextField productIdField;
    @FXML
    private JFXTextField quantityField;
    @FXML
    private JFXTextField remarkField;
    @FXML
    private RadioButton inRadio;
    @FXML
    private ToggleGroup inoutTogglegp;
    @FXML
    private RadioButton outRadio;
    @FXML
    private Button saveTransactionBtn;
    
    private ProductDAO pdDAO;
    private TransactionDAO transDAO;
    MessageBox msg; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inRadio.setUserData("IN");
        outRadio.setUserData("OUT");
        inRadio.setSelected(true);
        
        pdDAO = new ProductDAO();
        transDAO = new TransactionDAO();
        msg = new MessageBox();
        
        // TODO
    }    

    @FXML
    private void saveTransaction(ActionEvent event) throws SQLException {
        //Get Data
        String pdidString = productIdField.getText();
        String quString = quantityField.getText();
        String remarkString = remarkField.getText();
        
        //Validate
        if(pdidString.isEmpty() || quString.isEmpty() || remarkString.isEmpty()){
            msg.showerrormessage("WARNING", "Please Fill Out of All Field.");
            return;
        }
                
        try{
            int txtproductid = Integer.parseInt(pdidString);
            int txtquantity = Integer.parseInt(quString);
            
            String type = (String)inoutTogglegp.getSelectedToggle().getUserData();
            
            
            
           if(pdDAO.isProdcutExist(txtproductid)){
               Product pd = pdDAO.getProduct(txtproductid);
               int pdStock = pd.getStock();
               if(type.equals("IN")){
                   pd.setStock(pdStock + txtquantity);
                   pdDAO.updateProduct(pd);
               }else{
                   if(txtquantity <= pdStock){
                       pd.setStock(pdStock - txtquantity);
                       pdDAO.updateProduct(pd);
                       msg.showinformationmessage("Success", "Stock data Entry is Successfully Saved.");
                   }else{
                       msg.showerrormessage("ERROR", "Out of quantity is greather than stock.");
                       return;
                   }
               }
               Transaction trans = new Transaction(type, txtproductid, txtquantity, remarkString);
               transDAO.saveTransaction(trans);
               
               clearForm();
               
           }else{
               msg.showerrormessage("ERROR", "You entered Prodcut id does not exists. Please Try Again!");
           }
        }catch(NumberFormatException e){
            msg.showerrormessage("ERROR", "Invalid Number. Please Try Again!");
        }
        
    }

    private void clearForm() {
        productIdField.clear();
        quantityField.clear();
        remarkField.clear();
        inRadio.isSelected();
    }
    
}
