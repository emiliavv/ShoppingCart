package pl.javamarkt;

import javax.swing.*;
import java.awt.*;
import pl.javamarkt.promotion.OrderValueDiscount;
import pl.javamarkt.promotion.ThirdProductFree;
import pl.javamarkt.promotion.FreeGiftPromotion;
import pl.javamarkt.sorting.NameAscendingStrategy;

/**
 * Klasa reprezentująca graficzny interfejs użytkownika koszyka zakupowego.
 */
public class ShoppingCartGUI {
    // Główny obiekt koszyka, przechowuje produkty i promocje
    private ShoppingCart cart;
    // Pole tekstowe do wyświetlania listy produktów
    private JTextArea productList;
    // Etykieta do wyświetlania sumy
    private JLabel totalLabel;
    // Okno aplikacji
    private JFrame frame;

    /**
     * Konstruktor uruchamiający całą logikę GUI.
     */
    public ShoppingCartGUI() {
        cart = new ShoppingCart(); // Tworzy nowy koszyk
        initializeCart(); // Dodaje produkty do koszyka
        createAndShowGUI(); // Tworzy i wyświetla GUI
        findBestPromotion(); // Automatycznie wybiera najlepszą promocję
    }

    /**
     * Dodaje przykładowe produkty do koszyka.
     */
    private void initializeCart() {
        cart.addProduct(new Product("001", "Laptop", 3500.0));
        cart.addProduct(new Product("002", "Mysz", 150.0));
        cart.addProduct(new Product("003", "Klawiatura", 250.0));
        cart.addProduct(new Product("004", "Monitor", 1200.0));
        cart.addProduct(new Product("005", "Słuchawki", 300.0));
        cart.addProduct(new Product("006", "Podkładka", 50.0));
        cart.addProduct(new Product("007", "Kamera", 400.0));
        cart.addProduct(new Product("008", "Głośniki", 200.0));
        cart.addProduct(new Product("009", "USB", 80.0));
    }

    /**
     * Tworzy i konfiguruje graficzny interfejs użytkownika.
     */
    private void createAndShowGUI() {
        frame = new JFrame("Koszyk zakupowy"); // Tworzy główne okno
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); // Ustawia rozmiar okna

