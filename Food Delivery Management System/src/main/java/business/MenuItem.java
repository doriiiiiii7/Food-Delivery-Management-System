package business;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Comparable<MenuItem>, Serializable {
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public MenuItem(String title, double rating, int calories, int protein, int fat, int sodium, int price){
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public String toString() {
        return title;
    }

    public int compareTo(MenuItem otherMenuItem){
        return this.title.compareTo(otherMenuItem.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Double.compare(menuItem.rating, rating) == 0 && calories == menuItem.calories && protein == menuItem.protein && fat == menuItem.fat && sodium == menuItem.sodium && price == menuItem.price && title.equals(menuItem.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, calories, protein, fat, sodium, price);
    }

    public abstract int computePrice();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
