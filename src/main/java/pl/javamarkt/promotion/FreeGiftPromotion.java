package pl.javamarkt.promotion;

import pl.javamarkt.Product;

public class FreeGiftPromotion implements PromotionCommand {
    private final double threshold;
    private final Product gift;
    private boolean giftAdded;

    public FreeGiftPromotion(double threshold, Product gift) {
        this.threshold = threshold;
        this.gift = gift;
        this.giftAdded = false;
    }

    @Override
    public void execute(Product[] products) {
        if (giftAdded) {
            return;
        }

        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }

        if (total > threshold) {
            gift.setDiscountPrice(0.0);
            giftAdded = true;
        }
    }

    @Override
    public void undo(Product[] products) {
        if (giftAdded) {
            gift.resetDiscount();
            giftAdded = false;
        }
    }

    @Override
    public String getDescription() {
        return String.format("Darmowy %s przy zakupach powyżej %.2f zł", gift.getName(), threshold);
    }

    public Product getGift() {
        return gift;
    }
}
