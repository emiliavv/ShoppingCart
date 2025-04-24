package pl.javamarkt.sorting;

import pl.javamarkt.Product;

public class PriceAscendingStrategy implements ProductSortingStrategy {
    @Override
    public int compare(Product p1, Product p2) {
        return Double.compare(p1.getPrice(), p2.getPrice());
    }

    @Override
    public String getDescription() {
        return "Price ascending";
    }
}
