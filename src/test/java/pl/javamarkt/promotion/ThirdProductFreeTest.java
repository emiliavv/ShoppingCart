package pl.javamarkt.promotion;

import org.junit.jupiter.api.Test;
import pl.javamarkt.Product;
import static org.junit.jupiter.api.Assertions.*;

public class ThirdProductFreeTest {

    @Test
    void shouldMakeThirdProductFree() {
        Product[] products = {
            new Product("001", "Product 1", 300.0),
            new Product("002", "Product 2", 200.0),
            new Product("003", "Product 3", 100.0)
        };
        
        ThirdProductFree promotion = new ThirdProductFree();
        promotion.execute(products);
        
        assertEquals(300.0, products[0].getDiscountPrice());
        assertEquals(200.0, products[1].getDiscountPrice());
        assertEquals(0.0, products[2].getDiscountPrice());
    }

    @Test
    void shouldNotApplyToLessThanThreeProducts() {
        Product[] products = {
            new Product("001", "Product 1", 300.0),
            new Product("002", "Product 2", 200.0)
        };
        
        ThirdProductFree promotion = new ThirdProductFree();
        promotion.execute(products);
        
        assertEquals(300.0, products[0].getDiscountPrice());
        assertEquals(200.0, products[1].getDiscountPrice());
    }

    @Test
    void shouldMakeEveryThirdProductFree() {
        Product[] products = {
            new Product("001", "Product 1", 600.0),
            new Product("002", "Product 2", 500.0),
            new Product("003", "Product 3", 400.0),
            new Product("004", "Product 4", 300.0),
            new Product("005", "Product 5", 200.0),
            new Product("006", "Product 6", 100.0)
        };
        
        ThirdProductFree promotion = new ThirdProductFree();
        promotion.execute(products);
        
        assertEquals(600.0, products[0].getDiscountPrice());
        assertEquals(500.0, products[1].getDiscountPrice());
        assertEquals(0.0, products[2].getDiscountPrice());
        assertEquals(300.0, products[3].getDiscountPrice());
        assertEquals(200.0, products[4].getDiscountPrice());
        assertEquals(0.0, products[5].getDiscountPrice());
    }

    @Test
    void shouldUndoPromotion() {
        Product[] products = {
            new Product("001", "Product 1", 300.0),
            new Product("002", "Product 2", 200.0),
            new Product("003", "Product 3", 100.0)
        };
        
        ThirdProductFree promotion = new ThirdProductFree();
        promotion.execute(products);
        promotion.undo(products);
        
        assertEquals(300.0, products[0].getDiscountPrice());
        assertEquals(200.0, products[1].getDiscountPrice());
        assertEquals(100.0, products[2].getDiscountPrice());
    }
}
