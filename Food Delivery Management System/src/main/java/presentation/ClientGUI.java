package presentation;

import business.*;
import business.MenuItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class ClientGUI extends JFrame implements ActionListener {

        private JTable table;
        private DefaultTableModel tableModel;
        private JPopupMenu popupMenu;
        private JMenuItem searchItem;
        private JMenuItem itemToOrder;
        private JMenuItem order;
        private DeliveryService deliveryService;
        private final String[] columnNames = new String[] {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};


        public ClientGUI(DeliveryService deliveryService) {
            super("Restaurant Menu");
            this.deliveryService = deliveryService;
            tableModel = new DefaultTableModel();
            table = new JTable(tableModel);
            setTable(DeliveryService.menuItems);
            popupMenu = new JPopupMenu();
            searchItem = new JMenuItem("Search for Product");
            itemToOrder = new JMenuItem("Add Product to Order");
            order = new JMenuItem("Place Order");

            searchItem.addActionListener(this);
            itemToOrder.addActionListener(this);
            order.addActionListener(this);

            popupMenu.add(searchItem);
            popupMenu.add(itemToOrder);
            popupMenu.add(order);

            table.setComponentPopupMenu(popupMenu);

            table.addMouseListener(new TableMouseListener(table));

            add(new JScrollPane(table));

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(640, 150);
            setLocationRelativeTo(null);
        }

        public void setTable(TreeSet<MenuItem> menuItems){
            String[][] rowData = new String[DeliveryService.menuItems.size()][columnNames.length];
            TreeSet<MenuItem> auxiliarSet = new TreeSet<>();
            auxiliarSet.addAll(menuItems);
            for(int i = 0; i < menuItems.size(); i++){
                MenuItem menuItem = auxiliarSet.pollFirst();
                rowData[i][0] = menuItem.getTitle();
                rowData[i][1] = String.valueOf(menuItem.getRating());
                rowData[i][2] = String.valueOf(menuItem.getCalories());
                rowData[i][3] = String.valueOf(menuItem.getProtein());
                rowData[i][4] = String.valueOf(menuItem.getFat());
                rowData[i][5] = String.valueOf(menuItem.getSodium());
                rowData[i][6] = String.valueOf(menuItem.getPrice());
            }
            tableModel.setDataVector(rowData, columnNames);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == searchItem) {
                searchItem();
            } else if (menu == itemToOrder) {
                addItemToOrder();
            }else if(menu == order){
                placeOrder();
            }
        }

       private void searchItem(){
            SearchWindow searchWindow = new SearchWindow();
            SearchWindowController controller = new SearchWindowController(searchWindow);
            searchWindow.setVisible(true);
       }

       ArrayList<MenuItem> itemsOrdered = new ArrayList<>();
       private void addItemToOrder(){
            int index = table.getSelectedRow();
            for(MenuItem menuItem: DeliveryService.menuItems){
                if(menuItem.getTitle().equals((String)table.getValueAt(index, 0))){
                    itemsOrdered.add(menuItem);
                    break;
                }
            }
       }

       private void placeOrder(){
           String username = LogInController.lastClientLoggedIn;
            int orderID = 0;
            int clientID = 0;

            for (Order order : DeliveryService.ordersInformation.keySet()) {
                if (order.getOrderID() > orderID)
                    orderID = order.getOrderID();
            }

            orderID++;

            for(User user: DeliveryService.users){
                if(user.getUsername().equals(username)){
                    clientID = ((Client) user).getClientID();
                }
            }

            deliveryService.createOrder(orderID, clientID, LocalDateTime.now(), itemsOrdered);
            itemsOrdered.clear();
       }

        public JTable getTable() {
            return table;
        }

        public class SearchWindow extends JFrame{
            private JPanel contentPane;
            private JTextField stringField;
            private JTextField ratingField;
            private JTextField caloriesField;
            private JTextField proteinsField;
            private JTextField fatField;
            private JTextField sodiumField;
            private JTextField priceField;
            private JButton searchButton;

            public SearchWindow() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 400, 340);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblNewLabel = new JLabel("Complete the fields you want to search by");
                lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                lblNewLabel.setBounds(10, 10, 365, 20);
                contentPane.add(lblNewLabel);

                JLabel titleLabel = new JLabel("The title must contain keyword:");
                titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                titleLabel.setBounds(10, 50, 260, 20);
                contentPane.add(titleLabel);

                stringField = new JTextField();
                stringField.setBounds(280, 50, 90, 20);
                contentPane.add(stringField);
                stringField.setColumns(10);

                JLabel ratingLabel = new JLabel("The rating must be equal or greater than:");
                ratingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                ratingLabel.setBounds(10, 80, 260, 20);
                contentPane.add(ratingLabel);

                ratingField = new JTextField();
                ratingField.setColumns(10);
                ratingField.setBounds(280, 80, 90, 20);
                contentPane.add(ratingField);

                JLabel caloriesLabel = new JLabel("The calories must be equal or greater than:");
                caloriesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                caloriesLabel.setBounds(10, 110, 260, 20);
                contentPane.add(caloriesLabel);

                caloriesField = new JTextField();
                caloriesField.setColumns(10);
                caloriesField.setBounds(280, 110, 90, 20);
                contentPane.add(caloriesField);

                JLabel proteinsLabel = new JLabel("The proteins must be equal or greater than:");
                proteinsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                proteinsLabel.setBounds(10, 140, 260, 20);
                contentPane.add(proteinsLabel);

                proteinsField = new JTextField();
                proteinsField.setColumns(10);
                proteinsField.setBounds(280, 140, 90, 20);
                contentPane.add(proteinsField);

                JLabel fatLabel = new JLabel("The fat must be equal or greater than:");
                fatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fatLabel.setBounds(10, 170, 260, 20);
                contentPane.add(fatLabel);

                fatField = new JTextField();
                fatField.setColumns(10);
                fatField.setBounds(280, 170, 90, 20);
                contentPane.add(fatField);

                JLabel sodiumLabel = new JLabel("The sodium must be equal or greater than:");
                sodiumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                sodiumLabel.setBounds(10, 200, 260, 20);
                contentPane.add(sodiumLabel);

                sodiumField = new JTextField();
                sodiumField.setColumns(10);
                sodiumField.setBounds(280, 200, 90, 20);
                contentPane.add(sodiumField);

                JLabel priceLabel = new JLabel("The price must be equal or greater than:");
                priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                priceLabel.setBounds(10, 230, 260, 20);
                contentPane.add(priceLabel);

                priceField = new JTextField();
                priceField.setColumns(10);
                priceField.setBounds(280, 230, 90, 20);
                contentPane.add(priceField);

                searchButton = new JButton("Search");
                searchButton.setBounds(145, 260, 90, 25);
                contentPane.add(searchButton);
                setTitle("Employee Window");
            }

            public void addSearchButtonListener(ActionListener actionListener){
                searchButton.addActionListener(actionListener);
            }

            public String getString(){
                if(stringField.getText().isEmpty())
                    return "";
                else
                    return stringField.getText();
            }

            public String getPrice(){
                if(priceField.getText().isEmpty())
                    return "0";
                else
                    return priceField.getText();
            }

            public String getProteins(){
                if(proteinsField.getText().isEmpty())
                    return "0";
                else
                    return proteinsField.getText();
            }

            public String getFat(){
                if(fatField.getText().isEmpty())
                    return "0";
                else
                    return fatField.getText();
            }

            public String getCalories(){
                if(caloriesField.getText().isEmpty())
                    return "0";
                else
                    return caloriesField.getText();
            }

            public String getSodium(){
                if(sodiumField.getText().isEmpty())
                    return "0";
                else
                    return sodiumField.getText();
            }

            public String getRating(){
                if(ratingField.getText().isEmpty())
                    return "0";
                else
                    return ratingField.getText();
            }
        }

        public class SearchWindowController{
           private SearchWindow searchWindow;
           public SearchWindowController(SearchWindow searchWindow){
               this.searchWindow = searchWindow;
               searchWindow.addSearchButtonListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       String substring = searchWindow.getString();
                       double rating = Double.parseDouble(searchWindow.getRating());
                       int calories = Integer.parseInt(searchWindow.getCalories());
                       int proteins = Integer.parseInt(searchWindow.getProteins());
                       int fat = Integer.parseInt(searchWindow.getFat());
                       int sodium = Integer.parseInt(searchWindow.getSodium());
                       int price = Integer.parseInt(searchWindow.getPrice());

                       TreeSet<MenuItem> menuItemList = (TreeSet<MenuItem>)deliveryService.searchForProduct(substring, rating, calories, proteins,
                                                                                     fat, sodium, price);
                        setTable(menuItemList);
                   }
               });
           }
        }
}
