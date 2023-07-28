package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The login screen.
public class WelcomeUI {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 150;

    private JPanel panel;
    private JFrame frame;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton button;
    private String masterPassword = "password";

    // MODIFIES: this
    // EFFECTS: creates the login window, button, text field, and label
    public WelcomeUI()  {
        panel = new JPanel();
        panel.setLayout(null);

        passwordLabel = new JLabel("Master Password:");
        passwordLabel.setBounds(20, 20, 150, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 20, 150, 25);
        panel.add(passwordText);

        button = new JButton("Sign in");
        button.setBounds(330, 20, 90, 25);
        button.addActionListener(new ButtonListener());
        panel.add(button);

        frame = new JFrame();
        frame.setIconImage(new ImageIcon("PMIcon.png").getImage());
        frame.setTitle("PassMan Login");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(panel);
    }

    // Handles what to do when the button is pressed.
    private class ButtonListener implements ActionListener {

        // EFFECTS: if the password entered into passwordText matches masterPassword, closes this window and
        // runs the main application
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = passwordText.getText();
            if (input.equals(masterPassword)) {
                frame.dispose();
                new PassManUI();
            }
        }
    }

}
