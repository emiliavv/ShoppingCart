package pl.javamarkt.sorting;

import pl.javamarkt.Product;

public class NameAscendingStrategy implements ProductSortingStrategy {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getName().compareTo(p2.getName());
    }

    @Override
    public String getDescription() {
        return "Name ascending";
    }
}
