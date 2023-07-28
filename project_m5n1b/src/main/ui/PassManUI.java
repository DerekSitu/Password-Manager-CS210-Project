package ui;

import model.LoginCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// The main application window.
public class PassManUI {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final String JSON_STORE = "./data/collection.json";
    private JPanel buttonPane;
    private JFrame frame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private LoginCollection collection;
    private JList list;
    private DefaultListModel listModel;

    // MODIFIES: this
    // EFFECTS: creates a new PassMan app
    public PassManUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        collection = new LoginCollection();

        initializeList();
        initializeButtons();
        initializeFrame();
        initializeMenu();
    }

    // MODIFIES: this
    // EFFECTS: initializes a JList to be displayed
    private void initializeList() {
        listModel = new DefaultListModel();
        for (int i = 0; i < collection.size(); i++) {
            listModel.addElement(collection.getLogin(i).getSiteName());
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
    }

    // MODIFIES: this
    // EFFECTS: initializes all buttons and adds them to buttonPane
    private void initializeButtons() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(new ViewButtonListener());
        buttonPane.add(viewButton);

        JButton changeButton = new JButton("Change Password");
        changeButton.addActionListener(new ChangeButtonListener());
        buttonPane.add(changeButton);

        JButton deleteButton = new JButton("Delete Login");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPane.add(deleteButton);

        JButton addButton = new JButton("Add Login");
        addButton.addActionListener(new AddButtonListener());
        buttonPane.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes a JFrame with desired options, adds buttonPane, and a JScrollPane.
    private void initializeFrame() {
        frame = new JFrame();
        frame.setIconImage(new ImageIcon("PMIcon.png").getImage());
        frame.setTitle("PassMan");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(buttonPane, BorderLayout.PAGE_END);

        JScrollPane listScrollPane = new JScrollPane(list);
        frame.add(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: initializes a JMenuBar with a "File" JMenu with "Save" and "Load" JMenuItems
    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveItem = new JMenuItem("Save", new ImageIcon("saveIcon.png"));
        saveItem.addActionListener(new SaveButtonListener());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load", new ImageIcon("loadIcon.png"));
        loadItem.addActionListener(new LoadButtonListener());
        fileMenu.add(loadItem);

        frame.setJMenuBar(menuBar);
    }

    // Handles what to do when addButton is pressed.
    private class AddButtonListener implements ActionListener {

        JLabel siteNameLabel;
        JTextField siteNameText;
        JLabel userNameLabel;
        JTextField userNameText;
        JLabel passwordLabel;
        JPasswordField passwordText;
        JButton confirmButton;
        JPanel addPanel;
        JFrame addFrame;

        // EFFECTS: initializes a pop-up window when button is pressed.
        @Override
        public void actionPerformed(ActionEvent e) {
            initializeVisibleElements();
            initializeAddFrame();
        }

        // EFFECTS: initializes labels, text fields, and button with desired options
        private void initializeVisibleElements() {
            siteNameLabel = new JLabel("Site Name:");
            siteNameLabel.setBounds(20, 20, 150, 25);

            siteNameText = new JTextField(20);
            siteNameText.setBounds(150, 20, 150, 25);

            userNameLabel = new JLabel("Username:");
            userNameLabel.setBounds(20, 80, 150, 25);

            userNameText = new JTextField(20);
            userNameText.setBounds(150, 80, 150, 25);

            passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(20, 140, 150, 25);

            passwordText = new JPasswordField(20);
            passwordText.setBounds(150, 140, 150, 25);

            confirmButton = new JButton("Confirm");
            confirmButton.setBounds(210, 200, 90, 25);
            confirmButton.addActionListener(new ConfirmButtonListener());
        }

        // EFFECTS: initializes a new window with desired options.
        private void initializeAddFrame() {
            addPanel = new JPanel();
            addPanel.setLayout(null);
            addPanel.add(siteNameLabel);
            addPanel.add(siteNameText);
            addPanel.add(userNameLabel);
            addPanel.add(userNameText);
            addPanel.add(passwordLabel);
            addPanel.add(passwordText);
            addPanel.add(confirmButton);

            addFrame = new JFrame();
            addFrame.add(addPanel);
            addFrame.setIconImage(new ImageIcon("PMIcon.png").getImage());
            addFrame.setTitle("Add Login");
            addFrame.setSize(400, 300);
            addFrame.setLocationRelativeTo(null);
            addFrame.setVisible(true);
        }

        // Handles what to do when confirm button is pressed.
        private class ConfirmButtonListener implements ActionListener {

            // MODIFIES: PassManUI
            // EFFECTS: adds a Login to collection with the specified site name, username, and password, then closes
            // pop-up window.
            @Override
            public void actionPerformed(ActionEvent e) {
                collection.addLogin(siteNameText.getText(), userNameText.getText(), passwordText.getText());
                listModel.addElement(collection.getLogin(collection.size() - 1).getSiteName());
                addFrame.dispose();
            }
        }
    }

    // Handles what to do when changeButton is pressed.
    private class ChangeButtonListener implements ActionListener {

        JLabel newPasswordLabel;
        JPasswordField newPasswordText;
        JLabel confirmPasswordLabel;
        JPasswordField confirmPasswordText;
        JButton confirmButton;
        JPanel changePanel;
        JFrame changeFrame;

        // EFFECTS: if a list selection has been made, initializes a pop-up window.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(list.getSelectedIndex() == -1)) {
                initializeVisibleElements();
                initializeChangeFrame();
            }
        }

        // EFFECTS: initializes JLabels, JPasswordFields, and a JButton with desired options
        private void initializeVisibleElements() {
            newPasswordLabel = new JLabel("New Password:");
            newPasswordLabel.setBounds(20, 20, 150, 25);

            newPasswordText = new JPasswordField(20);
            newPasswordText.setBounds(150, 20, 150, 25);

            confirmPasswordLabel = new JLabel("Confirm Password:");
            confirmPasswordLabel.setBounds(20, 80, 150, 25);

            confirmPasswordText = new JPasswordField(20);
            confirmPasswordText.setBounds(150, 80, 150, 25);

            confirmButton = new JButton("Confirm");
            confirmButton.setBounds(210, 160, 90, 25);
            confirmButton.addActionListener(new ConfirmButtonListener());
        }

        // EFFECTS: initializes a pop-up window.
        private void initializeChangeFrame() {
            changePanel = new JPanel();
            changePanel.setLayout(null);
            changePanel.add(newPasswordLabel);
            changePanel.add(newPasswordText);
            changePanel.add(confirmPasswordLabel);
            changePanel.add(confirmPasswordText);
            changePanel.add(confirmButton);

            changeFrame = new JFrame();
            changeFrame.add(changePanel);
            changeFrame.setIconImage(new ImageIcon("PMIcon.png").getImage());
            changeFrame.setTitle("Change Password");
            changeFrame.setSize(400, 260);
            changeFrame.setLocationRelativeTo(null);
            changeFrame.setVisible(true);
        }

        // Handles what to do when confirm button is pressed.
        private class ConfirmButtonListener implements ActionListener {

            // EFFECTS: if inputted passwords match, changes the password of the selected Login to the given password.
            // Then closes pop-up window.

            @Override
            public void actionPerformed(ActionEvent e) {
                if (newPasswordText.getText().equals(confirmPasswordText.getText())) {
                    collection.getLogin(list.getSelectedIndex()).setPassword(newPasswordText.getText());
                    changeFrame.dispose();
                } else {
                    // do nothing
                    // TODO: give an error message
                }
            }
        }
    }

    // Handles what to do when viewButton is pressed.
    private class ViewButtonListener implements ActionListener {

        // EFFECTS: if a login has been selected, creates a pop-up window displaying login details.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextArea textArea = new JTextArea(5, 50);
                textArea.setEditable(false);
                textArea.append("Site Name: \t\t" + collection.getLogin(list.getSelectedIndex()).getSiteName() + "\n"
                        + "Username: \t\t" + collection.getLogin(list.getSelectedIndex()).getUserName() + "\n"
                        + "Encrypted Password: \t"
                        + collection.getLogin(list.getSelectedIndex()).getEncryptedPassword());

                JScrollPane scrollPane = new JScrollPane(textArea);

                JFrame viewFrame = new JFrame();
                viewFrame.setSize(400, 150);
                viewFrame.setIconImage(new ImageIcon("PMIcon.png").getImage());
                viewFrame.setTitle("Login Details");
                viewFrame.setLocationRelativeTo(null);
                viewFrame.setVisible(true);
                viewFrame.add(scrollPane);

            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                // do nothing
            }
        }
    }

    // Handles what to do when deleteButton is pressed.
    private class DeleteButtonListener implements ActionListener {

        // MODIFIES: PassManUI
        // EFFECTS: if a login has been selected, deletes the login.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listModel.remove(list.getSelectedIndex());
                collection.deleteLogin(list.getSelectedIndex() + 1);
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                // do nothing
            }
        }
    }

    // Handles what to do when saveItem has been selected.
    private class SaveButtonListener implements ActionListener {

        // EFFECTS: saves the workroom to file
        // From JsonSerializationDemo.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(collection);
                jsonWriter.close();
            } catch (FileNotFoundException fileNotFoundException) {
                // do nothing
                // TODO: display an error message
            }
        }
    }

    // Handles what to do when loadItem has been selected.
    private class LoadButtonListener implements ActionListener {

        // MODIFIES: PassManUI
        // EFFECTS: loads workroom from file
        // From JsonSerializationDemo.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                collection = jsonReader.read();
                frame.dispose();
                initializeList();
                initializeButtons();
                initializeFrame();
                initializeMenu();
            } catch (IOException ioException) {
                // do nothing
                // TODO: display an error message
            }
        }
    }
}
