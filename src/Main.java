import Backend.Contacte;
import Backend.Controlador;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    private InputStream inputStream;
    private Scanner scanner;
    private Controlador controlador;

    public Main(InputStream inputStream) {
        this.inputStream = inputStream;
        this.scanner = new Scanner(inputStream);
        this.controlador = new Controlador();
    }

    public static void main(String[] args) {
        Main aplicacio = new Main(System.in);
        aplicacio.iniciarAplicacio();
    }

    public void iniciarAplicacio() {
        int opcioPrincipal;
        do {
            opcioPrincipal = mostrarMenuPrincipal();

            switch(opcioPrincipal) {
                case 1:
                    crearContacte();
                    break;
                case 2:
                    cercarContacte();
                    break;
                case 3:
                    actualitzarContacte();
                    break;
                case 4:
                    esborrarContacte();
                    break;
                case 5:
                    System.out.println("Sortint de l'aplicació...");
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, seleccioni una opció del 1 al 5.");
            }
        } while(opcioPrincipal != 5);

        scanner.close();
    }

    private int mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear contacte");
        System.out.println("2. Cercar contacte");
        System.out.println("3. Actualitzar contacte");
        System.out.println("4. Esborrar contacte");
        System.out.println("5. Sortir");
        System.out.print("Seleccioni una opció: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no vàlida. Introdueixi un número.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private int mostrarMenuCerca() {
        System.out.println("\n=== MENÚ CERCAR ===");
        System.out.println("1. Cercar per ID");
        System.out.println("2. Cercar per nom");
        System.out.println("3. Cercar per cognoms");
        System.out.println("4. Cercar per telèfon");
        System.out.println("5. Cercar per email");
        System.out.println("6. Tornar al menú principal");
        System.out.print("Seleccioni una opció: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no vàlida. Introdueixi un número.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void crearContacte() {
        scanner.nextLine(); // Netejar buffer
        String[] dades = demandaDadesContacte();
        Contacte nouContacte = controlador.nouContacte(dades[0], dades[1], dades[2], dades[3]);
        System.out.println("\nContacte creat amb èxit:");
        System.out.println(nouContacte);
    }

    private void cercarContacte() {
        int opcioCerca;
        do {
            opcioCerca = mostrarMenuCerca();
            scanner.nextLine(); // Netejar buffer

            switch(opcioCerca) {
                case 1:
                    cercarPerId();
                    break;
                case 2:
                    cercarPerNom();
                    break;
                case 3:
                    cercarPerCognoms();
                    break;
                case 4:
                    cercarPerTelefon();
                    break;
                case 5:
                    cercarPerEmail();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, seleccioni una opció del 1 al 6.");
            }
        } while(opcioCerca != 6);
    }

    private void cercarPerId() {
        System.out.print("Introdueixi l'ID del contacte: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Netejar buffer
        Contacte contacte = controlador.cercaContactesPerId(id);
        mostrarContacte(contacte);
    }

    private void cercarPerNom() {
        System.out.print("Introdueixi el nom: ");
        String nom = scanner.nextLine();
        List<Contacte> resultats = controlador.cercaContactesPerNom(nom);
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerCognoms() {
        System.out.print("Introdueixi els cognoms: ");
        String cognoms = scanner.nextLine();
        List<Contacte> resultats = controlador.cercaContactesPerCognoms(cognoms);
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerTelefon() {
        System.out.print("Introdueixi el telèfon: ");
        String telefon = scanner.nextLine();
        List<Contacte> resultats = controlador.cercaContactesPerTelefon(telefon);
        mostrarLlistaContactes(resultats);
    }

    private void cercarPerEmail() {
        System.out.print("Introdueixi l'email: ");
        String email = scanner.nextLine();
        List<Contacte> resultats = controlador.cercaContactesPerEmail(email);
        mostrarLlistaContactes(resultats);
    }

    private void actualitzarContacte() {
        System.out.print("Introdueixi l'ID del contacte a actualitzar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Netejar buffer

        Contacte contacte = controlador.cercaContactesPerId(id);
        if (contacte == null) {
            System.out.println("No s'ha trobat cap contacte amb aquest ID.");
            return;
        }

        System.out.println("\nDades actuals del contacte:");
        System.out.println(contacte);

        String[] novesDades = demandaDadesActualitzarContacte();
        Contacte actualitzat = controlador.actualitzarContacte(
                id,
                novesDades[0],
                novesDades[1],
                novesDades[2],
                novesDades[3]
        );

        System.out.println("\nContacte actualitzat amb èxit:");
        System.out.println(actualitzat);
    }

    private void esborrarContacte() {
        System.out.print("Introdueixi l'ID del contacte a esborrar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Netejar buffer
        controlador.esborrarContacte(id);
        System.out.println("Contacte esborrat amb èxit (si existia).");
    }

    private String[] demandaDadesContacte() {
        System.out.println("\nIntrodueixi les dades del nou contacte:");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Cognoms: ");
        String cognoms = scanner.nextLine();
        System.out.print("Telèfon: ");
        String telefon = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new String[]{nom, cognoms, telefon, email};
    }

    private String[] demandaDadesActualitzarContacte() {
        System.out.println("\nIntrodueixi les noves dades (deixi en blanc per mantenir el valor actual):");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Cognoms: ");
        String cognoms = scanner.nextLine();
        System.out.print("Telèfon: ");
        String telefon = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new String[]{nom, cognoms, telefon, email};
    }

    private void mostrarContacte(Contacte contacte) {
        if (contacte == null) {
            System.out.println("No s'ha trobat cap contacte.");
        } else {
            System.out.println("\nContacte trobat:");
            System.out.println(contacte);
        }
    }

    private void mostrarLlistaContactes(List<Contacte> contactes) {
        if (contactes.isEmpty()) {
            System.out.println("No s'han trobat contactes.");
        } else {
            System.out.println("\nContactes trobats:");
            for (Contacte c : contactes) {
                System.out.println(c);
                System.out.println("-------------------");
            }
        }
    }
}