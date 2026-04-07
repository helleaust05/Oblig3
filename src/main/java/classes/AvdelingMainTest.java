package classes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;

public class AvdelingMainTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        AvdelingDAO dao = new AvdelingDAO(emf);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Legg til ny avdeling");
            System.out.print("Avdeling navn: ");
            String avdelingNavn = scanner.nextLine().trim();

            System.out.print("Sjef ID (eksisterende ansatt): ");
            int sjefId = Integer.parseInt(scanner.nextLine().trim());

            dao.leggTilAvdeling(avdelingNavn, sjefId);
            System.out.println("Avdeling lagt til med sjef.");
        } catch (Exception e) {
            System.out.println("Feil: " + e.getMessage());
        } finally {
            scanner.close();
            emf.close();
        }
    }
}
