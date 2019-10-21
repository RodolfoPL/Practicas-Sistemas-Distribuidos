
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
    private static Object t;
    private static Socket cordinadorSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static PrintWriter out2;
    private static BufferedReader in2;
    
    private static CoordinadorUI ui;
    // private static boolean coordinadorConectado = false;
    //private static javax.swing.JLabel estado;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Coordinador server = new Coordinador();
        ui = new CoordinadorUI();
        cordinadorSocket = null;
        ui.mostrar();
        
        try{
            serverSocket = new ServerSocket(6666);
           // System.out.print("INICIE SERVICIO");
            ui.setText("INICIE SERVICIO");
            //esperar clientes
            while(true){
                clientSocket = null;
                try{
                    //socket para recibir nuevos clientes
                    clientSocket = serverSocket.accept();
                    //Streams de entrada y salida
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //Nuevo hilo para manejar al cliente 
                    
                    Thread t;
                    //Si ya se conecto un coordinador
                    if(cordinadorSocket != null){
                        System.out.println("Nuevo Jugador conectado "+clientSocket);
                        ui.setText("Nuevo Jugador");
                        t = new ClientHandler(clientSocket, in, out, ui, cordinadorSocket, in2, out2);
                    }
                    else{
                        cordinadorSocket = clientSocket;
                        out2 = new PrintWriter(cordinadorSocket.getOutputStream(), true);
                        in2 = new BufferedReader(new InputStreamReader(cordinadorSocket.getInputStream()));
                        
                        System.out.println("Coordinador conectado "+clientSocket);
                        t = new ClientHandler(clientSocket, in, out, ui, cordinadorSocket, in2, out2);
                        //coordinadorConectado = true;
                        ui.setText("Nuevo Coordinador");
                        ui.bloquear();
                    }
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
    
    public void startConnection(String ip, int port) throws IOException {
        //coordinadorConectado = true;
        cordinadorSocket = new Socket(ip, port);
        out2 = new PrintWriter(cordinadorSocket.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(cordinadorSocket.getInputStream()));
        System.out.println("Nuevo Coordinador: "+ cordinadorSocket);
    }
}

