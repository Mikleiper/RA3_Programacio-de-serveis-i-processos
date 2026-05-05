
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorXat {

    public final static int PORT = 9999;
    public final static String HOST = "localhost";
    private final static String MSG_SORTIR = "sortir";
    private ServerSocket serverSocket;

    public void iniciarServidor() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
        } catch (IOException e) {
            System.err.println("Error iniciant el servidor: " + e.getMessage());
        }
    }

    public void pararServidor() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error parant el servidor: " + e.getMessage());
        }
    }

    public String getNom(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("Escriu el teu nom:");
            oos.flush();
            return (String) ois.readObject();
        } catch (Exception e) {
            return "Desconegut";
        }
    }

    public static void main(String[] args) {
        ServidorXat s = new ServidorXat();
        s.iniciarServidor();
        try {
            // Acceptem la connexió del client
            Socket clientSocket = s.serverSocket.accept(); //posem al servidor en mode escolta, para l'execució fins q un client es connecta. client Socker serà una conexxió Bidireccional
            System.out.println("Client connectat: " + clientSocket.getInetAddress());

            // Creem els streams (PRIMER Output, DESPRÉS Input)
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            // Obtenim el nom del client
            String nom = s.getNom(ois, oos);
            System.out.println("Nom rebut: " + nom);

            // Creem i iniciem el fil lector (rep misatges del client)
            FilServidorXat fsx = new FilServidorXat(nom, ois);
            System.out.println("Fil de xat creat.");
            fsx.start();
            System.out.println("Fil de " + nom + " iniciat");

            // Bucle principal: llegim de la consola i enviem al client
            Scanner sc = new Scanner(System.in);
            String msg;
            do {
                System.out.print("Missatge ('" + s.MSG_SORTIR + "' per tancar): ");
                msg = sc.nextLine();
                oos.writeObject(msg);
                oos.flush();
            } while (!msg.equalsIgnoreCase(s.MSG_SORTIR));

            // Esperem que el fil lector acabi
            fsx.join();
            System.out.println("Servidor aturat.");

            // Tanquem tot
            sc.close();
            ois.close();
            oos.close();
            clientSocket.close();

        } catch (IOException e) {
            System.err.println("Error de connexió: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error esperant el fil: " + e.getMessage());
        }
        s.pararServidor();
    }
}
