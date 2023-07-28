package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Login login;

    @BeforeEach
    public void runBefore() {
        login = new Login("edX","BobSmith","abc123");
    }

    @Test
    public void testEncryptPasswordNotPastIndex76() {
        assertEquals("fg", login.encryptPassword("ab"));
    }

    @Test
    public void testEncryptPasswordPastIndex76() {
        assertEquals("bc", login.encryptPassword("()"));
    }

    @Test
    public void testSetPassword() {
        assertEquals(login.encryptedPassword, login.encryptPassword("abc123"));
        login.setPassword("password456");
        assertEquals(login.encryptedPassword, login.encryptPassword("password456"));
    }
}