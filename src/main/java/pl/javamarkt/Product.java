package pl.javamarkt;

// Klasa reprezentująca pojedynczy produkt w koszyku
public class Product implements Comparable<Product> {
    // Kod produktu
    private String code;
    // Nazwa produktu
    private String name;
    // Cena podstawowa produktu
    private double price;
    // Cena po zastosowaniu zniżki/promocji
    private double discountPrice;

    // Konstruktor produktu – ustawia kod, nazwę i cenę
    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    // Zwraca kod produktu
    public String getCode() {
        return code;
    }

    // Zwraca nazwę produktu
    public String getName() {
        return name;
    }

    // Zwraca cenę podstawową produktu
    public double getPrice() {
        return price;
    }

    // Zwraca cenę po zniżce
    public double getDiscountPrice() {
        return discountPrice;
    }

    // Ustawia nową cenę po zniżce
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    // Resetuje cenę po zniżce do ceny podstawowej
    public void resetDiscount() {
        this.discountPrice = this.price;
    }

    // Porównuje produkty według ceny malejąco, a następnie według nazwy rosnąco
    @Override
    public int compareTo(Product other) {
        int priceCompare = Double.compare(other.price, this.price);
        if (priceCompare != 0) {
            return priceCompare;
        }
        return this.name.compareTo(other.name);
    }

    // Sprawdza, czy dwa produkty są równe na podstawie kodu
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code.equals(product.code);
    }

    // Zwraca hash kodu produktu
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
