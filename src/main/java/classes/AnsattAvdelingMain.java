package classes;

import java.util.Scanner;

public class AnsattAvdelingMain {

    public static void main(String[] args) {
        AnsattDAO ansattDAO = new AnsattDAO();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Legg inn ny ansatt med avdeling");
            System.out.print("Brukernavn: ");
            String brukernavn = scanner.nextLine().trim();

            System.out.print("Fornavn: ");
            String fornavn = scanner.nextLine().trim();

            System.out.print("Etternavn: ");
            String etternavn = scanner.nextLine().trim();

            System.out.print("Stilling: ");
            String stilling = scanner.nextLine().trim();

            System.out.print("Månedslønn: ");
            double manedslonn = lesDouble(scanner);

            System.out.print("Avdeling ID: ");
            long avdelingId = lesLong(scanner);

            Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, stilling, manedslonn);
            ansattDAO.leggTilAnsattMedAvdeling(nyAnsatt, avdelingId);

            System.out.println("Ny ansatt lagt til med avdeling: " + nyAnsatt.getAvdeling().getAvdelingNavn());
            System.out.println(nyAnsatt);
        } catch (Exception e) {
            System.out.println("Feil ved innlegging av ansatt: " + e.getMessage());
        } finally {
            scanner.close();
            AnsattDAO.close();
        }
    }

    private static double lesDouble(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Ugyldig tall for lønn.");
        }
    }

    private static long lesLong(Scanner scanner) {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Ugyldig tall for avdeling ID.");
        }
    }
}
