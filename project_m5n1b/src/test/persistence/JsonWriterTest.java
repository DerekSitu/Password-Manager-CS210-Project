package persistence;

import model.LoginCollection;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Based on the class with the same name in JsonSerializationDemo.
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            LoginCollection lc = new LoginCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            LoginCollection lc = new LoginCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(lc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            lc = reader.read();
            assertEquals(0, lc.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCollection() {
        try {
            LoginCollection lc = new LoginCollection();
            lc.addLogin("Facebook", "DerekSitu", "abc123");
            lc.addLogin("Github", "m5n1b", "password");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(lc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            lc = reader.read();
            assertEquals(2, lc.size());
            checkLogin("Facebook", "DerekSitu", lc.getLogin(0).encryptPassword("abc123"),
                    lc.getLogin(0));
            checkLogin("Github", "m5n1b", lc.getLogin(1).encryptPassword("password"),
                    lc.getLogin(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
