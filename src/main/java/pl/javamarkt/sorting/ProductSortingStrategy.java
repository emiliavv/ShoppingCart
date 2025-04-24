package pl.javamarkt.sorting;

import pl.javamarkt.Product;
import java.util.Comparator;

public interface ProductSortingStrategy extends Comparator<Product> {
    String getDescription();
}
