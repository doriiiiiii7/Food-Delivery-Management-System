package presentation;

import business.BaseProduct;
import business.CompositeProduct;
import business.DeliveryService;
import business.MenuItem;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItemController {
    private EditItemView editItemView;
    private DefaultTableModel tabel;

    public EditItemController(EditItemView editItemView, DefaultTableModel tabel, int index, String oldItem){
        this.editItemView = editItemView;
        this.tabel = tabel;
        editItemView.editButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean simple = false;
                for(MenuItem menuItem: DeliveryService.menuItems){
                    if(menuItem.getTitle().equals(oldItem)){
                        if(menuItem instanceof BaseProduct)
                            simple = true;
                        DeliveryService.menuItems.remove(menuItem);
                        break;
                    }
                }
                String title = editItemView.getTitle();
                double rating = editItemView.getRating();
                int calories = editItemView.getCalories();
                int protein = editItemView.getProtein();
                int fat = editItemView.getFat();
                int sodium = editItemView.getSodium();
                int price = editItemView.getPrice();

                if(simple) {
                    BaseProduct baseProduct = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
                    DeliveryService.menuItems.add(baseProduct);
                }else{
                    CompositeProduct compositeProduct = new CompositeProduct(title, rating, calories, protein, fat, sodium, price);
                    DeliveryService.menuItems.add(compositeProduct);
                }

                String[] data = new String[tabel.getColumnCount()];
                data[0] = title;
                data[1] = String.valueOf(editItemView.getRating());
                data[2] = String.valueOf(editItemView.getCalories());
                data[3] = String.valueOf(editItemView.getProtein());
                data[4] = String.valueOf(editItemView.getFat());
                data[5] = String.valueOf(editItemView.getSodium());
                data[6] = String.valueOf(editItemView.getPrice());
                for(int i = 0; i < tabel.getColumnCount(); i++)
                    tabel.setValueAt(data[i], index, i);
            }
        });
    }
}
