package classes;

import jakarta.persistence.*;

public class AvdelingDAO {

    private EntityManagerFactory emf;
    public AvdelingDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

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
