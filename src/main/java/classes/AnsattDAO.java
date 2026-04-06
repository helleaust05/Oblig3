package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class AnsattDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public Ansatt finnAnsattMedID(int ansattId) {
        EntityManager em = emf.createEntityManager();
        try {
            Ansatt ansatt = em.find(Ansatt.class, ansattId);
            return ansatt;
        } finally {
            em.close();
        }
    }

    public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn");
            query.setParameter("brukernavn", brukernavn);
            List<Ansatt> resultater = query.getResultList();
            return resultater.isEmpty() ? null : resultater.get(0);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Ansatt> hentAlleAnsatte() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Ansatt a");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void leggTilAnsatt(Ansatt ansatt) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ansatt);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void oppdaterAnsatt(Ansatt ansatt) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(ansatt);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
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
