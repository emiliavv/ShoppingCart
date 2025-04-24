package pl.javamarkt;

import pl.javamarkt.promotion.OrderValueDiscount;
import pl.javamarkt.promotion.ThirdProductFree;
import pl.javamarkt.promotion.FreeGiftPromotion;
import pl.javamarkt.sorting.NameAscendingStrategy;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Klasa uruchamiająca przykładową logikę działania koszyka zakupowego.
 * 
 * @author [Twoje imię i nazwisko]
 */
public class Main {
    /**
     * Główna metoda programu, uruchamiająca przykładową logikę koszyka zakupowego.
     * 
     * @param args tablica argumentów wejściowych
     */
    public static void main(String[] args) {
        // Tworzy nowy koszyk
        ShoppingCart cart = new ShoppingCart();

        // Dodaje przykładowe produkty do koszyka
        cart.addProduct(new Product("001", "Laptop", 3500.0));
        cart.addProduct(new Product("002", "Mysz", 150.0));
        cart.addProduct(new Product("003", "Klawiatura", 250.0));
        cart.addProduct(new Product("004", "Monitor", 1200.0));
        cart.addProduct(new Product("005", "Słuchawki", 300.0));
        cart.addProduct(new Product("006", "Podkładka", 50.0));
        cart.addProduct(new Product("007", "Kamera", 400.0));
        cart.addProduct(new Product("008", "Głośniki", 200.0));
        cart.addProduct(new Product("009", "USB", 80.0));

        // Wyświetla produkty w koszyku, posortowane domyślnie po cenie malejąco
        System.out.println("Produkty w koszyku (sortowane domyślnie po cenie malejąco):");
        for (Product product : cart.getProducts()) {
            System.out.printf("%s - %.2f zł%n", product.getName(), product.getPrice());
        }

        // Ustawia strategię sortowania produktów w koszyku na sortowanie po nazwie
        cart.setSortingStrategy(new NameAscendingStrategy());
        // Wyświetla produkty w koszyku, posortowane po nazwie
        System.out.println("\nProdukty posortowane po nazwie:");
        for (Product product : cart.getProducts()) {
            System.out.printf("%s - %.2f zł%n", product.getName(), product.getPrice());
        }

        // Wyświetla najtańsze 3 produkty w koszyku
        System.out.println("\nNajtańsze 3 produkty:");
        for (Product product : cart.findNCheapestProducts(3)) {
            System.out.printf("%s - %.2f zł%n", product.getName(), product.getPrice());
        }

        // Dodaje promocje do koszyka
        cart.addPromotion(new OrderValueDiscount(300.0, 0.05));
        cart.addPromotion(new ThirdProductFree());
        
        // Tworzy produkt, który będzie dodany jako gratis
        Product kubek = new Product("GIFT-001", "Firmowy kubek", 30.0);
        cart.addPromotion(new FreeGiftPromotion(200.0, kubek));

        // Oblicza całkowitą wartość koszyka po zastosowaniu promocji
        double total = cart.calculateTotal();
        System.out.printf("%nCałkowita wartość koszyka po promocjach: %.2f zł%n", total);

        // Wyświetla produkty w koszyku po zastosowaniu promocji
        System.out.println("\nProdukty po zastosowaniu promocji:");
        Product[] products = cart.getProducts();
        Arrays.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
        for (Product product : products) {
            if (product.getDiscountPrice() < product.getPrice()) {
                System.out.printf("%s - %.2f zł (było: %.2f zł)%n", product.getName(), product.getDiscountPrice(), product.getPrice());
            } else {
                System.out.printf("%s - %.2f zł%n", product.getName(), product.getPrice());
            }
        }

        // Wyświetla informację o gratisach
        System.out.println("\nDodatkowe gratisy:");
        if (kubek.getDiscountPrice() == 0.0) {
            System.out.println("Otrzymujesz firmowy kubek gratis!");
        }
    }
}
