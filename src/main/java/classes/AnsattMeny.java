package classes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import static classes.AnsattDAO.*;

public class AnsattMeny {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private static AnsattDAO dao = new AnsattDAO();
    private static Scanner scanner = new Scanner(System.in);

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
        System.out.println("6. Avslutt");
        System.out.print("Velg alternativ (1-6): ");
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
        scanner.nextLine(); // consume newline
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

        try {
            dao.oppdaterAnsatt(ansatt);
            System.out.println("Ansatt oppdatert successfully!");
        } catch (Exception e) {
            System.out.println("Feil ved oppdatering: " + e.getMessage());
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

        System.out.print("AvdelingID: ");
        long avdelingid = lesLong();

        AvdelingDAO avdelingDAO = new AvdelingDAO(emf);
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdelingid);

        if (avdeling == null) {
            System.out.println("Ugyldig ID.");
        } else {
            try {
                Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, stilling, lonn);
                nyAnsatt.setAvdeling(avdeling);
                dao.leggTilAnsatt(nyAnsatt);
                System.out.println("Ny ansatt lagt til successfully!");
            } catch (Exception e) {
                System.out.println("Feil ved innsetting: " + e.getMessage());
            }
        }
    }

    private static void skrivUtAnsatt(Ansatt ansatt) {
        System.out.println("ID: " + ansatt.getId());
        System.out.println("Brukernavn: " + ansatt.getBrukernavn());
        System.out.println("Fornavn: " + ansatt.getFornavn());
        System.out.println("Etternavn: " + ansatt.getEtternavn());
        System.out.println("Stilling: " + ansatt.getStilling());
        System.out.println("Månedslønn: " + ansatt.getManedslonn());
        System.out.println("Ansettelsesdato: " + ansatt.getAnsettelseDato());
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
