package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddItemView extends JFrame {

    private JPanel contentPane;
    private JButton addButton;
    private JTextField titleField;
    private JTextField ratingField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField fatField;
    private JTextField sodiumField;
    private JTextField priceField;

    public AddItemView(String windowTitle) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 170);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBackground(new Color(255, 204, 204));
        contentPane.setBackground( new Color(255, 204, 204));
        setTitle(windowTitle);

        addButton = new JButton("Add");
        addButton.setBounds(135, 100, 105, 25);
        contentPane.add(addButton);

        titleField = new JTextField();
        titleField.setBounds(60, 15, 310, 20);
        contentPane.add(titleField);
        titleField.setColumns(10);

        ratingField = new JTextField();
        ratingField.setBounds(60, 45, 50, 20);
        contentPane.add(ratingField);
        ratingField.setColumns(10);

        JLabel titleLabel = new JLabel("title");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBounds(0, 15, 50, 20);
        contentPane.add(titleLabel);

        JLabel ratingLabel = new JLabel("rating");
        ratingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ratingLabel.setBounds(0, 45, 50, 20);
        contentPane.add(ratingLabel);

        JLabel caloriesLabel = new JLabel("calories");
        caloriesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        caloriesLabel.setBounds(130, 45, 50, 20);
        contentPane.add(caloriesLabel);

        caloriesField = new JTextField();
        caloriesField.setColumns(10);
        caloriesField.setBounds(190, 45, 50, 20);
        contentPane.add(caloriesField);

        JLabel proteinLabel = new JLabel("protein");
        proteinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        proteinLabel.setBounds(260, 45, 50, 20);
        contentPane.add(proteinLabel);

        proteinField = new JTextField();
        proteinField.setColumns(10);
        proteinField.setBounds(320, 45, 50, 20);
        contentPane.add(proteinField);

        JLabel fatLabel = new JLabel("fat");
        fatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fatLabel.setBounds(0, 75, 49, 14);
        contentPane.add(fatLabel);

        fatField = new JTextField();
        fatField.setColumns(10);
        fatField.setBounds(60, 75, 50, 20);
        contentPane.add(fatField);

        JLabel sodiumLabel = new JLabel("sodium");
        sodiumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sodiumLabel.setBounds(130, 75, 50, 20);
        contentPane.add(sodiumLabel);

        sodiumField = new JTextField();
        sodiumField.setColumns(10);
        sodiumField.setBounds(190, 75, 50, 20);
        contentPane.add(sodiumField);

        JLabel priceLabel = new JLabel("price");
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setBounds(260, 75, 50, 20);
        contentPane.add(priceLabel);

        priceField = new JTextField();
        priceField.setColumns(10);
        priceField.setBounds(320, 75, 50, 20);
        contentPane.add(priceField);

    }
    public void addButtonListener(ActionListener actionListener){
        addButton.addActionListener(actionListener);
    }
    public String getTitle(){
        return titleField.getText();
    }
    public double getRating(){
        return Double.parseDouble(ratingField.getText());
    }
    public int getCalories(){
        return Integer.parseInt(caloriesField.getText());
    }
    public int getProtein(){
        return Integer.parseInt(proteinField.getText());
    }
    public int getFat(){
        return Integer.parseInt(fatField.getText());
    }
    public int getSodium(){
        return Integer.parseInt(sodiumField.getText());
    }
    public int getPrice(){
        return Integer.parseInt(priceField.getText());
    }
}
