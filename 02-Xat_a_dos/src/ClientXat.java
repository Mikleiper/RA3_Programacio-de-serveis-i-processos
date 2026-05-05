
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {

    private Socket socket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;

    /**
     * Obre el socket al servidor i crea els streams de sortida i entrada.
     * Important: primer es crea l'OutputStream, després l'InputStream, per
     * evitar deadlock amb el servidor.
     */
    public void connecta() {
        try {
            socket = new Socket(ServidorXat.HOST, ServidorXat.PORT);
            System.out.println("Client connectat a " + (ServidorXat.HOST + ":" + ServidorXat.PORT));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Flux d'entrada i sortida creat.");

        } catch (IOException e) {
            System.err.println("Error connectant al servidor: " + e.getMessage());
        }
    }

    /**
     * Envia un missatge al servidor a través de l'ObjectOutputStream.
     */
    public void enviarMissatge(String msg) {
        try {
            oos.writeObject(msg);
            oos.flush();
            System.out.println("Enviant missatge: " + msg);
        } catch (IOException e) {
            System.err.println("Error enviant missatge: " + e.getMessage());
        }
    }

    /**
     * Tanca els streams i el socket.
     */
    public void tancarClient() {
        try {
            System.out.println("Tancant client...");
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Client tancat.");
        } catch (IOException e) {
            System.err.println("Error tancant el client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClientXat c = new ClientXat();
        c.connecta();

        // Creem el fil lector (rep missatges del servidor)
        FilLectorCX fl = new FilLectorCX(c.ois);
        fl.start();

        // Bucle principal: llegim de la consola i enviem al servidor
        Scanner sc = new Scanner(System.in);
        String msg = "";
        while (!msg.equalsIgnoreCase("sortir")) {
            System.out.print("Missatge ('sortir' per tancar): Rebut " + msg);
            msg = sc.nextLine();
            c.enviarMissatge(msg);
        }

        // Tanquem tot
        sc.close();
        c.tancarClient();
    }
}
