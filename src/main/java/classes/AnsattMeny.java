package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class AnsattMeny {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private static AnsattDAO dao = new AnsattDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static AvdelingDAO avdelingDAO = new AvdelingDAO(emf);

    public static void main(String[] args) {
        boolean kjorer = true;

        while (kjorer) {
            visHovedMeny();
            int valg = lesInt();

            switch (valg) {
                case 1:
                    sokEtterID();
                    break;
                case 2:
                    sokEtterBrukernavn();
                    break;
                case 3:
                    listeAlleAnsatte();
                    break;
                case 4:
                    oppdaterAnsatt();
                    break;
                case 5:
                    leggInnNyAnsatt();
                    break;
                case 6:
                    skrivUtAvdelingMedId();
                    break;
                case 7:
                    skrivUtAvdeling();
                    break;
                case 8:
                    kjorer = false;
                    System.out.println("Avslutter programmet.");
                    break;
                default:
                    System.out.println("Ugyldig valg. Prøv igjen.");
            }
            if (kjorer) {
                System.out.println("\nTrykk Enter for å fortsette...");
                scanner.nextLine();
            }
        }

        AnsattDAO.close();
        scanner.close();
    }

    private static void visHovedMeny() {
        System.out.println("\n========== ANSATT ADMINISTRASJON ==========");
        System.out.println("1. Søk etter ansatt (ID)");
        System.out.println("2. Søk etter ansatt (Brukernavn)");
        System.out.println("3. Liste alle ansatte");
        System.out.println("4. Oppdatere ansatt (stilling/lønn)");
        System.out.println("5. Legge inn ny ansatt");
        System.out.println("6. Finn avdeling med ID");
        System.out.println("7. Skriv ut avdeling");
        System.out.println("8. Avslutt");
        System.out.print("Velg alternativ (1-8): ");
    }

    private static void sokEtterID() {
        System.out.print("Skriv inn ansatt-ID: ");
        int id = lesInt();
        Ansatt ansatt = dao.finnAnsattMedID(id);

        if (ansatt != null) {
            skrivUtAnsatt(ansatt);
        } else {
            System.out.println("Ingen ansatt funnet med ID: " + id);
        }
    }

    private static void sokEtterBrukernavn() {
        System.out.print("Skriv inn brukernavn (initialer): ");
        String brukernavn = scanner.nextLine().trim();
        Ansatt ansatt = dao.finnAnsattMedBrukernavn(brukernavn);

        if (ansatt != null) {
            skrivUtAnsatt(ansatt);
        } else {
            System.out.println("Ingen ansatt funnet med brukernavn: " + brukernavn);
        }
    }

    private static void listeAlleAnsatte() {
        List<Ansatt> ansatte = dao.hentAlleAnsatte();

        if (ansatte.isEmpty()) {
            System.out.println("Ingen ansatte i systemet.");
        } else {
            System.out.println("\n========== ALLE ANSATTE ==========");
            for (Ansatt ansatt : ansatte) {
                System.out.println("---");
                skrivUtAnsatt(ansatt);
            }
        }
    }

    private static void oppdaterAnsatt() {
        System.out.print("Skriv inn ansatt-ID: ");
        int id = lesInt();
        Ansatt ansatt = dao.finnAnsattMedID(id);

        if (ansatt == null) {
            System.out.println("Ingen ansatt funnet med ID: " + id);
            return;
        }

        System.out.println("\nNåværende data:");
        skrivUtAnsatt(ansatt);

        System.out.print("\nOppgi ny stilling (eller trykk Enter for å beholde): ");
        String nyStilling = scanner.nextLine().trim();
        if (!nyStilling.isEmpty()) {
            ansatt.setStilling(nyStilling);
        }

        System.out.print("Oppgi ny lønn (eller trykk Enter for å beholde): ");
        String nyLonn = scanner.nextLine().trim();
        if (!nyLonn.isEmpty()) {
            try {
                double lonn = Double.parseDouble(nyLonn);
                ansatt.setManedslonn(lonn);
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig lønn. Beholder gammel verdi.");
            }
        }

        System.out.print("Oppgi ny ID for avdeling (eller trykk Enter for å beholde): ");
        Long avdelingid = lesLong();

        if (avdelingid != 0) {
            Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdelingid);

            if (avdeling != null) {
                ansatt.setAvdeling(avdeling);
            } else {
                System.out.println("Ugyldig AvdelingId. Beholder gammel verdi.");
            }
        }

            try {
                dao.oppdaterAnsatt(ansatt);
                System.out.println("Ansatt oppdatert successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println("Feil ved oppdatering: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Feil ved oppdatering: " + e.getMessage());
                e.printStackTrace();
            }

        }

    private static void leggInnNyAnsatt() {
        System.out.println("\n========== LEGGE INN NY ANSATT ==========");

        System.out.print("Brukernavn (initialer): ");
        String brukernavn = scanner.nextLine().trim();

        System.out.print("Fornavn: ");
        String fornavn = scanner.nextLine().trim();

        System.out.print("Etternavn: ");
        String etternavn = scanner.nextLine().trim();

        System.out.print("Stilling: ");
        String stilling = scanner.nextLine().trim();

        System.out.print("Månedslønn: ");
        double lonn = lesDouble();

        System.out.print("Avdeling ID: ");
        long avdelingId = lesLong();

        try {
            Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, stilling, lonn);
            dao.leggTilAnsatt(nyAnsatt);
            System.out.println("Ny ansatt lagt til successfully!");
        } catch (Exception e) {
            System.out.println("Feil ved innsetting: " + e.getMessage());
        }
    }

    private static void skrivUtAnsatt(Ansatt ansatt) {
        System.out.println("ID: " + ansatt.getId());
        System.out.println("Brukernavn: " + ansatt.getBrukernavn());
        System.out.println("Fornavn: " + ansatt.getFornavn());
        System.out.println("Etternavn: " + ansatt.getEtternavn());
        System.out.println("Stilling: " + ansatt.getStilling());
        System.out.println("Månedslønn: " + ansatt.getManedslonn());
        System.out.println("Avdeling: " + (ansatt.getAvdeling() == null ? "Ingen" : ansatt.getAvdeling().getAvdelingNavn()));
        System.out.println("Ansettelsesdato: " + ansatt.getAnsettelseDato());
    }

    public static void skrivUtAvdelingMedId() { //Tar inn Id nummer og skriver ut navnet
        System.out.println("Skriv Id");
        Long id = lesLong();
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);

        if (avdeling == null) {
            System.out.println("Ugyldig ID");
            return;
        }
        System.out.println(avdeling.getAvdelingNavn());
    }

    public static List alleIAvdeling(Avdeling avdeling) { //finner ansatte i avdeling
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createQuery("SELECT a FROM Ansatt a WHERE a.avdeling = :avdeling", Ansatt.class);
            query.setParameter("avdeling", avdeling);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void skrivUtAvdeling() {
        System.out.println("Skriv Id");
        Long avdelingid = lesLong();
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdelingid);

        if (avdeling == null) {
            System.out.println("Ugyldig ID");
            return;
        }
        System.out.println("--" + avdeling.getAvdelingNavn() + "--");

        List<Ansatt> ansatte = alleIAvdeling(avdeling);

        for (Ansatt a : ansatte) {
            if (a.getId().equals(avdeling.getSjef().getId())){
                System.out.println(("Sjef: ") + a.getFornavn() + " " + a.getEtternavn());
            } else {
                System.out.println("Fornavn: " + a.getFornavn());
            }
        }
    }

    private static int lesInt() {
        try {
            int verdi = scanner.nextInt();
            scanner.nextLine();
            return verdi;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static double lesDouble() {
        try {
            double verdi = scanner.nextDouble();
            scanner.nextLine();
            return verdi;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    private static long lesLong() {
        try {
            long verdi = scanner.nextLong();
            scanner.nextLine();
            return verdi;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}
