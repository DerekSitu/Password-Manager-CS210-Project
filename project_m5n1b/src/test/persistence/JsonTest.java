package persistence;

import model.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Based on the class with the same name in JsonSerializationDemo.
public class JsonTest {

    protected void checkLogin(String siteName, String userName, String encryptedPassWord, Login login) {
        assertEquals(siteName, login.getSiteName());
        assertEquals(userName, login.getUserName());
        assertEquals(encryptedPassWord, login.getEncryptedPassword());
    }
}