        JPanel mainPanel = new JPanel(); // Panel główny
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        productList = new JTextArea(); // Pole do wyświetlania produktów
        productList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productList); // Rolowanie listy produktów

        JPanel buttonPanel = new JPanel(); // Panel na przyciski
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));

        // Przycisk sortowania po nazwie
        JButton sortByNameBtn = new JButton("Sortuj po nazwie");
        sortByNameBtn.addActionListener(e -> sortByName());

        // Przycisk promocji: zniżka od wartości zamówienia
        JButton orderValueDiscountBtn = new JButton("Promocja: 5% zniżki od 300 zł");
        orderValueDiscountBtn.addActionListener(e -> applyOrderValueDiscount());

        // Przycisk promocji: trzeci produkt gratis
        JButton thirdProductFreeBtn = new JButton("Promocja: trzeci produkt gratis");
        thirdProductFreeBtn.addActionListener(e -> applyThirdProductFree());

        // Przycisk promocji: kubek gratis
        JButton freeGiftBtn = new JButton("Promocja: kubek gratis od 200 zł");
        freeGiftBtn.addActionListener(e -> applyFreeGift());

        // Dodanie przycisków do panelu
        buttonPanel.add(sortByNameBtn);
        buttonPanel.add(orderValueDiscountBtn);
        buttonPanel.add(thirdProductFreeBtn);
        buttonPanel.add(freeGiftBtn);

        totalLabel = new JLabel(); // Etykieta sumy
        totalLabel.setHorizontalAlignment(JLabel.CENTER);

        // Rozmieszczenie elementów na panelu głównym
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(totalLabel, BorderLayout.NORTH);

        frame.add(mainPanel); // Dodanie panelu do okna
        updateProductList(); // Wyświetlenie produktów
        updateTotal(); // Wyświetlenie sumy
        frame.setVisible(true); // Pokazanie okna
    }

    /**
     * Wyszukuje i stosuje najlepszą promocję na podstawie sumy końcowej.
     */
    private void findBestPromotion() {
        // Tworzenie kopii koszyka do testowania różnych promocji
        ShoppingCart testCart1 = new ShoppingCart();
        ShoppingCart testCart2 = new ShoppingCart();
        ShoppingCart testCart3 = new ShoppingCart();
        
        // Kopiowanie produktów do każdego testowego koszyka
        for (Product p : cart.getProducts()) {
            testCart1.addProduct(new Product(p.getCode(), p.getName(), p.getPrice()));
            testCart2.addProduct(new Product(p.getCode(), p.getName(), p.getPrice()));
            testCart3.addProduct(new Product(p.getCode(), p.getName(), p.getPrice()));
        }

        // Dodanie różnych promocji do testowych koszyków
        testCart1.addPromotion(new OrderValueDiscount(300.0, 0.05));
        testCart2.addPromotion(new ThirdProductFree());
        Product kubek = new Product("GIFT-001", "Firmowy kubek", 25.0);
        testCart3.addPromotion(new FreeGiftPromotion(200.0, kubek));

        // Obliczenie sumy po zastosowaniu każdej promocji
        double total1 = testCart1.calculateTotal();
        double total2 = testCart2.calculateTotal();
        double total3 = testCart3.calculateTotal();

        // Wybór promocji dającej najniższą cenę końcową
        if (total1 <= total2 && total1 <= total3) {
            applyOrderValueDiscount();
            productList.append("\nZastosowano najlepszą promocję: 5% zniżki od 300 zł");
        } else if (total2 <= total1 && total2 <= total3) {
            applyThirdProductFree();
            productList.append("\nZastosowano najlepszą promocję: trzeci produkt gratis");
        } else {
            applyFreeGift();
            productList.append("\nZastosowano najlepszą promocję: kubek gratis od 200 zł");
        }
    }

    /**
     * Aktualizuje wyświetlaną listę produktów.
     */
    private void updateProductList() {
        StringBuilder sb = new StringBuilder();
        for (Product product : cart.getProducts()) {
            // Sprawdza czy produkt ma zniżkę
            if (product.getDiscountPrice() < product.getPrice()) {
                sb.append(String.format("%s - %.2f zł (było: %.2f zł)%n", 
                    product.getName(), product.getDiscountPrice(), product.getPrice()));
            } else {
                sb.append(String.format("%s - %.2f zł%n", 
                    product.getName(), product.getPrice()));
            }
        }
        productList.setText(sb.toString()); // Ustawia tekst w polu listy
    }

    /**
     * Aktualizuje sumę w etykiecie.
     */
    private void updateTotal() {
        double total = cart.calculateTotal();
        totalLabel.setText(String.format("Całkowita wartość: %.2f zł", total));
    }

    /**
     * Sortuje produkty po nazwie rosnąco.
     */
    private void sortByName() {
        cart.setSortingStrategy(new NameAscendingStrategy());
        updateProductList();
    }

    /**
     * Nakłada promocję: 5% zniżki od 300 zł.
     */
    private void applyOrderValueDiscount() {
        cart.addPromotion(new OrderValueDiscount(300.0, 0.05));
        updateProductList();
        updateTotal();
    }

    /**
     * Nakłada promocję: trzeci produkt gratis.
     */
    private void applyThirdProductFree() {
        cart.addPromotion(new ThirdProductFree());
        updateProductList();
        updateTotal();
    }

    /**
     * Nakłada promocję: kubek gratis od 200 zł.
     */
    private void applyFreeGift() {
        Product kubek = new Product("GIFT-001", "Firmowy kubek", 25.0);
        cart.addPromotion(new FreeGiftPromotion(200.0, kubek));
        updateProductList();
        updateTotal();
        
        // Jeśli kubek jest gratis, wyświetl informację
        if (kubek.getDiscountPrice() == 0.0) {
            productList.append("\nBonus: Otrzymujesz firmowy kubek gratis!");
        }
    }

    /**
     * Metoda główna uruchamiająca aplikację.
     * @param args argumenty linii poleceń
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShoppingCartGUI());
    }
}
