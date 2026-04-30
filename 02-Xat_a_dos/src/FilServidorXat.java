
import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {

    private String nom;
    public ObjectInputStream oIS = new ObjectInputStream(String nom

    );

    public FilServidorXat() {
    }

    public FilServidorXat(String nom) {
        this.nom = nom;
    }

    @Override
    public void run() {

    }
}
