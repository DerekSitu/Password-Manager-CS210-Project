package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginCollectionTest {

    private LoginCollection collection;
    private Login l;

    @BeforeEach
    public void runBefore() {
        collection = new LoginCollection();
        l = new Login("Facebook", "Jeff Jeffson", "strongpass");
    }

    @Test
    public void testAddLoginOneParam() {
        collection.addLogin(l);
        assertEquals(1, collection.size());
        assertEquals(l, collection.getLogin(0));
    }

    @Test
    public void testAddLoginThreeParam() {
        collection.addLogin("Facebook", "Jeff Jeffson", "strongpass");
        assertEquals(1, collection.size());
        assertEquals("Facebook", collection.getLogin(0).getSiteName());
        assertEquals("Jeff Jeffson", collection.getLogin(0).getUserName());
        assertEquals(collection.getLogin(0).encryptPassword("strongpass"),
                collection.getLogin(0).getEncryptedPassword());
    }

    @Test
    public void testDeleteLoginNothingThrown() {
        try {
            collection.addLogin(l);
            assertEquals(1, collection.size());
            collection.deleteLogin(0);
            assertEquals(0, collection.size());
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            fail("not supposed to catch this exception");
        }
    }

    @Test
    public void testDeleteLoginCatchOutOfBounds() {
        try {
            collection.addLogin(l);
            assertEquals(1, collection.size());
            collection.deleteLogin(-20);
            fail("supposed to catch IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            assertEquals(1, collection.size());
        }
    }

    @Test
    public void testGetLoginNothingThrown() {
        try {
            collection.addLogin(l);
            assertEquals(l, collection.getLogin(0));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            fail("not supposed to catch this exception");
        }
    }

    @Test
    public void testGetLoginCatchOutOfBounds() {
        try {
            collection.addLogin(l);
            assertEquals(1, collection.size());
            assertEquals(l, collection.getLogin(-20));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // pass
        }
    }

    @Test
    public void testCollectionSize() {
        assertEquals(0, collection.size());
        collection.addLogin(l);
        assertEquals(1, collection.size());
    }
}
