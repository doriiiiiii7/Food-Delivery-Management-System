package presentation;

import business.BaseProduct;
import business.CompositeProduct;
import business.DeliveryService;
import business.MenuItem;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class AddItemController {
    private AddItemView addItemView;
    private DefaultTableModel table;

    public AddItemController(AddItemView addItemView, DefaultTableModel table, TreeSet<MenuItem> set){
        this.addItemView = addItemView;
        this.table = table;
        addItemView.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = addItemView.getTitle();
                double rating = addItemView.getRating();
                int calories = addItemView.getCalories();
                int protein = addItemView.getProtein();
                int fat = addItemView.getFat();
                int sodium = addItemView.getSodium();
                int price = addItemView.getPrice();
                CompositeProduct compositeProduct= new CompositeProduct(title, rating, calories, protein, fat, sodium, price);
                compositeProduct.setProducts(set);
                String[] data = new String[table.getColumnCount()];
                data[0] = addItemView.getTitle();
                data[1] = String.valueOf(rating);
                data[2] = String.valueOf(calories);
                data[3] = String.valueOf(protein);
                data[4] = String.valueOf(fat);
                data[5] = String.valueOf(sodium);
                data[6] = String.valueOf(price);
                table.addRow(data);
                DeliveryService.menuItems.add(compositeProduct);
            }
        });

    }

    public AddItemController(AddItemView addItemView, DefaultTableModel table){
        this.addItemView = addItemView;
        this.table = table;
        addItemView.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = addItemView.getTitle();
                double rating = addItemView.getRating();
                int calories = addItemView.getCalories();
                int protein = addItemView.getProtein();
                int fat = addItemView.getFat();
                int sodium = addItemView.getSodium();
                int price = addItemView.getPrice();
                BaseProduct baseProduct = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
                String[] data = new String[table.getColumnCount()];
                data[0] = addItemView.getTitle();
                data[1] = String.valueOf(addItemView.getRating());
                data[2] = String.valueOf(addItemView.getCalories());
                data[3] = String.valueOf(addItemView.getProtein());
                data[4] = String.valueOf(addItemView.getFat());
                data[5] = String.valueOf(addItemView.getSodium());
                data[6] = String.valueOf(addItemView.getPrice());
                table.addRow(data);
                DeliveryService.menuItems.add(baseProduct);
            }
        });
    }
}
