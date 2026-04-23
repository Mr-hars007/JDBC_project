package crex.gui;

import javax.swing.*;
import java.awt.*;
import crex.dao.UserDAO;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;

    public LoginFrame() {
        setTitle("CREX - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField(15);
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passField = new JPasswordField(15);
        add(passField, gbc);

        JPanel btnPanel = new JPanel();
        loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");
        btnPanel.add(loginBtn);
        btnPanel.add(regBtn);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (UserDAO.login(user, pass)) {
                dispose();
                new MainFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login!");
            }
        });

        regBtn.addActionListener(e -> {
            JDialog regDialog = new JDialog(this, "Register New User", true);
            regDialog.setLayout(new BorderLayout());
            regDialog.add(new UserPanel(), BorderLayout.CENTER);
            regDialog.pack();
            regDialog.setLocationRelativeTo(this);
            regDialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
