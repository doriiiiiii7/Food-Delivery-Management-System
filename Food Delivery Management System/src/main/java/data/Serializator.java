package data;

import business.DeliveryService;
import business.MenuItem;
import business.Order;
import business.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Serializator {
    public static void serialize(){
        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream("users");
            ObjectOutputStream objectOutputStream1
                    = new ObjectOutputStream(fileOutputStream1);

            objectOutputStream1.writeObject(DeliveryService.users);

            FileOutputStream fileOutputStream2 = new FileOutputStream("orders");
            ObjectOutputStream objectOutputStream2
                    = new ObjectOutputStream(fileOutputStream2);

            objectOutputStream2.writeObject(DeliveryService.ordersInformation);

            FileOutputStream fileOutputStream3 = new FileOutputStream("menuItems");
            ObjectOutputStream objectOutputStream3
                    = new ObjectOutputStream(fileOutputStream3);

            objectOutputStream3.writeObject(DeliveryService.menuItems);

            objectOutputStream1.close();
            fileOutputStream1.close();
            objectOutputStream2.close();
            fileOutputStream2.close();
            objectOutputStream3.close();
            fileOutputStream3.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deserialize(){
        try {
            FileInputStream fileInputStream1 = new FileInputStream("users");
            ObjectInputStream objectInputStream1
                    = new ObjectInputStream(fileInputStream1);

            ArrayList<User> arrayList
                    = (ArrayList<User>) objectInputStream1.readObject();
            DeliveryService.users.clear();
            DeliveryService.users.addAll(arrayList);

            FileInputStream fileInputStream2 = new FileInputStream("menuItems");
            ObjectInputStream objectInputStream2
                    = new ObjectInputStream(fileInputStream2);

            TreeSet<MenuItem> menuItems = (TreeSet<MenuItem>) objectInputStream2.readObject();
            DeliveryService.menuItems.clear();
            DeliveryService.menuItems.addAll(menuItems);

            FileInputStream fileInputStream3 = new FileInputStream("orders");
            ObjectInputStream objectInputStream3
                    = new ObjectInputStream(fileInputStream3);

            HashMap<Order, ArrayList<MenuItem>> orders
                    = (HashMap<Order, ArrayList<MenuItem>>) objectInputStream3.readObject();
            DeliveryService.ordersInformation.clear();
            DeliveryService.ordersInformation.putAll(orders);

            fileInputStream1.close();
            objectInputStream1.close();
            fileInputStream2.close();
            objectInputStream2.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
