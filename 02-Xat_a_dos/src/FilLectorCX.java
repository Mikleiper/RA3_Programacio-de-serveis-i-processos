
import java.io.IOException;
import java.io.ObjectInputStream;

public class FilLectorCX extends Thread {

    private ObjectInputStream ois;

    /**
     * Constructor que rep l'stream d'entrada. L'ObjectInputStream es crea al
     * connecta() de ClientXat a partir del socket.getInputStream().
     */
    public FilLectorCX(ObjectInputStream ois) {
        this.ois = ois;
    }

    /**
     * Mètode d'execució del fil. Llegeix missatges del servidor i els mostra
     * per consola. Quan el servidor tanca la connexió, es llança una
     * IOException i el fil acaba.
     */
    @Override
    public void run() {
        try {
            String msg = (String) ois.readObject();

            while (!msg.equalsIgnoreCase("sortir")) {
                System.out.println("Rebut: " + msg);
                msg = (String) ois.readObject();
            }

            System.out.println("El servidor ha tancat la connexió.");

        } catch (IOException e) {
            System.err.println("Connexió tancada: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de tipus: " + e.getMessage());
        }
    }
}
