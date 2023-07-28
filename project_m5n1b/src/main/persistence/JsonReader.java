package persistence;

import model.Login;
import model.LoginCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// Based on the class of the same name from JsonSerializationDemo.
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads lc from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LoginCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLoginCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses lc from JSON object and returns it
    private LoginCollection parseLoginCollection(JSONObject jsonObject) {
        LoginCollection lc = new LoginCollection();
        addLogins(lc, jsonObject);
        return lc;
    }

    // MODIFIES: lc
    // EFFECTS: parses Logins from JSON object and adds them to lc
    private void addLogins(LoginCollection lc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Logins");
        for (Object json : jsonArray) {
            JSONObject nextLogin = (JSONObject) json;
            addLogin(lc, nextLogin);
        }
    }

    // MODIFIES: lc
    // EFFECTS: parses Login from JSON object and adds it to lc
    private void addLogin(LoginCollection lc, JSONObject jsonObject) {
        String siteName = jsonObject.getString("site name");
        String userName = jsonObject.getString("username");
        Login l = new Login(siteName, userName, "");
        l.encryptedPassword = jsonObject.getString("encrypted password");
        lc.addLogin(l);
    }
}
