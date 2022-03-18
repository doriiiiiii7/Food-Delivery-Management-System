package presentation;

import business.BaseProduct;
import business.DeliveryService;
import business.MenuItem;
import data.Serializator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminController {
    private AdminGUI adminGUI;
    public AdminController(AdminGUI adminGUI){
        this.adminGUI = adminGUI;

        adminGUI.addSaveDataListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Serializator.serialize();
            }
        });

        adminGUI.addLoadDataListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Serializator.deserialize();
            }
        });

        adminGUI.addImportProductsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeliveryService ds = new DeliveryService();
                try {
                    ds.importProducts();
                    adminGUI.printMessage("The import was successful!");
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        adminGUI.addGenerateReportListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateWindow generateWindow = new GenerateWindow();
                GenerateWindowController controller = new GenerateWindowController(generateWindow);
                generateWindow.setVisible(true);
            }
        });

        adminGUI.addManageProductsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablePopupMenu tablePopupMenu = new TablePopupMenu();
                tablePopupMenu.setVisible(true);
            }
        });
    }

    public class GenerateWindow extends JFrame {
            private JPanel contentPane;
            private JTextField firstInput;
            private JTextField secondInput;
            private JComboBox<String> comboBox;
            private JLabel secondLabel;
            private JLabel firstLabel;
            private JButton generateButton;


            public GenerateWindow() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 400, 170);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel description = new JLabel("Select the report you want to generate");
                description.setHorizontalAlignment(SwingConstants.CENTER);
                description.setBounds(10, 11, 366, 14);
                contentPane.add(description);

                ArrayList<String> reports = new ArrayList<>();
                reports.add("peak hour for orders");
                reports.add("the best products");
                reports.add("the loyal customers");
                reports.add("best products of the day");
                comboBox = new JComboBox(reports.toArray());
                comboBox.setBounds(45, 35, 285, 20);
                contentPane.add(comboBox);


                firstInput = new JTextField();
                firstInput.setBounds(115, 70, 60, 20);
                contentPane.add(firstInput);
                firstInput.setColumns(10);

                secondInput = new JTextField();
                secondInput.setColumns(10);
                secondInput.setBounds(270, 70, 60, 20);
                contentPane.add(secondInput);

                firstLabel = new JLabel("start");
                firstLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                firstLabel.setBounds(25, 70, 80, 20);
                contentPane.add(firstLabel);

                secondLabel = new JLabel("end");
                secondLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                secondLabel.setBounds(180, 70, 80, 20);
                contentPane.add(secondLabel);

                generateButton = new JButton("Generate");
                generateButton.setBounds(140, 100, 90, 25);
                contentPane.add(generateButton);
                setTitle("Generate Window");
            }

            public void addGenerateButtonListener(ActionListener actionListener){
                generateButton.addActionListener(actionListener);
            }

            public void setFirstLabel(String text){
                firstLabel.setText(text);
            }

            public void setSecondLabel(String text){
                secondLabel.setText(text);
            }

            public JTextField getFirstInput(){
                return firstInput;
            }

            public JTextField getSecondInput(){
                return secondInput;
            }

            public int getFirstParameter(){
                return Integer.parseInt(firstInput.getText());
            }

            public int getSecondParameter(){
                return Integer.parseInt(secondInput.getText());
            }

            public void addComboBoxListener(ActionListener actionListener){
                comboBox.addActionListener(actionListener);
            }

            public int getReportNumber(){
                return comboBox.getSelectedIndex();
            }
        }

        public class GenerateWindowController{
            private GenerateWindow generateWindow;

            public GenerateWindowController(GenerateWindow generateWindow){
                this.generateWindow = generateWindow;

                generateWindow.addComboBoxListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int report = generateWindow.getReportNumber();
                        switch (report){
                            case 0:{
                                generateWindow.getFirstInput().setEditable(true);
                                generateWindow.getSecondInput().setEditable(true);
                                generateWindow.setFirstLabel("start");
                                generateWindow.setSecondLabel("end");
                                break;
                            }
                            case 1:{
                                generateWindow.getFirstInput().setEditable(true);
                                generateWindow.getSecondInput().setEditable(false);
                                generateWindow.getSecondInput().setText("");
                                generateWindow.setFirstLabel("freq");
                                generateWindow.setSecondLabel("");
                                break;
                            }
                            case 2:{
                                generateWindow.getFirstInput().setEditable(true);
                                generateWindow.getSecondInput().setEditable(true);
                                generateWindow.setFirstLabel("freq");
                                generateWindow.setSecondLabel("amount");
                                break;
                            }
                            case 3:{
                                generateWindow.getFirstInput().setEditable(true);
                                generateWindow.getSecondInput().setEditable(true);
                                generateWindow.setFirstLabel("day");
                                generateWindow.setSecondLabel("month");
                                break;
                            }
                        }
                    }
                });

                generateWindow.addGenerateButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int report = generateWindow.getReportNumber();
                        DeliveryService ds = new DeliveryService();
                        switch (report){
                            case 0:{
                                ds.generateTimeReport(generateWindow.getFirstParameter(), generateWindow.getSecondParameter());
                                break;
                            }
                            case 1:{
                                ds.generateProductsReport(generateWindow.getFirstParameter());
                                break;
                            }
                            case 2:{
                                ds.generateClientsReport(generateWindow.getFirstParameter(), generateWindow.getSecondParameter());
                                break;
                            }
                            case 3:{
                                LocalDate date = LocalDate.of(2021, generateWindow.getSecondParameter(), generateWindow.getFirstParameter());
                                ds.generateProductsWithinSpecifiedDayReport(date.toString());
                                break;
                            }

                            }
                    }
                });
            }
        }
}
