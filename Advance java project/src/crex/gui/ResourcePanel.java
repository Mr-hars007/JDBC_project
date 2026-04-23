package crex.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import crex.dao.ResourceDAO;
import crex.model.Resource;

public class ResourcePanel extends JPanel {
    private JTextField titleField, ownerIdField;
    private JButton addBtn, refreshBtn;
    private JList<Resource> resourceList;
    private DefaultListModel<Resource> listModel;

    public ResourcePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Resource"));
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Owner User ID:"));
        ownerIdField = new JTextField();
        inputPanel.add(ownerIdField);
        addBtn = new JButton("Add Resource");
        inputPanel.add(addBtn);
        refreshBtn = new JButton("Refresh Available");
        inputPanel.add(refreshBtn);

        add(inputPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        resourceList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(resourceList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Resources"));
        add(scrollPane, BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            String title = titleField.getText();
            String ownerStr = ownerIdField.getText();
            if (title.isEmpty() || ownerStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            try {
                int ownerId = Integer.parseInt(ownerStr);
                ResourceDAO.addResource(title, ownerId);
                JOptionPane.showMessageDialog(this, "Resource added successfully!");
                titleField.setText("");
                ownerIdField.setText("");
                refreshList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Owner ID must be a number!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refreshBtn.addActionListener(e -> refreshList());
        
        refreshList(); // Initial load
    }

    private void refreshList() {
        try {
            List<Resource> resources = ResourceDAO.getAvailableResources();
            listModel.clear();
            for (Resource r : resources) {
                listModel.addElement(r);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading resources: " + ex.getMessage());
        }
    }
}
