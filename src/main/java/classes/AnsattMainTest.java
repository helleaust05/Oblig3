package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AnsattMainTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            Ansatt ansatt = em.find(Ansatt.class, 1);

            if (ansatt != null) {
                System.out.println("Fant ansatt:");
                System.out.println("ID: " + ansatt.getId());
                System.out.println("Brukernavn: " + ansatt.getBrukernavn());
                System.out.println("Fornavn: " + ansatt.getFornavn());
                System.out.println("Etternavn: " + ansatt.getEtternavn());
                System.out.println("Stilling: " + ansatt.getStilling());
                System.out.println("Månedslønn: " + ansatt.getManedslonn());
                System.out.println("Ansettelsesdato: " + ansatt.getAnsettelseDato());
            } else {
                System.out.println("Ingen ansatt funnet med ID 1.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
            emf.close();
        }
    }
}
