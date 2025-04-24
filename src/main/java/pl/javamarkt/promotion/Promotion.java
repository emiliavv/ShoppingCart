package pl.javamarkt.promotion;

import pl.javamarkt.Product;

public interface Promotion {
    void apply(Product[] products);
    String getDescription();
}
