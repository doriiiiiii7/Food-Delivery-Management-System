package presentation;

import business.DeliveryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class EmployeeGUI extends JFrame implements Observer {
        private JPanel contentPane;
        private JTextArea textArea;
        public EmployeeGUI(DeliveryService deliveryService) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 400, 300);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            JLabel lblNewLabel = new JLabel("ORDERS PLACED BY CLIENTS");
            lblNewLabel.setFont(new Font("Felix Titling", Font.PLAIN, 20));
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setBounds(0, 0, 386, 36);
            contentPane.add(lblNewLabel);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(0, 47, 386, 216);
            contentPane.add(scrollPane);

            textArea = new JTextArea();
            scrollPane.setViewportView(textArea);
            setTitle("Employee Window");
        }

    @Override
    public void update(Observable o, Object arg) {
        textArea.setText((String) arg);
    }
}
