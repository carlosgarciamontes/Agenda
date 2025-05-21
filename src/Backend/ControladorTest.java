package Backend;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ControladorTest {
    private Controlador controlador;

    @BeforeEach
    void setUp() {
        controlador = new Controlador();
    }

    @Test
    @DisplayName("Test creación y búsqueda básica de contactos")
    void testCreacionYBusquedaBasica() {
        // Crear primer contacto
        Contacte c1 = controlador.nouContacte("Carlos", "Garcia", "123456789", "carlos@gmail.com");
        assertNotNull(c1);
        assertEquals(1, c1.getId());
        assertEquals("Carlos", c1.getNom());

        // Verificar que existe
        Contacte encontrado = controlador.cercaContactesPerId(1);
        assertEquals(c1, encontrado);

        // Crear segundo contacto
        Contacte c2 = controlador.nouContacte("Pau", "Martínez", "987654321", "pau@gmail.com");
        assertEquals(2, c2.getId());

        // Buscar por nombre
        List<Contacte> nom = controlador.cercaContactesPerNom("Pepe");
        assertEquals(1, nom.size());
        assertEquals(c1, nom.get(0));
    }

    @Test
    @DisplayName("Test secuencial completo: crear, modificar, eliminar")
    void testSecuenciaCompleta() {
        // 1. Crear primer contacto
        Contacte c1 = controlador.nouContacte("Ferran", "Tena", "111222333", "ferran@tena.com");
        assertEquals(1, c1.getId());

        // 2. Crear segundo contacto
        Contacte c2 = controlador.nouContacte("Pedro", "Picapiedra", "444555666", "picapiedra@piedras.com");
        assertEquals(2, c2.getId());

        // 3. Verificar búsqueda
        List<Contacte> cognoms = controlador.cercaContactesPerCognoms("Picapiedra");
        assertEquals(1, cognoms.size());
        assertEquals(c2, cognoms.get(0));

        // 4. Eliminar un contacto
        controlador.esborrarContacte(1);
        assertNull(controlador.cercaContactesPerId(1));
        assertEquals(1, controlador.getTotsElsContactes().size());

        // 5. Crear tercer contacto (debería tener ID 3 aunque hayamos eliminado 1)
        Contacte c3 = controlador.nouContacte("Carlitos", "Garcia", "777888999", "laura@example.com");
        assertEquals(3, c3.getId());

        // 6. Modificar contacto
        Contacte modificado = controlador.actualitzarContacte(2, "Joan", "Messi Ronaldo", null, "cr7@messi.com");
        assertEquals("Messi Ronaldo", modificado.getCognoms());
        assertEquals("cr7@messi.com", modificado.getEmail());
        // Teléfono debería permanecer igual
        assertEquals("444555666", modificado.getTelefon());
    }

    @Test
    @DisplayName("Test IDs consecutivos después de eliminaciones")
    void testIDsConsecutivos() {
        Contacte c1 = controlador.nouContacte("A", "A", "1", "a@a.com");
        Contacte c2 = controlador.nouContacte("B", "B", "2", "b@b.com");
        controlador.esborrarContacte(1);
        Contacte c3 = controlador.nouContacte("C", "C", "3", "c@c.com");

        assertEquals(2, c2.getId());
        assertEquals(3, c3.getId()); // No debería reusar el ID 1
    }

    @Test
    @DisplayName("Test búsqueda case insensitive")
    void testBusquedaCaseInsensitive() {
        controlador.nouContacte("Carlos", "Garcia", "111", "carlos@garcia.com");

        List<Contacte> resultados = controlador.cercaContactesPerNom("CARLOS");
        assertEquals(1, resultados.size());

        resultados = controlador.cercaContactesPerEmail("CARLOS@GARCIA.COM");
        assertEquals(1, resultados.size());
    }

    @Test
    @DisplayName("Test actualización parcial")
    void testActualizacionParcial() {
        Contacte original = controlador.nouContacte("Fernando", "Alonso", "999888777", "el@nano.com");
        Contacte actualizado = controlador.actualitzarContacte(1, null, "Porrino", null, "porrino@gmail.com");

        assertEquals("Fernando", actualizado.getNom()); // No cambió
        assertEquals("Porrino", actualizado.getCognoms());
        assertEquals("999888777", actualizado.getTelefon()); // No cambió
        assertEquals("porrino@gmail.com", actualizado.getEmail());
    }

    @Test
    @DisplayName("Test obtener todos los contactos")
    void testGetTodosContactos() {
        assertEquals(0, controlador.getTotsElsContactes().size());

        controlador.nouContacte("A", "A", "1", "a@a.com");
        controlador.nouContacte("B", "B", "2", "b@b.com");

        List<Contacte> todos = controlador.getTotsElsContactes();
        assertEquals(2, todos.size());
    }
}