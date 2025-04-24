package pl.javamarkt.sorting;

import pl.javamarkt.Product;

public class PriceDescendingNameAscendingStrategy implements ProductSortingStrategy {
    @Override
    public int compare(Product p1, Product p2) {
        int priceCompare = Double.compare(p2.getPrice(), p1.getPrice());
        if (priceCompare != 0) {
            return priceCompare;
        }
        return p1.getName().compareTo(p2.getName());
    }

    @Override
    public String getDescription() {
        return "Price descending, name ascending";
    }
}
