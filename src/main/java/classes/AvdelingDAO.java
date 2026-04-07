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
