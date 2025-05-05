package Backend;

public class Contacte {
    // Atributos (ahora privados para mejor encapsulamiento)
    private int id;
    private String nom;
    private String cognoms;
    private String telefon;
    private String email;

    // Constructores
    public Contacte() {
    }

    public Contacte(int id, String nom, String cognoms, String telefon, String email) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.telefon = telefon;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método toString mejorado
    @Override
    public String toString() {
        return "ID: " + id +
                "\nNom: " + nom +
                "\nCognoms: " + cognoms +
                "\nTelèfon: " + telefon +
                "\nEmail: " + email + "\n";
    }

    // Método adicional para mostrar información resumida
    public String infoResumida() {
        return id + ": " + nom + " " + cognoms;
    }
}
