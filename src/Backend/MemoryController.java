package Backend;

import java.lang.reflect.Field;

public abstract class MemoryController implements Controlador {

    protected void setContactID(Contacte c, int id) {
        try {
            Field idField = c.getClass().getDeclaredField("ID");
            idField.setAccessible(true);
            idField.setInt(c, id);
            idField.setAccessible(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
