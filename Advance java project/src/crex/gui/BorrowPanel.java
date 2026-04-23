package crex.gui;

import javax.swing.*;
import java.awt.*;
import crex.dao.BorrowDAO;
import crex.dao.ResourceDAO;
import crex.dao.UserDAO;
import crex.model.Resource;
import java.util.List;

public class BorrowPanel extends JPanel {
    private JTextField resIdField;
    private JButton borrowBtn, returnBtn, refreshBtn;
    private JList<Resource> borrowedList;
    private DefaultListModel<Resource> listModel;

    public BorrowPanel() {
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Borrow/Return Actions"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Resource ID:"), gbc);
        gbc.gridx = 1;
        resIdField = new JTextField(10);
        inputPanel.add(resIdField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        borrowBtn = new JButton("Borrow");
        returnBtn = new JButton("Return");
        refreshBtn = new JButton("Refresh Status");
        btnPanel.add(borrowBtn);
        btnPanel.add(returnBtn);
        btnPanel.add(refreshBtn);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        inputPanel.add(btnPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // List Panel
        listModel = new DefaultListModel<>();
        borrowedList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(borrowedList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("My Borrowed Resources"));
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        borrowBtn.addActionListener(e -> {
            try {
                int resId = Integer.parseInt(resIdField.getText());
                int currentUserId = UserDAO.getCurrentUserId();
                if (BorrowDAO.borrowResource(resId, currentUserId)) {
                    JOptionPane.showMessageDialog(this, "Resource borrowed successfully!");
                    refreshBorrowedList();
                    resIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Resource not available or ID incorrect!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int resId = Integer.parseInt(resIdField.getText());
                BorrowDAO.returnResource(resId);
                JOptionPane.showMessageDialog(this, "Resource returned successfully!");
                refreshBorrowedList();
                resIdField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refreshBtn.addActionListener(e -> refreshBorrowedList());

        refreshBorrowedList();
    }

    private void refreshBorrowedList() {
        try {
            List<Resource> borrowed = ResourceDAO.getBorrowedByMe();
            listModel.clear();
            for (Resource r : borrowed) {
                listModel.addElement(r);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading borrowed list: " + ex.getMessage());
        }
    }
}
