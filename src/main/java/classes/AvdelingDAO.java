package classes;

import jakarta.persistence.*;

public class AvdelingDAO {

    private EntityManagerFactory emf;

    public AvdelingDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Avdeling finnAvdelingMedId(long avdelingid) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Avdeling.class, avdelingid);
        } finally {
            em.close();
        }
    }
}
