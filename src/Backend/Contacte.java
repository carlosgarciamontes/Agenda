package Backend;

//Source: https://spring.io/guides/gs/accessing-data-jpa
// https://www.baeldung.com/hibernate-search
// https://medium.com/@ramanamuttana/connect-hibernate-with-postgres-d8f29249db0c


import jakarta.persistence.*;

@Entity
@Table(name = "contacte")
public class Contacte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "cognoms")
    private String cognoms;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    //Needed by hibernate
    protected Contacte(){};

    public Contacte(int i, String nom, String cognoms, String telefon, String email) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contacte(String nom, String cognoms, String telefon, String email) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.telefon = telefon;
        this.email = email;
    }


    public String toString(){
        return String.format("ID: %d\n  Name: %s\n  Surname: %s\n  Phone: %s\n  Email: %s", this.id, this.nom, this.cognoms, this.telefon, this.email);
    }

    public String toFileContent(){
        return String.format("%d\n%s\n%s\n%s\n%s", this.id, this.nom, this.cognoms, this.telefon, this.email);
    }
}
