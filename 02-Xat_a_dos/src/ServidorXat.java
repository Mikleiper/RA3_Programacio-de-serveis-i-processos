
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Stream;

public class ServidorXat {

    private final int PORT = 9999;
    private final String HOST = "localhost";
    private final String MSG_SORTIR = "sortir";
    private ServerSocket serverSocket;

    public void iniciarServidor() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
            clientSocket.get
        } catch (IOException e) {
        }
    }

    public void pararServidor() {
        try {
            serverSocket.close();
        } catch (IOException e) {
        }
    }

    public String getNom(ObjectInputStream ois, ObjectOutputStream oos) {
        String nom;
        try {
            oos.writeObject("Escriu el teu nom:");  //1 escribim la dada al cnal
            oos.flush(); // 2 després fem push

            nom = (String) ois.readObject();
            return nom;
        } catch (IOException | ClassNotFoundException c) {
            return c.getMessage();
        }4
    }

    public static void main(String[] args) {
        ServidorXat s = new ServidorXat();
        s.iniciarServidor();
        try {
            Socket clientSocket = s.serverSocket.accept(); //posem al servidor en mode escolta, para l'execució fins q un client es connecta. client Socker serà una conexxió Bidireccional
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); // Primer es crea l'Output
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            String nom = s.getNom(ois, oos);
            FilServidorXat fsx = new FilServidorXat(nom);
            fsx.start();
        } catch (IOException e) {
        }

    }
}
