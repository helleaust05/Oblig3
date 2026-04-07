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

    public void leggTilAvdeling(String avdelingNavn, int sjefId) {
        EntityManager em = emf.createEntityManager();
        try {
            Ansatt sjef = em.find(Ansatt.class, sjefId);
            if (sjef == null) {
                throw new IllegalArgumentException("Ansatt med ID " + sjefId + " finnes ikke.");
            }

            Avdeling nyAvdeling = new Avdeling(avdelingNavn, sjef);

            em.getTransaction().begin();
            em.persist(nyAvdeling);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}
