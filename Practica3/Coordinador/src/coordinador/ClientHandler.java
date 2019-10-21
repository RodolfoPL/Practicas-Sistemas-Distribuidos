/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ClientHandler extends Thread{
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    
    private float suma;
    private int linea_leida;
    private final int no_lineas = 100;
    private Conexion con;
    
    private final CoordinadorUI ui;
    
  
    ClientHandler(Socket clientSocket, BufferedReader in, PrintWriter out, CoordinadorUI ui) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.ui = ui;
        this.suma = (float) 0.0;
    }
    
    @Override
    public void run(){
        linea_leida = 0;
        suma = 0;
        try {
            SUMAR();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SUMAR()  throws IOException, SQLException{
        
        
        String greeting; 
        while((greeting = in.readLine()) != null){
          
           
            //Si se leyo todo el archivo
            int res = calcula(greeting);
            System.out.println(res);
            if(res == no_lineas){
                System.out.println("Calculos realizados el resultado de la suma es: " + suma);
                ui.getTextPane().setText("Calculos realizados el resultado de la suma es: " + suma);
                out.println("Calculos realizados");
                linea_leida = 0;
                
                InetAddress IP = clientSocket.getInetAddress();
                String hora = ui.getReloj().toString();
               
                con = new Conexion();
                /*Guardar en base
                *Resultado
                *IP jugador
                *Hora                
                */
                try{
                    Connection conn = con.getConnection();

                    String query = "insert into jugador (resultado, ip, hora)\n" +
                                    " values(\""+suma+"\", \""+IP+"\", \""+hora+"\");";

                    boolean rs;
                    // Ejecutar el query
                    // crear el java statement
                    try (Statement st = conn.createStatement()) {
                        rs = st.execute(query);
                    }
                    System.out.println("BD CAMBIADA: "+rs);
                    ui.getTextPane().setText("Base de Datos actualizada con datos del jugador");
                }
                catch(Exception e){
                    System.out.println("Error al guardar Query "+e);
                    //ui.getTextPane().setText("Error al ejecutar Query: " + e);
                }
      
                //System.out.println(query);
                con.desconecta();
                suma = 0;
            }
            else{
                System.out.println("Error en el archivo");
                ui.getTextPane().setText("Archivo con errores enviando respuesta al jugador: ");
                out.println("error");
            }
        }
        //System.out.println("Deje de sumar");
    }
    
    private int calcula(String archivo){
        String[] numero_str = archivo.split("-");
       // System.out.println(numero_str.length);
        for(String a : numero_str){
            if("".equals(a))
                return 0;
            else{
                linea_leida++;
                try{
                    float sumando = Float.parseFloat(a);
                    suma += sumando;
                }
                catch(Exception e){
                    System.out.println("Hay algo que no es un numero");
                    ui.getTextPane().setText("Hay algo que no es un número");
                }
                System.out.println("line: "+ a); 
            }
        }
        //System.out.println(suma);
        return linea_leida;
    }
}
