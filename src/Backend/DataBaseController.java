package Backend;

import jakarta.persistence.criteria.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.logging.Level;

public class DataBaseController implements Controlador, AutoCloseable {
    private final SessionFactory factory;
    private final Session session;
    private final CriteriaBuilder criteriaBuilder;

    public DataBaseController() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        this.session = factory.openSession();
        this.criteriaBuilder = factory.getCriteriaBuilder();
    }

    @Override
    public Contacte nouContacte(String nom, String cognoms, String telefon, String email) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contacte nou = new Contacte(nom, cognoms, telefon, email);
            session.persist(nou);
            tx.commit();
            return nou;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al crear contacto", e);
        }
    }

    @Override
    public Contacte actualitzarContacte(int id, String nom, String cognoms, String telefon, String email) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contacte c = session.get(Contacte.class, id);
            if (c != null) {
                if (nom != null) c.setNom(nom);
                if (cognoms != null) c.setCognoms(cognoms);
                if (telefon != null) c.setTelefon(telefon);
                if (email != null) c.setEmail(email);
                session.merge(c);
            }
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al actualizar contacto", e);
        }
    }

    @Override
    public void esborrarContacte(int id) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contacte c = session.get(Contacte.class, id);
            if (c != null) {
                session.remove(c);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al eliminar contacte", e);
        }
    }

    @Override
    public Contacte cercaContactesPerId(int id) {
        return session.get(Contacte.class, id);
    }

    @Override
    public List<Contacte> cercaContactesPerNom(String nom) {
        return cercarPerCamp("nom", nom);
    }

    @Override
    public List<Contacte> cercaContactesPerCognoms(String cognoms) {
        return cercarPerCamp("cognoms", cognoms);
    }

    @Override
    public List<Contacte> cercaContactesPerTelefon(String telefon) {
        return cercarPerCamp("telefon", telefon);
    }

    @Override
    public List<Contacte> cercaContactesPerEmail(String email) {
        return cercarPerCamp("email", email);
    }

    @Override
    public List<Contacte> getTotsElsContactes() {
        CriteriaQuery<Contacte> query = criteriaBuilder.createQuery(Contacte.class);
        query.select(query.from(Contacte.class));
        return session.createQuery(query).getResultList();
    }

    private List<Contacte> cercarPerCamp(String camp, String valor) {
        CriteriaQuery<Contacte> query = criteriaBuilder.createQuery(Contacte.class);
        Root<Contacte> root = query.from(Contacte.class);
        query.select(root).where(criteriaBuilder.like(
                criteriaBuilder.lower(root.get(camp)),
                "%" + valor.toLowerCase() + "%"
        ));
        return session.createQuery(query).getResultList();
    }

    @Override
    public void close() throws Exception {
        if (session != null) session.close();
        if (factory != null) factory.close();
    }
}