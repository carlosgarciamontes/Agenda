package Frontend;

import Backend.Contacte;
import Backend.Controlador;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.List;

public class TUI {
    private Scanner sc;
    private Controlador controlador;

    public TUI(Controlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void inici() {
        int opcio;
        do {
            opcio = mostrarMenuPrincipal();
            switch(opcio) {
                case 1: crearContacte(); break;
                case 2: cercarContacte(); break;
                case 3: actualitzarContacte(); break;
                case 4: esborrarContacte(); break;
                case 5: System.out.println("Sortint..."); break;
                default: System.out.println("Opció no vàlida");
            }
        } while(opcio != 5);
        sc.close();
    }

    private int mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear contacte");
        System.out.println("2. Cercar contacte");
        System.out.println("3. Actualitzar contacte");
        System.out.println("4. Esborrar contacte");
        System.out.println("5. Sortir");
        System.out.print("Selecciona una opció: ");
        return llegirEnter();
    }

    private int mostrarMenuCerca() {
        System.out.println("\n=== MENÚ CERCAR ===");
        System.out.println("1. Cercar per ID");
        System.out.println("2. Cercar per nom");
        System.out.println("3. Cercar per cognoms");
        System.out.println("4. Cercar per telèfon");
        System.out.println("5. Cercar per email");
        System.out.println("6. Mostra tots els contactes");
        System.out.println("7. Tornar al menú principal");
        System.out.print("Selecciona una opció: ");
        while (!sc.hasNextInt()) {
            System.out.println("Entrada no vàlida. Introdueixi un número.");
            sc.next();
        }
        return llegirEnter();

    }

    private void crearContacte() {
        System.out.println("\n--- CREAR CONTACTE ---");
        String[] dades = demandaDadesContacte();
        Contacte nou = controlador.nouContacte(dades[0], dades[1], dades[2], dades[3]);
        System.out.println("\nContacte creat:\n" + nou);
    }

    private void cercarContacte() {
        int opcio;

        do {
            opcio = mostrarMenuCerca();
            switch(opcio) {
                case 1: cercarPerId(); break;
                case 2: cercarPerNom(); break;
                case 3: cercarPerCognoms(); break;
                case 4: cercarPerTelefon(); break;
                case 5: cercarPerEmail(); break;
                case 6: mostrarTotsElsContactes(); break;
                case 7: break;
                default: System.out.println("Opció no vàlida");
            }
        } while(opcio != 7);
    }
    private void mostrarTotsElsContactes() {
        List<Contacte> totsElsContactes = controlador.getTotsElsContactes();
        mostrarLlistaContactes(totsElsContactes);
    }

    private void cercarPerId() {
        System.out.print("Introdueix ID: ");
        Contacte c = controlador.cercaContactesPerId(llegirEnter());
        mostrarContacte(c);
    }

    private void cercarPerNom() {
        System.out.print("Introdueix nom: ");
        List<Contacte> resultats = controlador.cercaContactesPerNom(llegirString());
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerCognoms() {
        System.out.print("Introdueix cognoms: ");
        List<Contacte> resultats = controlador.cercaContactesPerCognoms(llegirString());
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerTelefon() {
        System.out.print("Introdueix telèfon: ");
        List<Contacte> resultats = controlador.cercaContactesPerTelefon(llegirString());
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerEmail() {
        System.out.print("Introdueix email: ");
        List<Contacte> resultats = controlador.cercaContactesPerEmail(llegirString());
        mostrarLlistaContactes(resultats);
    }

    private void actualitzarContacte() {
        System.out.println("\n--- ACTUALITZAR CONTACTE ---");
        System.out.print("Introdueix ID del contacte a actualitzar: ");
        int id = llegirEnter();

        Contacte actual = controlador.cercaContactesPerId(id);
        if (actual == null) {
            System.out.println("No s'ha trobat cap contacte amb aquest ID");
            return;
        }

        System.out.println("\nDades actuals:");
        System.out.println(actual);

        String[] novesDades = demandaDadesActualitzarContacte();
        Contacte actualitzat = controlador.actualitzarContacte(
                id,
                novesDades[0],
                novesDades[1],
                novesDades[2],
                novesDades[3]
        );

        System.out.println("\nContacte actualitzat:");
        System.out.println(actualitzat);
    }

    private void esborrarContacte() {
        System.out.println("\n--- ESBORRAR CONTACTE ---");
        System.out.print("Introdueix ID del contacte a esborrar: ");
        int id = llegirEnter();
        Contacte actual = controlador.cercaContactesPerId(id);
        if (actual == null) {
            System.out.println("No s'ha trobat cap contacte amb aquest ID");
        }
        else {
            controlador.esborrarContacte(id);
            System.out.println("Contacte esborrat amb èxit");
        }
    }


    private String[] demandaDadesContacte() {
        System.out.print("Nom: ");
        String nom = llegirString();
        System.out.print("Cognoms: ");
        String cognoms = llegirString();
        System.out.print("Telèfon: ");
        String telefon = llegirString();
        System.out.print("Email: ");
        String email = llegirString();
        return new String[]{nom, cognoms, telefon, email};
    }

    private String[] demandaDadesActualitzarContacte() {
        System.out.println("Introdueix les noves dades (deixa en blanc per mantenir):");
        System.out.print("Nom: ");
        String nom = llegirStringOpcio();
        System.out.print("Cognoms: ");
        String cognoms = llegirStringOpcio();
        System.out.print("Telèfon: ");
        String telefon = llegirStringOpcio();
        System.out.print("Email: ");
        String email = llegirStringOpcio();
        return new String[]{
                nom.isEmpty() ? null : nom,
                cognoms.isEmpty() ? null : cognoms,
                telefon.isEmpty() ? null : telefon,
                email.isEmpty() ? null : email
        };
    }

    private void mostrarContacte(Contacte c) {
        if (c == null) {
            System.out.println("No s'ha trobat cap contacte");
        } else {
            System.out.println("\nContacte trobat:");
            System.out.println(c);
        }
    }

    private void mostrarLlistaContactes(List<Contacte> contactes) {
        if (contactes.isEmpty()) {
            System.out.println("No s'han trobat contactes");
        } else {
            System.out.println("\nContactes trobats:");
            contactes.forEach(c -> System.out.println(c + "-----"));
        }
    }

    private int llegirEnter() {
        while (!sc.hasNextInt()) {
            System.out.println("Has d'introduir un número enter");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine(); // Consumir el salt de línea
        return num;
    }

    private String llegirString() {
        return sc.nextLine().trim();
    }

    private String llegirStringOpcio() {
        String input = sc.nextLine().trim();
        return input.isEmpty() ? "" : input;
    }
}