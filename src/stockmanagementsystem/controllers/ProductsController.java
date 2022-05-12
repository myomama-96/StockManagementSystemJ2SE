/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import stockmanagementsystem.dao.ProductDAO;
import stockmanagementsystem.model.Product;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class ProductsController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    
    private ProductDAO productDAO;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private JFXTextField searchnameField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        productDAO = new ProductDAO();
        
        productTable.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        
        initColumns();
        loadTableData();
        
    }    

    @FXML
    private void loadProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stockmanagementsystem/views/newproduct.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initOwner(addBtn.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        loadTableData();
    }

    @FXML
    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
     
    @FXML
    private void loadTableData(){
        try {
            List<Product> product = productDAO.getProducts();
            productTable.getItems().setAll(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        
        //Get Selected Item
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        
        //Validation
        if(selectedProduct == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please Select You Want to delete Prodcut.");
            alert.setHeaderText("");
            alert.show();
            return;
        }
        
        Alert alert  = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setContentText("Are you sure want to delete this item.");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK){
            try {
            productDAO.deleteProduct(selectedProduct.getId());
            
            loadTableData();
//            productTable.getItems().remove(selectedProduct);
                } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    @FXML
    private void updateProductName(TableColumn.CellEditEvent<Product, String> event) {
        System.out.println("Update Name");
        System.out.println("Old value: "+event.getOldValue());
        System.out.println("New Value: "+event.getNewValue());
        Product product = event.getRowValue();
        product.setName(event.getNewValue());
        try {
            productDAO.updateProduct(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void updateProductPrice(TableColumn.CellEditEvent<Product, Double> event) {
        System.out.println("Update Price");
        System.out.println("old value: "+ event.getOldValue());
        System.out.println("new value: "+ event.getNewValue());
        Product product = event.getRowValue();
        product.setPrice(event.getNewValue());
        try {
            productDAO.updateProduct(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchProductByName(ActionEvent event) {
        String searchName = searchnameField.getText();
        
        try {    
            List<Product> pd = productDAO.getProdcutByName(searchName);
            productTable.getItems().setAll(pd);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
