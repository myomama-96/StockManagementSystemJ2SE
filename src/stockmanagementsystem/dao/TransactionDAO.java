/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import stockmanagementsystem.database.Database;
import stockmanagementsystem.model.Product;
import stockmanagementsystem.model.Transaction;

/**
 *
 * @author Hp
 */
public class TransactionDAO {
    //CRUD
    public int saveTransaction(Transaction trans) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into smsdb.transaction (type,product_id, quantity, remark) values (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, trans.getType());
        stmt.setInt(2, trans.getProductId());
        stmt.setInt(3, trans.getQuantity());
        stmt.setString(4, trans.getRemark());
        
        int rows = stmt.executeUpdate();
        return rows;
    }
    
    public List<Transaction> getTansactions(Date startDate, Date endDate) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select transaction.id, transaction.type, products.name, transaction.quantity, transaction.remark, transaction.date from smsdb.transaction left join smsdb.products on transaction.product_id = products.id where date between ? and ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, startDate);
        stmt.setDate(2, endDate);
        ResultSet result = stmt.executeQuery();
        List<Transaction> transList = new ArrayList<>();
        
        while(result.next()){
            int id = result.getInt("id");
            String type = result.getString("type");
            String productName = result.getString("name");
            int quantity = result.getInt("quantity");
            String remark = result.getString("remark");
            Timestamp dateTime = result.getTimestamp("date");
            
            
            Transaction trans = new Transaction(id, type, productName, quantity, remark, dateTime.toString());
            transList.add(trans);         
        }
        return transList;
    }
}
