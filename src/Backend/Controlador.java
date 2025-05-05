package Backend;

import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private List<Contacte> contactes;
    private int lastContactId;

    public Controlador() {
        this.contactes = new ArrayList<>();
        this.lastContactId = 0;
    }

    public Contacte nouContacte(String nom, String cognoms, String telefon, String email) {
        Contacte nou = new Contacte(++lastContactId, nom, cognoms, telefon, email);
        contactes.add(nou);
        return nou;
    }

    public Contacte actualitzarContacte(int id, String nom, String cognoms, String telefon, String email) {
        Contacte c = cercaContactesPerId(id);
        if (c != null) {
            if (nom != null) c.setNom(nom);
            if (cognoms != null) c.setCognoms(cognoms);
            if (telefon != null) c.setTelefon(telefon);
            if (email != null) c.setEmail(email);
        }
        return c;
    }

    public void esborrarContacte(int id) {
        contactes.removeIf(c -> c.getId() == id);
    }

    public Contacte cercaContactesPerId(int id) {
        return contactes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Contacte> cercaContactesPerNom(String nom) {
        return contactes.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nom))
                .toList();
    }

    public List<Contacte> cercaContactesPerCognoms(String cognoms) {
        return contactes.stream()
                .filter(c -> c.getCognoms().equalsIgnoreCase(cognoms))
                .toList();
    }

    public List<Contacte> cercaContactesPerTelefon(String telefon) {
        return contactes.stream()
                .filter(c -> c.getTelefon().equals(telefon))
                .toList();
    }

    public List<Contacte> cercaContactesPerEmail(String email) {
        return contactes.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
