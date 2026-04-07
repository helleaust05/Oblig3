package classes;

import jakarta.persistence.*;
import java.util.List;

public class ProsjektDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void leggTilProsjekt(Prosjekt prosjekt) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(prosjekt);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Prosjekt finnProsjektMedId(long prosjektId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prosjekt.class, prosjektId);
        } finally {
            em.close();
        }
    }

    public void leggTilDeltaker(long prosjektId, int ansattId) {
        EntityManager em = emf.createEntityManager();
        try {
            Prosjekt prosjekt = em.find(Prosjekt.class, prosjektId);
            if (prosjekt == null) {
                throw new IllegalArgumentException("Prosjekt med ID " + prosjektId + " finnes ikke.");
            }

            Ansatt ansatt = em.find(Ansatt.class, ansattId);
            if (ansatt == null) {
                throw new IllegalArgumentException("Ansatt med ID " + ansattId + " finnes ikke.");
            }

            prosjekt.leggTilDeltaker(ansatt);

            em.getTransaction().begin();
            em.merge(prosjekt);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Prosjekt> hentAlleProsjekter() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prosjekt> query = em.createQuery("SELECT p FROM Prosjekt p", Prosjekt.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}