package pl.javamarkt.promotion;

import pl.javamarkt.Product;
import java.util.Arrays;
import java.util.Comparator;

public class ThirdProductFree implements PromotionCommand {
    private Product[] discountedProducts;

    @Override
    public void execute(Product[] products) {
        if (products.length < 3) {
            return;
        }

        Product[] sortedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice));

        int freeProductsCount = products.length / 3;
        discountedProducts = Arrays.copyOf(sortedProducts, freeProductsCount);
        
        for (Product product : discountedProducts) {
            product.setDiscountPrice(0.0);
        }
    }

    @Override
    public void undo(Product[] products) {
        if (discountedProducts != null) {
            for (Product product : discountedProducts) {
                product.resetDiscount();
            }
            discountedProducts = null;
        }
    }

    @Override
    public String getDescription() {
        return "Jeden najtańszy produkt gratis na każde trzy produkty w koszyku";
    }
}
