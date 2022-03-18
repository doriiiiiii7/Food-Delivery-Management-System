package business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private LocalDateTime orderDate;
    private int orderTotal;

    public Order(int orderID, int clientID, LocalDateTime orderDate){
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    public String toString(){
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        return "Order with ID " + orderID + " was placed by client with ID " + clientID + " on " + orderDate.format(dateTimeFormat)
                + ".\nThe order total is " + orderTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientID == order.clientID && orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, orderDate);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }
}
