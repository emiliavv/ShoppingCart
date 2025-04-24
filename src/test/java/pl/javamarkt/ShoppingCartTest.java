//package pl.javamarkt;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pl.javamarkt.promotion.OrderValueDiscount;
//import pl.javamarkt.promotion.PromotionCommand;
//import pl.javamarkt.promotion.ThirdProductFree;
//import java.util.Comparator;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ShoppingCartTest {
//    private ShoppingCart cart;
//
//    @BeforeEach
//    void setUp() {
//        cart = new ShoppingCart();
//    }
//
//    @Test
//    void shouldAddProducts() {
//        Product product = new Product("001", "Test Product", 100.0);
//        cart.addProduct(product);
//
//        Product[] products = cart.getProducts();
//        assertEquals(1, products.length);
//        assertEquals(product.getCode(), products[0].getCode());
//    }
//
//    @Test
//    void shouldSortProductsByPriceAndName() {
//        Product p1 = new Product("001", "A Product", 100.0);
//        Product p2 = new Product("002", "B Product", 100.0);
//        Product p3 = new Product("003", "C Product", 200.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//        cart.addProduct(p3);
//
//        Product[] products = cart.getProducts();
//        assertEquals(p3.getCode(), products[0].getCode());
//        assertEquals(p1.getCode(), products[1].getCode());
//        assertEquals(p2.getCode(), products[2].getCode());
//    }
//
//    @Test
//    void shouldFindCheapestAndMostExpensiveProducts() {
//        Product p1 = new Product("001", "Product 1", 100.0);
//        Product p2 = new Product("002", "Product 2", 200.0);
//        Product p3 = new Product("003", "Product 3", 300.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//        cart.addProduct(p3);
//
//        assertEquals(p1.getCode(), cart.findCheapestProduct().getCode());
//        assertEquals(p3.getCode(), cart.findMostExpensiveProduct().getCode());
//    }
//
//    @Test
//    void shouldFindNCheapestProducts() {
//        Product p1 = new Product("001", "Product 1", 100.0);
//        Product p2 = new Product("002", "Product 2", 200.0);
//        Product p3 = new Product("003", "Product 3", 300.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//        cart.addProduct(p3);
//
//        Product[] cheapest = cart.findNCheapestProducts(2);
//        assertEquals(2, cheapest.length);
//        assertEquals(p1.getCode(), cheapest[0].getCode());
//        assertEquals(p2.getCode(), cheapest[1].getCode());
//    }
//
//    @Test
//    void shouldCalculateTotalWithBestPromotionOrder() {
//        Product p1 = new Product("001", "Product 1", 200.0);
//        Product p2 = new Product("002", "Product 2", 200.0);
//        Product p3 = new Product("003", "Product 3", 100.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//        cart.addProduct(p3);
//
//        PromotionCommand promotionCommand = new PromotionCommand();
//        promotionCommand.addPromotion(new OrderValueDiscount(300.0, 0.05));
//        promotionCommand.addPromotion(new ThirdProductFree());
//
//        cart.setPromotionCommand(promotionCommand);
//
//        double total = cart.calculateTotal();
//        assertEquals(475.0, total, 0.01);
//    }
//
//    @Test
//    void shouldChangeSortingStrategy() {
//        Product p1 = new Product("001", "Z Product", 100.0);
//        Product p2 = new Product("002", "A Product", 100.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//
//        cart.setSortingStrategy(Comparator.comparing(Product::getName));
//
//        Product[] products = cart.getProducts();
//        assertEquals(p2.getCode(), products[0].getCode());
//        assertEquals(p1.getCode(), products[1].getCode());
//    }
//
//    @Test
//    void shouldApplyPromotionsInOptimalOrder() {
//        Product p1 = new Product("001", "Product 1", 200.0);
//        Product p2 = new Product("002", "Product 2", 200.0);
//        Product p3 = new Product("003", "Product 3", 100.0);
//
//        cart.addProduct(p1);
//        cart.addProduct(p2);
//        cart.addProduct(p3);
//
//        PromotionCommand promotionCommand = new PromotionCommand();
//        promotionCommand.addPromotion(new ThirdProductFree());
//        promotionCommand.addPromotion(new OrderValueDiscount(300.0, 0.05));
//
//        cart.setPromotionCommand(promotionCommand);
//
//        double total = cart.calculateTotal();
//
//        // Sprawdzamy, czy promocje są stosowane w optymalnej kolejności
//        // W tym przypadku, najpierw powinien być zastosowany ThirdProductFree,
//        // a następnie OrderValueDiscount na pozostałej kwocie
//        double expectedTotal = 475.0; // (200 + 200 + 0) * 0.95
//        assertEquals(expectedTotal, total, 0.01);
//    }
//}
