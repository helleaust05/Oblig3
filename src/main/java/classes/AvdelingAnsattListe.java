package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class AvdelingAnsattListe {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Oppgi avdeling ID: ");
            long avdelingId = parseLong(scanner.nextLine().trim());
            listAnsatteForAvdeling(avdelingId);
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig input: " + e.getMessage());
        }
    }

    private static void listAnsatteForAvdeling(long avdelingId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            Avdeling avdeling = em.find(Avdeling.class, avdelingId);
            if (avdeling == null) {
                System.out.println("Fant ingen avdeling med ID " + avdelingId);
                return;
            }

            Ansatt sjef = avdeling.getSjef();
            String sjefNavn = sjef == null ? "Ingen sjef registrert" : sjef.getFornavn() + " " + sjef.getEtternavn();

            System.out.println("\nAvdeling: " + avdeling.getAvdelingNavn() + " (ID " + avdeling.getAvdelingId() + ")");
            System.out.println("Sjef/leder: " + sjefNavn);
            System.out.println("Ansatte:");

            TypedQuery<Ansatt> query = em.createQuery(
                    "SELECT a FROM Ansatt a WHERE a.avdeling.avdelingid = :avdelingId ORDER BY a.etternavn, a.fornavn",
                    Ansatt.class);
            query.setParameter("avdelingId", avdelingId);
            List<Ansatt> ansatte = query.getResultList();

            if (ansatte.isEmpty()) {
                System.out.println("  Ingen ansatte funnet for denne avdelingen.");
                return;
            }

            Integer sjefId = sjef != null ? sjef.getId() : null;
            for (Ansatt ansatt : ansatte) {
                boolean isSjef = sjefId != null && ansatt.getId().equals(sjefId);
                String prefix = isSjef ? "  * [SJEF/LEDER] " : "  - ";
                System.out.println(prefix + ansatt.getFornavn() + " " + ansatt.getEtternavn()
                        + " (" + ansatt.getStilling() + ")");
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    private static long parseLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Avdeling ID må være et tall.");
        }
    }
}
