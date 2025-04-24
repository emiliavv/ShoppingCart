package pl.javamarkt;

import pl.javamarkt.promotion.PromotionCommand;
import pl.javamarkt.sorting.ProductSortingStrategy;
import pl.javamarkt.sorting.PriceDescendingNameAscendingStrategy;
import pl.javamarkt.sorting.PriceAscendingStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Klasa reprezentująca koszyk zakupowy
public class ShoppingCart {
    // Tablica produktów znajdujących się w koszyku
    private Product[] products;
    // Lista zastosowanych promocji
    private List<PromotionCommand> promotions;
    // Strategia sortowania produktów
    private ProductSortingStrategy sortingStrategy;

    // Konstruktor koszyka – ustawia pustą tablicę produktów, pustą listę promocji i domyślną strategię sortowania
    public ShoppingCart() {
        this.products = new Product[0];
        this.promotions = new ArrayList<>();
        this.sortingStrategy = new PriceDescendingNameAscendingStrategy();
    }

    // Dodaje nowy produkt do koszyka i sortuje produkty zgodnie z wybraną strategią
    public void addProduct(Product product) {
        Product[] newProducts = Arrays.copyOf(products, products.length + 1);
        newProducts[products.length] = product;
        products = newProducts;
        sortProducts();
    }

    // Ustawia strategię sortowania i sortuje produkty
    public void setSortingStrategy(ProductSortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
        sortProducts();
    }

    // Sortuje produkty w koszyku według bieżącej strategii
    private void sortProducts() {
        Arrays.sort(products, sortingStrategy);
    }

    // Dodaje promocję do listy aktywnych promocji
    public void addPromotion(PromotionCommand promotion) {
        promotions.add(promotion);
    }

    // Usuwa promocję z listy aktywnych promocji
    public void removePromotion(PromotionCommand promotion) {
        promotions.remove(promotion);
    }

    // Oblicza całkowitą wartość koszyka po zastosowaniu najlepszej kolejności promocji
    public double calculateTotal() {
        resetDiscounts(); // Najpierw resetuje wszystkie zniżki
        applyBestPromotionOrder(); // Szuka najlepszej kolejności promocji
        
        double total = 0;
        for (Product product : products) {
            total += product.getDiscountPrice(); // Sumuje ceny po zniżkach
        }
        return total;
    }

    // Resetuje zniżki dla wszystkich produktów w koszyku
    private void resetDiscounts() {
        for (Product product : products) {
            product.resetDiscount();
        }
    }

    // Przetestowanie wszystkich permutacji promocji i zastosowanie tej, która daje najniższą sumę
    private void applyBestPromotionOrder() {
        List<List<PromotionCommand>> permutations = generatePermutations(new ArrayList<>(promotions));
        double bestTotal = Double.MAX_VALUE;
        List<PromotionCommand> bestOrder = null;

        for (List<PromotionCommand> permutation : permutations) {
            resetDiscounts();
            for (PromotionCommand promotion : permutation) {
                promotion.execute(products); // Wykonuje promocję
            }
            
            double currentTotal = Arrays.stream(products)
                .mapToDouble(Product::getDiscountPrice)
                .sum();

            if (currentTotal < bestTotal) {
                bestTotal = currentTotal;
                bestOrder = permutation;
            }

            // Cofnięcie promocji, aby nie wpływały na kolejne permutacje
            for (int i = permutation.size() - 1; i >= 0; i--) {
                permutation.get(i).undo(products);
            }
        }

        // Zastosowanie najlepszej kolejności promocji
        if (bestOrder != null) {
            resetDiscounts();
            for (PromotionCommand promotion : bestOrder) {
                promotion.execute(products);
            }
        }
    }

    // Generuje wszystkie możliwe permutacje listy promocji
    private List<List<PromotionCommand>> generatePermutations(List<PromotionCommand> promotions) {
        List<List<PromotionCommand>> result = new ArrayList<>();
        if (promotions.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }

        for (int i = 0; i < promotions.size(); i++) {
            PromotionCommand current = promotions.get(i);
            List<PromotionCommand> remaining = new ArrayList<>(promotions);
            remaining.remove(i);

            for (List<PromotionCommand> permutation : generatePermutations(remaining)) {
                List<PromotionCommand> newPermutation = new ArrayList<>();
                newPermutation.add(current);
                newPermutation.addAll(permutation);
                result.add(newPermutation);
            }
        }
        return result;
    }

    // Zwraca najtańszy produkt w koszyku
    public Product findCheapestProduct() {
        if (products.length == 0) return null;
        return Arrays.stream(products)
            .min(new PriceAscendingStrategy())
            .orElse(null);
    }

    // Zwraca najdroższy produkt w koszyku
    public Product findMostExpensiveProduct() {
        if (products.length == 0) return null;
        return Arrays.stream(products)
            .max(new PriceAscendingStrategy())
            .orElse(null);
    }

    // Zwraca tablicę n najtańszych produktów
    public Product[] findNCheapestProducts(int n) {
        return Arrays.stream(products)
            .sorted(new PriceAscendingStrategy())
            .limit(n)
            .toArray(Product[]::new);
    }

    // Zwraca tablicę n najdroższych produktów
    public Product[] findNMostExpensiveProducts(int n) {
        return Arrays.stream(products)
            .sorted(new PriceAscendingStrategy().reversed())
            .limit(n)
            .toArray(Product[]::new);
    }

    // Zwraca kopię tablicy produktów
    public Product[] getProducts() {
        return Arrays.copyOf(products, products.length);
    }
}
