package classes;

import jakarta.persistence.*;

public class AvdelingDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public Avdeling finnAvdelingMedId(long avdelingId) {

        EntityManager em = emf.createEntityManager();

        try {
            Avdeling avdeling = em.find(Avdeling.class, avdelingId);
            return avdeling;
        } finally {
            em.close();
        }
    }
}
