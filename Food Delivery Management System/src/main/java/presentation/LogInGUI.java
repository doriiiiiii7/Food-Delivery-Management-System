package presentation;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LogInGUI extends JFrame {

    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton logInButton;
    private JTextField createAccountField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LogInGUI frame = new LogInGUI();
                    LogInController controller = new LogInController(frame);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LogInGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle("Log In Window");
        setBackground(new Color(255, 204, 204));
        contentPane.setBackground( new Color(255, 204, 204));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordField.setEchoChar('*');
        passwordField.setBounds(170, 60, 130, 20);
        contentPane.add(passwordField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordLabel.setBounds(60, 60, 100, 20);
        contentPane.add(passwordLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        usernameLabel.setBounds(60, 30, 100, 20);
        contentPane.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(170, 30, 130, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        logInButton = new JButton("LOG IN");
        logInButton.setBounds(170, 90, 80, 25);
        contentPane.add(logInButton);

        createAccountField = new JTextField("You don't have an account? Create one!");
        createAccountField.setFont(new Font("Tahoma", Font.PLAIN, 10));
        createAccountField.setBounds(220, 140, 180, 15);
        createAccountField.setEditable(false);
        createAccountField.setBorder(null);
        createAccountField.setBackground(new Color(255, 204, 204));
        contentPane.add(createAccountField);
    }

    public void createAccountListener(MouseListener mouseListener){
        createAccountField.addMouseListener(mouseListener);
    }

    public void logInButtonListener(ActionListener actionListener){
        logInButton.addActionListener(actionListener);
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return new String(passwordField.getPassword());
    }

    public JTextField getCreateAccountLabel(){
        return createAccountField;
    }

    public void printError(String msg){
        JOptionPane.showMessageDialog(this, msg, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public void printMessage(String msg){
        JOptionPane.showMessageDialog(this, msg, "Yey!", JOptionPane.INFORMATION_MESSAGE);
    }
}