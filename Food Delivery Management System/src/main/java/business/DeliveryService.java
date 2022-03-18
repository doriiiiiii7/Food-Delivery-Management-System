package business;
import data.FileWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Tirsogoiu Dorina-Mihaela, grupa 302210
 * @since 16.05.2021
 */

public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    public static TreeSet<MenuItem> menuItems = new TreeSet<>();;
    public static HashMap<Order, ArrayList<MenuItem>> ordersInformation = new HashMap<>();
    public static ArrayList<User> users = new ArrayList<>();

    static {
        User admin = new User("admin", "admin", "ADMIN");
        User employee = new User("employee", "employee", "EMPLOYEE");
        users.add(admin);
        users.add(employee);
    }
    public static Function<String, BaseProduct> mapToBaseProduct = (line) -> {
        String[] p = line.split(",");
        return new BaseProduct(p[0].replaceAll("\"", ""), Double.parseDouble(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]),
                Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]));
    };

    /**
     * Imports data from .csv file into products array
     * @throws FileNotFoundException
     * @pre importFile exists
     * @post products array is not empty
     */
    @Override
    public void importProducts() throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("products.csv"));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        TreeSet<BaseProduct> baseProducts = br.lines()
                .skip(1)
                .map(mapToBaseProduct)
                .collect(Collectors.toCollection(TreeSet::new));
        menuItems.addAll(baseProducts);
    }

    /**
     * Adds a product in the products array
     * @param newMenuItem the product that should be added
     * @pre product value is not null
     * @post product exists now in array
     */
    @Override
    public void addProduct(MenuItem newMenuItem) {
        assert newMenuItem != null;
        menuItems.add(newMenuItem);
        assert menuItems.contains(newMenuItem);
        assert isWellFormed();
    }

    /**
     * Edites a product in the products array
     * @param toEditProduct the product that should be edited
     * @param editedProduct the product after editing
     * @pre product to edit and the new value for it are not null
     * @post the product title should be equal to the changed one
     */
    @Override
    public void editProduct(MenuItem toEditProduct, MenuItem editedProduct) {
        assert toEditProduct != null && editedProduct != null;
        String newTitle = "";
        for(MenuItem mi: menuItems){
            if(mi.compareTo(toEditProduct) == 0){
                mi = editedProduct;
                newTitle = mi.getTitle();
            }
        }
        assert newTitle.equals(editedProduct.getTitle());
        assert isWellFormed();
    }

    /**
     * Deletes a product in the products array
     * @param toDeleteProduct the product that should be deleted
     * @pre product is not null
     * @post products does not exist in the array anymore
     */
    @Override
    public void deleteProduct(MenuItem toDeleteProduct) {
        assert toDeleteProduct != null;
        menuItems.remove(toDeleteProduct);
        assert !menuItems.contains(toDeleteProduct);
        assert isWellFormed();
    }

    /**
     * Generates a report of orders placed between startHour and endHour
     * @param startHour start hour of the orders
     * @param endHour   end hour of the orders
     * @pre startHour <= endHour
     * @post fileWriter is closed
     */
    @Override
    public void generateTimeReport(int startHour, int endHour) {
        assert startHour <= endHour;
        AtomicReference<String> text = new AtomicReference<>("Orders made bewteen " + startHour + " and " + endHour + " are:\n");

        ordersInformation.entrySet().stream()
                                    .filter(e -> e.getKey().getOrderDate().getHour() >= startHour)
                                    .filter(e -> e.getKey().getOrderDate().getHour() < endHour)
                                    .forEach(o -> {
                                        text.getAndSet(text + o.getKey().toString() + "\n");
                                    });

        FileWriter fileWriter = new FileWriter("TimeReport.txt");
        fileWriter.write(text.get());
        fileWriter.close();
        assert fileWriter.isClosed();
        assert isWellFormed();
    }

    /**
     * Generates a report of products ordered more than a specified amount of times
     * @param specifiedNumber the specified amount
     * @pre specifiedNumber >= 0
     * @post fileWriter is closed
     */
    @Override
    public void generateProductsReport(int specifiedNumber) {
        assert specifiedNumber >= 0;
        AtomicReference<String> text = new AtomicReference<>("Products ordered more than " + specifiedNumber + " times are:\n");
        ArrayList<String> products = new ArrayList<>();
        for(ArrayList<MenuItem> list: ordersInformation.values()) {
            for (MenuItem menuItem : list) {
                products.add(menuItem.getTitle());
            }
        }

      products.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream().filter(s -> s.getValue() > specifiedNumber)
            .forEach(set ->{
                text.getAndSet(text + set.getKey() + "\n");
            });

        FileWriter fileWriter = new FileWriter("ProductsReport.txt");
        fileWriter.write(text.get());
        fileWriter.close();
        assert fileWriter.isClosed();
        assert isWellFormed();
    }

    /**
     * Generates a report of clients that ordered more than a specified value and the orders'
     * value was greater than a specified amount
     * @param specifiedNumber
     * @param totalSpecifiedNumber
     * @pre specifiedValue >=0 && totalSpecifiedValue >= 0
     * @post fileWriter is closed
     */
    @Override
    public void generateClientsReport(int specifiedNumber, int totalSpecifiedNumber) {
        assert specifiedNumber >=0 && totalSpecifiedNumber >=0;
        AtomicReference<String> text = new AtomicReference<>("The clients that have ordered more than " + specifiedNumber + " times and the value of the order was " +
                "more than " + totalSpecifiedNumber + " are:\n");

        List<Integer> ids = ordersInformation.keySet().stream()
                                                                .filter(o -> o.getOrderTotal() > totalSpecifiedNumber)
                                                                .map(Order::getClientID)
                                                                .collect(Collectors.toList());
        List<Integer> ids2 = ids.stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().stream().filter(c -> c.getValue() > specifiedNumber)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
        ids2.stream()
                .forEach(cId -> {
                    users.stream().filter(c -> c instanceof Client)
                                .filter(c -> ((Client) c).getClientID() == cId.intValue())
                                .forEach(validC -> {
                                    text.getAndSet(text + ((Client) validC).toString() + "\n");
                                });
                });

        FileWriter fileWriter = new FileWriter("ClientsReport.txt");
        fileWriter.write(text.get());
        fileWriter.close();
        assert fileWriter.isClosed();
        assert isWellFormed();
    }


    /**
     * Generates a report of products ordered in a specified day and the number of time they were ordered
     * @param date the date the products are ordered
     * @pre date is not empty
     * @post fileWriter is closed
     */
    @Override
    public void generateProductsWithinSpecifiedDayReport(String date) {
        assert !date.isEmpty();
        AtomicReference<String> text = new AtomicReference<>("The products ordered within the day of " + date + " are:\n");

        ArrayList<String> products = new ArrayList<>();
        for(Order order: ordersInformation.keySet()) {
            if(order.getOrderDate().toLocalDate().toString().equals(date))
                for (MenuItem menuItem : ordersInformation.get(order))
                    products.add(menuItem.getTitle());
        }

        products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .forEach(set ->{
                    text.getAndSet(text + set.getKey() + " has been ordered " + set.getValue() + "times.\n");
                });


        FileWriter fileWriter = new FileWriter("ProductsWithinSpecifiedDayReport.txt");
        fileWriter.write(text.get());
        fileWriter.close();
        assert fileWriter.isClosed();
        assert isWellFormed();
    }

    /**
     * Creates an order
     * @param orderID the id of the order
     * @param clientID the id of the client that placed the order
     * @param dateTime the date and time when the order was placed
     * @param itemsOrdered the ordered products
     * @pre itemsOrdered is not empty
     * @post order has been added to order map
     */
    @Override
    public void createOrder(int orderID, int clientID, LocalDateTime dateTime, ArrayList<MenuItem> itemsOrdered) {
        assert !itemsOrdered.isEmpty();
        int orderTotal = 0;
        for(MenuItem menuItem: itemsOrdered){
            orderTotal += menuItem.computePrice();
        }
        Order newOrder = new Order(orderID, clientID, dateTime);
        newOrder.setOrderTotal(orderTotal);

        ArrayList<MenuItem> items = new ArrayList<>();
        items.addAll(itemsOrdered);
        ordersInformation.put(newOrder, items);
        String text = "";
        int cnt = 0;
        for(Order order: ordersInformation.keySet()){
            cnt++;
            text += "Order " + cnt + "\n" + order.toString() + "\nOrdered items are:\n" + items.toString() + "\n";
        }
        FileWriter fileWriter = new FileWriter("Order" + String.valueOf(orderID) + ".txt");
        fileWriter.write(newOrder.toString() + "\nOrdered items are:\n" + items.toString() + "\n");
        fileWriter.close();
        setChanged();
        notifyObservers(text);
        assert ordersInformation.containsKey(newOrder);
        assert isWellFormed();
    }

    /**
     * Searches the products that meat the given criteria
     * @param substring the substring that the title should contain
     * @param rating the value the rating should be greater than
     * @param calories the value the calories should be greater than
     * @param protein the value the protein should be greater than
     * @param fat the value the fat should be greater than
     * @param sodium the value the sodium should be greater than
     * @param price the value the price should be greater than
     * @return the filtered products
     * @pre rating >= 0
     * @post filtered products set is not null
     */
    @Override
    public TreeSet<MenuItem> searchForProduct(String substring, double rating, int calories, int protein, int fat, int sodium, int price) {
        assert rating >=0;
        TreeSet<MenuItem> filteredItems = menuItems.stream()
                .filter(menuItem -> menuItem.getTitle().toLowerCase().contains(substring.toLowerCase()))
                .filter(menuItem -> menuItem.getRating() >= rating)
                .filter(menuItem -> menuItem.getCalories() >= calories )
                .filter(menuItem -> menuItem.getProtein() >= protein)
                .filter(menuItem -> menuItem.getFat() >= fat)
                .filter(menuItem -> menuItem.getSodium() >= sodium)
                .filter(menuItem -> menuItem.getPrice() >= price)
                .collect(Collectors.toCollection(TreeSet::new));
        assert !filteredItems.isEmpty();
        assert isWellFormed();
        return filteredItems;
    }

    public TreeSet<MenuItem> getBaseProducts() {
        return menuItems;
    }

    public void setBaseProducts(TreeSet<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrdersInformation() {
        return ordersInformation;
    }

    public void setOrdersInformation(HashMap<Order, ArrayList<MenuItem>> ordersInformation) {
        this.ordersInformation = ordersInformation;
    }

    public boolean isWellFormed(){
        return true;
    }
}
