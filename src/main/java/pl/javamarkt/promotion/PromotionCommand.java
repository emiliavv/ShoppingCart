package pl.javamarkt.promotion;

import pl.javamarkt.Product;

public interface PromotionCommand {
    void execute(Product[] products);
    void undo(Product[] products);
    String getDescription();
}
