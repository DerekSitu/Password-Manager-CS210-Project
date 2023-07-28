package model;

import org.json.JSONObject;
import persistence.Writable;

// login info with a site name, username, and encrypted password
public class Login implements Writable {

    public String siteName;
    public String userName;
    public String encryptedPassword;

    // constructs a new Login consisting of a siteName, userName, and encrypted password.
    public Login(String sn, String un, String pw) {
        siteName = sn;
        userName = un;
        encryptedPassword = encryptPassword(pw);
    }

    public String getSiteName() {
        return this.siteName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public void setPassword(String pw) {
        encryptedPassword = encryptPassword(pw);
    }

    // MODIFIES: this
    // EFFECTS: returns given password with caesarCipher applied
    public String encryptPassword(String pw) {
        String epw = "";
        for (char c : pw.toCharArray()) {
            c = caesarCipher(c);
            epw = epw.concat(String.valueOf(c));
        }
        return epw;
    }

    // EFFECTS: returns the character 5 units to the right of c in alphabet.
    // Starts back at 'a' if counts past 76th character.
    private char caesarCipher(char c) {
        String alphabet = "abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-=!@#$%^&*()_+";
        char d = 'a';
        for (int i = 0; i < alphabet.length(); i++) {
            if (c == alphabet.charAt(i)) {
                d = alphabet.charAt((i + 5) % 76);
                break;
            }
        }
        return d;
    }

    // EFFECTS: returns login information in this Login as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("encrypted password", encryptedPassword);
        json.put("site name", siteName);
        json.put("username", userName);
        return json;
    }
}
