package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// a list of logins
public class LoginCollection implements Writable {

    public ArrayList<Login> collection;

    // constructs a new LoginCollection as a list of Logins
    public LoginCollection() {
        collection = new ArrayList<>();
    }

    // adds a new login to the login collection
    // MODIFIES: this
    // EFFECTS: adds l to the end of collection
    public void addLogin(Login l) {
        collection.add(l);
    }

    // adds a new login to the login collection
    // MODIFIES: this
    // EFFECTS: creates a new Login with siteName sn, userName un, and password pw and adds it to the end of collection
    public void addLogin(String sn, String un, String pw) {
        Login l = new Login(sn, un, pw);
        collection.add(l);
    }

    // deletes the login at index i
    // MODIFIES: this
    // EFFECTS: deletes the Login at index i of collection
    public void deleteLogin(int i) throws IndexOutOfBoundsException {
        collection.remove(i);
    }

    public Login getLogin(int i) throws IndexOutOfBoundsException {
        return collection.get(i);
    }

    // EFFECTS: returns the size of the collection
    public int size() {
        return collection.size();
    }

    // EFFECTS: returns this collection as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Logins", collectionToJson());
        return json;
    }

    // EFFECTS: returns things in this collection as a JSONArray
    private JSONArray collectionToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Login l : collection) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}


