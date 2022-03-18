package business;

public class BaseProduct extends MenuItem{

    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price){
        super(title, rating, calories, protein, fat, sodium, price);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int computePrice() {
        return getPrice();
    }
}
