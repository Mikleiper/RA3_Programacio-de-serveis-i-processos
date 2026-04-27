import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket; 

public class Servidor {

    private final int PORT = 7777;
    private final String HOST = "localhost";
    private Socket clientSocket;
    private ServerSocket srvSocket;

    public void connecta(){
        try {
            srvSocket = new ServerSocket(PORT);
            System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
            System.out.println("Esperant connexions a " + HOST + ":" + PORT);

            clientSocket = srvSocket.accept();                
            System.out.println("Client connectat: " + clientSocket.getInetAddress());

        } catch (IOException ex) {
        }
    }

    public void repDades(){
        try{
            InputStream is = clientSocket.getInputStream();  //obtenim el flux d'entrada d bytes dl socket
            InputStreamReader inputStRe = new InputStreamReader(is); //convertim en el flux d bytes a flux d caracters
            BufferedReader br = new BufferedReader(inputStRe); //llegim el flux de caracters
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println("Rebut: " + line);
                if (line.equals("Adéu!")) {
                    break; // Rompe el bucle de lectura inmediatamente
                }
            }            
            br.close();            

        } catch (IOException ex) {
        }    
    }

    public void tanca(){
        try {
            clientSocket.close();
            srvSocket.close();
            System.out.println("Servidor tancat.");
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.connecta();
        s.repDades();
        s.tanca();
    }   

}