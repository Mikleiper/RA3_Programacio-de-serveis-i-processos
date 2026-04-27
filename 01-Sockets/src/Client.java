import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 7777;
    private final String HOST = "localhost";
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connecta() {
        try {
            socket = new Socket(HOST, PORT); // Obre el socket del servidor, tot indicat el host i el port("adreça")
            System.out.println("Connectat a servidor en " + HOST + ":" + PORT);

            out = new PrintWriter(socket.getOutputStream(), true); // Crea el flux d'escriptura per enviar dades en format array Srting i trasnforar-ho en bytes. Auto FLuhs en true servei per faciliar el flow de la data
        } catch (IOException e) {
        }
    }

     public void envia(String missatge){
        out.println(missatge);
        System.out.println("Enviat al servidor: " + missatge);
     }

     public void tanca() {
        try {
            out.close();
            socket.close();
            System.out.println("Client tancat.");
        } catch (IOException e) {
        }
     }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] missatges = {"Prova d'enviament 1", "Prova d'enviament 2", "Adéu!"};
        Client client = new Client();
        client.connecta();
        for (String m : missatges) {
            client.envia(m);
        } 

        System.out.println("Prem Enter per tancar el client...");
        sc.nextLine();
        
        client.tanca();
        sc.close();
    }
}
