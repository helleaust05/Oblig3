package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
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
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn", Ansatt.class);
            query.setParameter("brukernavn", brukernavn);
            List<Ansatt> resultater = query.getResultList();
            return resultater.isEmpty() ? null : resultater.get(0);
        } finally {
            em.close();
        }
    }

    public List<Ansatt> hentAlleAnsatte() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a", Ansatt.class);
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

    public void leggTilAnsattMedAvdeling(Ansatt ansatt, long avdelingId) {
        EntityManager em = emf.createEntityManager();
        try {
            Avdeling avdeling = em.find(Avdeling.class, avdelingId);
            if (avdeling == null) {
                throw new IllegalArgumentException("Avdeling med ID " + avdelingId + " finnes ikke.");
            }
            avdeling.setAvdelingId(avdeling.getAvdelingId());

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

            Ansatt eksisterende = em.find(Ansatt.class, ansatt.getId());
            if (eksisterende == null) {
                throw new IllegalArgumentException("Ansatt med ID " + ansatt.getId() + " finnes ikke.");
            }

            TypedQuery<Avdeling> sjefQuery = em.createQuery(
                    "SELECT a FROM Avdeling a WHERE a.sjef.ansattId = :ansattId", Avdeling.class);
            sjefQuery.setParameter("ansattId", ansatt.getId());
            List<Avdeling> sjefAvdelinger = sjefQuery.getResultList();

            if (!sjefAvdelinger.isEmpty()) {
                Long gjeldendeAvdelingId = eksisterende.getAvdeling().getAvdelingId() == null ? null : eksisterende.getAvdeling().getAvdelingId();
                Long nyAvdelingId = ansatt.getAvdeling().getAvdelingId() == null ? null : ansatt.getAvdeling().getAvdelingId();

                if (!java.util.Objects.equals(gjeldendeAvdelingId, nyAvdelingId)) {
                    throw new IllegalArgumentException("Ansatt som er sjef kan ikke bytte avdeling.");
                }
            }

            eksisterende.setStilling(ansatt.getStilling());
            eksisterende.setManedslonn(ansatt.getManedslonn());
            eksisterende.setAvdeling(ansatt.getAvdeling());

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
