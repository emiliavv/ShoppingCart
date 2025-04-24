package pl.javamarkt.promotion;

import pl.javamarkt.Product;
import java.util.Arrays;
import java.util.Comparator;

public class BonusProductsPromotion implements PromotionCommand {
    private Product[] discountedProducts;
    private final int requiredProducts;
    private final int freeProducts;

    public BonusProductsPromotion(int requiredProducts, int freeProducts) {
        this.requiredProducts = requiredProducts;
        this.freeProducts = freeProducts;
        this.discountedProducts = new Product[0];
    }

    @Override
    public void execute(Product[] products) {
        if (products.length != requiredProducts) {
            return;
        }

        Product[] sortedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice));

        discountedProducts = Arrays.copyOf(sortedProducts, freeProducts);
        for (Product product : discountedProducts) {
            product.setDiscountPrice(0.0);
        }
    }

    @Override
    public void undo(Product[] products) {
        for (Product product : discountedProducts) {
            product.resetDiscount();
        }
        discountedProducts = new Product[0];
    }

    @Override
    public String getDescription() {
        return String.format("Przy zakupie %d produktów, %d najtańsze gratis", 
            requiredProducts, freeProducts);
    }
}
