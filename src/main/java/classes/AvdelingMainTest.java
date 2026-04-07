package classes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;

public class AvdelingMainTest {

    public static void main(String[] args) {

        private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        AvdelingDAO dao = new AvdelingDAO();

        Avdeling avdelingTest = AvdelingDAO.finnAvdelingMedId(1);
    }
}