package coordinador;
//package coordinador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Lenovo
 */
public class Coordinador{
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    
    private static CoordinadorUI ui;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Coordinador server = new Coordinador();
        ui = new CoordinadorUI();
        ui.mostrar();
        try{
            serverSocket = new ServerSocket(6666);
            System.out.print("INICIE SERVICIO");
            //esperar clientes
            while(true){
                clientSocket = null;
                try{
                    //socket para recibir nuevos clientes
                    clientSocket = serverSocket.accept();
                    System.out.println("Nuevo Jugador conectado "+clientSocket);
                    //Streams de entrada y salida
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //Nuevo hilo para manejar al cliente 
                    Thread t = new ClientHandler(clientSocket, in, out, ui);
                    t.start();
                }catch(Exception e){
                    clientSocket.close();
                }
            }
            //server.start(6666);
            
        }catch(Exception e){
            // System.out.println("");
       }
        
    }
    
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }    
    
    public void cierraCliente() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
    }
}
