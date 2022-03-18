package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {
    private JPanel contentPane;
    private JButton importProductsButton;
    private JButton manageProductsButton;
    private JButton generateReportsButton;
    private JButton saveDataButton;
    private JButton loadDataButton;

    public AdminGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 280, 280);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBackground(new Color(255, 204, 204));
        contentPane.setBackground( new Color(255, 204, 204));

        importProductsButton = new JButton("Import Products");
        importProductsButton.setBounds(50, 60, 160, 25);
        contentPane.add(importProductsButton);

        manageProductsButton = new JButton("Manage Products");
        manageProductsButton.setBounds(50, 95, 160, 25);
        contentPane.add(manageProductsButton);

        generateReportsButton = new JButton("Generate Report");
        generateReportsButton.setBounds(50, 130, 160, 25);
        contentPane.add(generateReportsButton);

        saveDataButton = new JButton("Save Data");
        saveDataButton.setBounds(50, 165, 160, 25);
        contentPane.add(saveDataButton);

        loadDataButton = new JButton("Load Data");
        loadDataButton.setBounds(50, 200, 160, 25);
        contentPane.add(loadDataButton);

        JLabel titleLabel = new JLabel("ADMIN HOMEPAGE");
        titleLabel.setFont(new Font("Felix Titling", Font.PLAIN, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(50, 20, 160, 20);
        contentPane.add(titleLabel);
        setTitle("Admin Homepage");
    }

    public void addSaveDataListener(ActionListener actionListener){
        saveDataButton.addActionListener(actionListener);
    }

    public void addLoadDataListener(ActionListener actionListener){
        loadDataButton.addActionListener(actionListener);
    }

    public void addImportProductsListener(ActionListener actionListener){
        importProductsButton.addActionListener(actionListener);
    }

    public void addManageProductsListener(ActionListener actionListener){
        manageProductsButton.addActionListener(actionListener);
    }

    public void addGenerateReportListener(ActionListener actionListener){
        generateReportsButton.addActionListener(actionListener);
    }

    public void printMessage(String msg){
        JOptionPane.showMessageDialog(this, msg, "Yey!", JOptionPane.INFORMATION_MESSAGE);
    }
}
