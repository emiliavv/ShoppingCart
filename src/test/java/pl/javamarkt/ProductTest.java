package pl.javamarkt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void shouldCreateProductWithCorrectValues() {
        Product product = new Product("001", "Test Product", 100.0);
        
        assertEquals("001", product.getCode());
        assertEquals("Test Product", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(100.0, product.getDiscountPrice());
    }

    @Test
    void shouldSetDiscountPrice() {
        Product product = new Product("001", "Test Product", 100.0);
        product.setDiscountPrice(80.0);
        
        assertEquals(80.0, product.getDiscountPrice());
        assertEquals(100.0, product.getPrice());
    }

    @Test
    void shouldResetDiscount() {
        Product product = new Product("001", "Test Product", 100.0);
        product.setDiscountPrice(80.0);
        product.resetDiscount();
        
        assertEquals(100.0, product.getDiscountPrice());
    }
}
