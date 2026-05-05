
import java.io.IOException;
import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {

    private String nom;
    private ObjectInputStream oIS;

    public FilServidorXat(String nom, ObjectInputStream oIS) {
        this.nom = nom;
        this.oIS = oIS;
    }

    @Override
    public void run() {
        try {
            // readObject() bloqueja fins que el client envia un missatge
            String msg = (String) oIS.readObject();

            while (!msg.equalsIgnoreCase("sortir")) {
                System.out.println("Rebut: " + msg);
                msg = (String) oIS.readObject();
            }

            System.out.println("Fil de xat finalitzat.");

        } catch (IOException e) {
            System.err.println("Error de connexió al fil de " + nom + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de tipus al fil de " + nom + ": " + e.getMessage());
        }

    }

}
