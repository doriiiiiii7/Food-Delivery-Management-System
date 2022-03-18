package presentation;
import business.BaseProduct;
import business.DeliveryService;
import business.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class TablePopupMenu extends JFrame implements ActionListener {

    private JTable table;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemAddComponent;
    private JMenuItem menuItemGenerateComponent;
    private JMenuItem menuItemEdit;

    public TablePopupMenu() {
        super("JTable Popup Menu");

        String[] columnNames = new String[] {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        String[][] rowData = new String[DeliveryService.menuItems.size()][columnNames.length];
        TreeSet<MenuItem> auxiliarSet = new TreeSet<>();
        auxiliarSet.addAll(DeliveryService.menuItems);
        for(int i = 0; i < DeliveryService.menuItems.size(); i++){
            MenuItem menuItem = auxiliarSet.pollFirst();
            rowData[i][0] = menuItem.getTitle();
            rowData[i][1] = String.valueOf(menuItem.getRating());
            rowData[i][2] = String.valueOf(menuItem.getCalories());
            rowData[i][3] = String.valueOf(menuItem.getProtein());
            rowData[i][4] = String.valueOf(menuItem.getFat());
            rowData[i][5] = String.valueOf(menuItem.getSodium());
            rowData[i][6] = String.valueOf(menuItem.getPrice());
        }

        tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);

        popupMenu = new JPopupMenu();
        menuItemAdd = new JMenuItem("Add New Row");
        menuItemRemove = new JMenuItem("Remove Current Row");
        menuItemAddComponent = new JMenuItem("Add to Composed Item");
        menuItemGenerateComponent = new JMenuItem("Add Composed Item");
        menuItemEdit = new JMenuItem("Edit Current Row");

        menuItemAdd.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemAddComponent.addActionListener(this);
        menuItemGenerateComponent.addActionListener(this);
        menuItemEdit.addActionListener(this);

        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemAddComponent);
        popupMenu.add(menuItemGenerateComponent);
        popupMenu.add(menuItemEdit);

        table.setComponentPopupMenu(popupMenu);

        table.addMouseListener(new TableMouseListener(table));

        add(new JScrollPane(table));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuItemAdd) {
            addNewRow();
        } else if (menu == menuItemRemove) {
            removeCurrentRow();
        }else if(menu == menuItemAddComponent){
            addToComposedItem();
        }else if(menu == menuItemGenerateComponent){
            addComposedItem();
            selectedProducts.clear();
        }else if(menu == menuItemEdit){
            editCurrentRow();
        }
    }

    private void editCurrentRow(){
        int selectedRow = table.getSelectedRow();
        String[] info = new String[table.getColumnCount()];
        for(int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) tableModel.getValueAt(selectedRow, i);
        }
        EditItemView editItemView = new EditItemView(info);
        EditItemController controller = new EditItemController(editItemView, tableModel, selectedRow, info[0]);
        editItemView.setVisible(true);
    }

    private void addComposedItem(){
        AddItemView addItemView = new AddItemView("Add Composite Item Window");
        AddItemController controller = new AddItemController(addItemView, tableModel, selectedProducts);
        addItemView.setVisible(true);
    }

    TreeSet<MenuItem> selectedProducts = new TreeSet<>();
    private void addToComposedItem(){
        int selectedRow = table.getSelectedRow();
        String[] info = new String[table.getColumnCount()];
        for(int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) tableModel.getValueAt(selectedRow, i);
        }
        BaseProduct newItem = convertToItem(info);
        selectedProducts.add(newItem);
        System.out.println(newItem);
    }


    private BaseProduct convertToItem(String[] info){
        return new BaseProduct(info[0], Double.parseDouble(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]),
                Integer.parseInt(info[4]), Integer.parseInt(info[5]), Integer.parseInt(info[6]));
    }

    private void addNewRow() {
        AddItemView addItemView = new AddItemView("Add Item Window");
        AddItemController controller = new AddItemController(addItemView, tableModel);
        addItemView.setVisible(true);
    }

    private void removeCurrentRow() {
        int selectedRow = table.getSelectedRow();
        String toBeDeleted = (String)tableModel.getValueAt(selectedRow, 0);
        for(MenuItem menuItem: DeliveryService.menuItems){
            if(menuItem.getTitle().equals(toBeDeleted)) {
                DeliveryService.menuItems.remove(menuItem);
                tableModel.removeRow(selectedRow);
                break;
            }
        }
    }

    public JTable getTable() {
        return table;
    }

}