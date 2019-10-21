/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class Conexion {
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "root";
    private static final String url = "jdbc:mysql://localhost:3306/distribuidos";
    
    public Conexion(){
        conn = null;
        try{
            Class.forName(driver);
        }catch(Exception e){
            System.out.println("Error de driver: " + e);
        }
        try{
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null){
                System.out.println("Conexion con DB establecida");
            }
        }catch(SQLException e){
            System.out.println("Error al conectar: " + e);
        }
    }
    
    //Regresar la conexion 
    public Connection getConnection(){
        return conn;
    }
    
    public void desconecta(){
        conn = null;
        System.out.println("Conexion finalizada");
    }
}