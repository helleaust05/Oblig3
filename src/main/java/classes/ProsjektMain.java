package classes;

import java.util.Scanner;

public class ProsjektMain {

    public static void main(String[] args) {
        ProsjektDAO prosjektDAO = new ProsjektDAO();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Velg handling:");
            System.out.println("1. Opprett nytt prosjekt");
            System.out.println("2. Legg til deltaker i prosjekt");
            System.out.print("Valg: ");
            int valg = Integer.parseInt(scanner.nextLine().trim());

            if (valg == 1) {
                System.out.print("Prosjekt navn: ");
                String navn = scanner.nextLine().trim();
                System.out.print("Beskrivelse: ");
                String beskrivelse = scanner.nextLine().trim();

                Prosjekt nyttProsjekt = new Prosjekt(navn, beskrivelse);
                prosjektDAO.leggTilProsjekt(nyttProsjekt);
                System.out.println("Prosjekt opprettet med ID: " + nyttProsjekt.getProsjektId());
            } else if (valg == 2) {
                System.out.print("Prosjekt ID: ");
                long prosjektId = Long.parseLong(scanner.nextLine().trim());
                System.out.print("Ansatt ID: ");
                int ansattId = Integer.parseInt(scanner.nextLine().trim());

                prosjektDAO.leggTilDeltaker(prosjektId, ansattId);
                System.out.println("Deltaker lagt til i prosjektet.");
            } else {
                System.out.println("Ugyldig valg.");
            }
        } catch (Exception e) {
            System.out.println("Feil: " + e.getMessage());
        } finally {
            scanner.close();
            ProsjektDAO.close();
        }
    }
}