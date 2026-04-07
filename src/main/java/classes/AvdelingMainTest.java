package classes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AvdelingMainTest {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        AvdelingDAO dao = new AvdelingDAO(emf);

        Avdeling avdeling = dao.finnAvdelingMedId(1);
        System.out.println(avdeling.getAvdelingNavn());
    }
}