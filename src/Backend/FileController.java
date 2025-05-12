package Backend;

import Backend.Contacte;
import Backend.Controlador;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileController extends Controlador {
    private final File dataFolder;
    private final File contactFolder;

    public FileController(String dataPath) {
        super();
        this.dataFolder = new File(dataPath);
        this.contactFolder = new File(dataPath, "contacts");

        // Crear directorios si no existen
        if (!contactFolder.exists()) {
            contactFolder.mkdirs();
        }

        // Cargar contactos existentes al iniciar
        carregarContactes();
    }

    @Override
    public Contacte nouContacte(String nom, String cognoms, String telefon, String email) {
        Contacte nou = super.nouContacte(nom, cognoms, telefon, email);
        guardarContacte(nou);
        return nou;
    }

    @Override
    public Contacte actualitzarContacte(int id, String nom, String cognoms, String telefon, String email) {
        Contacte actualitzat = super.actualitzarContacte(id, nom, cognoms, telefon, email);
        if (actualitzat != null) {
            guardarContacte(actualitzat);
        }
        return actualitzat;
    }

    @Override
    public void esborrarContacte(int id) {
        // Eliminar archivo antes de borrar el contacto
        File fitxerContacte = new File(contactFolder, id + ".txt");
        if (fitxerContacte.exists()) {
            fitxerContacte.delete();
        }
        super.esborrarContacte(id);
    }

    private void guardarContacte(Contacte contacte) {
        try {
            File fitxer = new File(contactFolder, contacte.getId() + ".txt");
            try (PrintWriter writer = new PrintWriter(new FileWriter(fitxer))) {
                writer.println(contacte.getNom());
                writer.println(contacte.getCognoms());
                writer.println(contacte.getTelefon());
                writer.println(contacte.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error en guardar el contacte: " + e.getMessage());
        }
    }

    private void carregarContactes() {
        File[] fitxers = contactFolder.listFiles((dir, nom) -> nom.endsWith(".txt"));
        if (fitxers != null) {
            for (File fitxer : fitxers) {
                try {
                    String nomFitxer = fitxer.getName();
                    int id = Integer.parseInt(nomFitxer.substring(0, nomFitxer.lastIndexOf('.')));

                    try (BufferedReader reader = new BufferedReader(new FileReader(fitxer))) {
                        String nom = reader.readLine();
                        String cognoms = reader.readLine();
                        String telefon = reader.readLine();
                        String email = reader.readLine();

                        if (nom != null && cognoms != null && telefon != null && email != null) {
                            super.nouContacte(nom, cognoms, telefon, email);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error en carregar contacte: " + e.getMessage());
                }
            }
        }
    }
}