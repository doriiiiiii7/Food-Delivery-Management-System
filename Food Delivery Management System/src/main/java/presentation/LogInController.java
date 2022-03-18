package presentation;

import business.Client;
import business.DeliveryService;
import business.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class LogInController {
    private LogInGUI view;
    public static  String lastClientLoggedIn;
    DeliveryService deliveryService = new DeliveryService();
    public EmployeeGUI employeeGUI = new EmployeeGUI(deliveryService);
    public LogInController(LogInGUI view){
        this.view = view;

        view.logInButtonListener(e -> {
            String username = view.getUsername();
            String password = view.getPassword();
            boolean correctUsername = false;
            boolean correctPassword = false;
            String role = "none";
            if(!DeliveryService.users.isEmpty()) {
                for (User user : DeliveryService.users) {
                    if (user.getUsername().equals(username)) {
                        correctUsername = true;
                        username = user.getUsername();
                        if(user.getPassword().equals(password)) {
                            correctPassword = true;
                        }
                        role = user.getRole().toString();
                        break;
                    }
                }

                if(correctPassword && correctUsername) {
                    deliveryService.addObserver(employeeGUI);
                    switch (role) {
                        case "CLIENT" -> {
                            ClientGUI clientGUI = new ClientGUI(deliveryService);
                            ClientController controller = new ClientController(clientGUI);
                            clientGUI.setVisible(true);
                            lastClientLoggedIn = username;
                        }
                        case "ADMIN" -> {
                            AdminGUI adminGUI = new AdminGUI();
                            AdminController controller = new AdminController(adminGUI);
                            adminGUI.setVisible(true);
                        }
                        case "EMPLOYEE" -> {
                            EmployeeController controller = new EmployeeController(employeeGUI);
                            employeeGUI.setVisible(true);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + role);
                    }
                    } else{
                        view.printError("Incorrect username or password!");
                    }
            }
            else{
                view.printMessage("You must create an account first!");
            }
        });

        view.createAccountListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CreateAccountGUI secondaryView = new CreateAccountGUI();
                CreateAccountController controller = new CreateAccountController(secondaryView);
                secondaryView.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                view.getCreateAccountLabel().setForeground(Color.RED);
                Font font = view.getCreateAccountLabel().getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                view.getCreateAccountLabel().setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                view.getCreateAccountLabel().setForeground(Color.BLACK);
                view.getCreateAccountLabel().setFont(new Font("Tahoma", Font.PLAIN, 10));
            }
        });
    }

    public class CreateAccountGUI extends JFrame{

        private JPanel contentPane;
        private JPasswordField passwordField;
        private JTextField usernameField;
        private JButton createAccountButton;
        private JTextField nameField;

        public CreateAccountGUI() {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 450, 200);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);
            setTitle("Create Account Window");
            setBackground(new Color(255, 204, 204));
            contentPane.setBackground( new Color(255, 204, 204));


            passwordField = new JPasswordField();
            passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
            passwordField.setEchoChar('*');
            passwordField.setBounds(170, 90, 130, 20);
            contentPane.add(passwordField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            passwordLabel.setBounds(60, 90, 100, 20);
            contentPane.add(passwordLabel);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            usernameLabel.setBounds(60, 60, 100, 20);
            contentPane.add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(170, 60, 130, 20);
            contentPane.add(usernameField);
            usernameField.setColumns(10);

            createAccountButton = new JButton("CREATE ACCOUNT");
            createAccountButton.setBounds(130, 130, 150, 20);
            contentPane.add(createAccountButton);

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            nameLabel.setBounds(60, 30, 100, 20);
            contentPane.add(nameLabel);

            nameField = new JTextField();
            nameField.setColumns(10);
            nameField.setBounds(170, 30, 130, 20);
            contentPane.add(nameField);
        }

        public void addCreateAccountButtonListener(ActionListener actionListener){
            createAccountButton.addActionListener(actionListener);
        }

        public String getUsername(){
            return usernameField.getText();
        }

        public String getPassword(){
            return new String(passwordField.getPassword());
        }

        public String getName(){
            return nameField.getText();
        }

        public void printError(String msg){
            JOptionPane.showMessageDialog(this, msg, "Error!", JOptionPane.ERROR_MESSAGE);
        }

        public void printMessage(String msg){
            JOptionPane.showMessageDialog(this, msg, "Yey!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public class CreateAccountController{
        private CreateAccountGUI secondView;

        public CreateAccountController(CreateAccountGUI secondView){
            this.secondView = secondView;
            secondView.addCreateAccountButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newUsername = secondView.getUsername();
                    String password = secondView.getPassword();
                    String name = secondView.getName();

                    if(newUsername.equals("") || password.equals("") || name.equals(""))
                        secondView.printError("You must complete all fields in order to create an account!");
                    else{
                        User newUser = new User(newUsername, password, "CLIENT");
                        if(!DeliveryService.users.isEmpty() && DeliveryService.users.contains(newUser))
                            secondView.printError("This username already exists! Try another one!");
                        else {
                            int id = 0;
                            for(User user: DeliveryService.users){
                                if(user instanceof Client) {
                                    if (((Client) user).getClientID() > id)
                                        id = ((Client) user).getClientID();
                                }
                            }
                            id += 10;
                            DeliveryService.users.add(new Client(newUsername, password, "CLIENT", id, name));
                            secondView.printMessage("Client " + name + " was added successfully with id " + id);
                        }
                    }
                }
            });
        }
    }
}
