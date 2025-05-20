package Backend;

import java.util.List;

public interface Controlador {
    Contacte nouContacte(String nom, String cognoms, String telefon, String email);
    Contacte actualitzarContacte(int id, String nom, String cognoms, String telefon, String email);
    void esborrarContacte(int id);
    Contacte cercaContactesPerId(int id);
    List<Contacte> cercaContactesPerNom(String nom);
    List<Contacte> cercaContactesPerCognoms(String cognoms);
    List<Contacte> cercaContactesPerTelefon(String telefon);
    List<Contacte> cercaContactesPerEmail(String email);
    List<Contacte> getTotsElsContactes();
}
