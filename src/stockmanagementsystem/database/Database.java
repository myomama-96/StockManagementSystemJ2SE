/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmanagementsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hp
 */
public class Database {
    
    private static Database db;
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "";
    
    private Database() throws SQLException{
        connect();
    }
    
    public static Database getInstance() throws SQLException{
        if(db == null){
            db = new Database();
        }
        return db;
    }
    
    private void connect() throws SQLException{
        conn = DriverManager.getConnection(url, user, password);
      
    }
    
    public void createDatabase() throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = "create database if not exists smsdb";
        stmt.execute(sql);
    }
    
    public void createTable() throws SQLException{
        Statement stmt = conn.createStatement();
        String sql = "create table if not exists smsdb.products(id int primary key, name varchar(80), price double, stock int(11))";
        stmt.execute(sql);
        
        Statement stmt1 = conn.createStatement();
        String sql1 = "create table if not exists smsdb.transaction(id int primary key auto_increment, type varchar(10), product_id int,quantity int, remark varchar(255), date timestamp, foreign key (product_id) references products(id))";
        stmt1.execute(sql1);
    }
    
    public Connection getConnection(){
        return conn;
    }
}
