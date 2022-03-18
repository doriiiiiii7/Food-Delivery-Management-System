package business;

import java.util.TreeSet;

public class CompositeProduct extends MenuItem{
    private TreeSet<MenuItem> products;

    public CompositeProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price){
        super(title, rating, calories, protein, fat, sodium, price);
        products = new TreeSet<>();
    }

    @Override
    public int computePrice() {
        return getPrice();
    }

    public TreeSet<MenuItem> getProducts() {
        return products;
    }

    public void setProducts(TreeSet<MenuItem> products) {
        this.products = products;
    }

    public String toString(){
        return products.toString();
    }
}
