package pl.javamarkt.promotion;

import pl.javamarkt.Product;

public class OrderValueDiscount implements PromotionCommand {
    private final double threshold;
    private final double discountPercentage;

    public OrderValueDiscount(double threshold, double discountPercentage) {
        this.threshold = threshold;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void execute(Product[] products) {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }

        if (total > threshold) {
            for (Product product : products) {
                double currentPrice = product.getPrice();
                product.setDiscountPrice(currentPrice * (1 - discountPercentage));
            }
        }
    }

    @Override
    public void undo(Product[] products) {
        for (Product product : products) {
            product.resetDiscount();
        }
    }

    @Override
    public String getDescription() {
        return String.format("%.0f%% zniżki przy zamówieniu powyżej %.2f zł", discountPercentage * 100, threshold);
    }
}
