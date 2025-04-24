package pl.javamarkt.promotion;

import org.junit.jupiter.api.Test;
import pl.javamarkt.Product;
import static org.junit.jupiter.api.Assertions.*;

public class OrderValueDiscountTest {

    @Test
    void shouldApplyDiscountWhenThresholdExceeded() {
        Product[] products = {
            new Product("001", "Product 1", 200.0),
            new Product("002", "Product 2", 150.0)
        };
        
        OrderValueDiscount discount = new OrderValueDiscount(300.0, 0.05);
        discount.execute(products);
        
        assertEquals(190.0, products[0].getDiscountPrice());
        assertEquals(142.5, products[1].getDiscountPrice());
    }

    @Test
    void shouldNotApplyDiscountWhenThresholdNotExceeded() {
        Product[] products = {
            new Product("001", "Product 1", 150.0),
            new Product("002", "Product 2", 100.0)
        };
        
        OrderValueDiscount discount = new OrderValueDiscount(300.0, 0.05);
        discount.execute(products);
        
        assertEquals(150.0, products[0].getDiscountPrice());
        assertEquals(100.0, products[1].getDiscountPrice());
    }

    @Test
    void shouldUndoDiscount() {
        Product[] products = {
            new Product("001", "Product 1", 200.0),
            new Product("002", "Product 2", 150.0)
        };
        
        OrderValueDiscount discount = new OrderValueDiscount(300.0, 0.05);
        discount.execute(products);
        discount.undo(products);
        
        assertEquals(200.0, products[0].getDiscountPrice());
        assertEquals(150.0, products[1].getDiscountPrice());
    }

    @Test
    void shouldProvideCorrectDescription() {
        OrderValueDiscount discount = new OrderValueDiscount(300.0, 0.05);
        assertEquals("5% zniżki przy zamówieniu powyżej 300,00 zł", discount.getDescription());
    }
}
