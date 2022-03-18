package business;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Tirsogoiu Dorina-Mihaela, grupa 302210
 * @since 16.05.2021
 */

public interface IDeliveryServiceProcessing {
    /**
     * Imports data from .csv file into products array
     * @throws FileNotFoundException
     * @pre importFile exists
     * @post products array is not empty
     */
    void importProducts() throws FileNotFoundException;

    /**
     * Adds a product in the products array
     * @param newMenuItem the product that should be added
     * @pre product value is not null
     * @post product exists now in array
     */
    void addProduct(MenuItem newMenuItem);

    /**
     * Edites a product in the products array
     * @param toEditProduct the product that should be edited
     * @param editedProduct the product after editing
     * @pre product to edit and the new value for it are not null
     * @post the product title should be equal to the changed one
     */
    void editProduct(MenuItem toEditProduct, MenuItem editedProduct);

    /**
     * Deletes a product in the products array
     * @param toDeleteProduct the product that should be deleted
     * @pre product is not null
     * @post products does not exist in the array anymore
     */
    void deleteProduct(MenuItem toDeleteProduct);

    /**
     * Generates a report of orders placed between startHour and endHour
     * @param startHour start hour of the orders
     * @param endHour   end hour of the orders
     * @pre startHour <= endHour
     * @post fileWriter is closed
     */
    void generateTimeReport(int startHour, int endHour);

    /**
     * Generates a report of products ordered more than a specified amount of times
     * @param specifiedNumber the specified amount
     * @pre specifiedNumber >= 0
     * @post fileWriter is closed
     */
    void generateProductsReport(int specifiedNumber);

    /**
     * Generates a report of clients that ordered more than a specified value and the orders'
     * value was greater than a specified amount
     * @param specifiedValue
     * @param totalSpecifiedValue
     * @pre specifiedValue >=0 && totalSpecifiedValue >= 0
     * @post fileWriter is closed
     */
    void generateClientsReport(int specifiedValue, int totalSpecifiedValue);

    /**
     * Generates a report of products ordered in a specified day and the number of time they were ordered
     * @param date the date the products are ordered
     * @pre date is not empty
     * @post fileWriter is closed
     */
    void generateProductsWithinSpecifiedDayReport(String date);

    /**
     * Creates an order
     * @param orderID the id of the order
     * @param clientID the id of the client that placed the order
     * @param dateTime the date and time when the order was placed
     * @param itemsOrdered the ordered products
     * @pre itemsOrdered is not empty
     * @post order has been added to order map
     */
    void createOrder(int orderID, int clientID, LocalDateTime dateTime, ArrayList<MenuItem> itemsOrdered);

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
    TreeSet<MenuItem> searchForProduct(String substring, double rating, int calories, int protein, int fat, int sodium, int price);

}
