package persistence;

import model.LoginCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Based on the class with the same name in JsonSerializationDemo.
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            LoginCollection lc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            LoginCollection lc = reader.read();
            assertEquals(0,lc.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            LoginCollection lc = reader.read();
            assertEquals(2, lc.size());
            checkLogin("Facebook", "DerekSitu", "fgh678", lc.getLogin(0));
            checkLogin("Github", "m5n1b", "ufxxBtwj", lc.getLogin(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
