
package org.route.nile_mart_mvc.repository;

import jakarta.annotation.PostConstruct;
import org.route.nile_mart_mvc.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryProductRepository implements IProductRepository {

    private final List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {

        products.add(Product.builder()
                .sku("P002")
                .name("Desk Lamp")
                .category("lighting")
                .brandSlug(null)
                .price(new BigDecimal("500.00"))
                .inStock(10)
                .build());

        products.add(Product.builder()
                .sku("P003")
                .name("ASUS TUF")
                .category("laptops")
                .brandSlug("asus")
                .price(new BigDecimal("24000.00"))
                .inStock(4)
                .build());

        products.add(Product.builder()
                .sku("PH-A55-128")
                .name("Samsung Galaxy A55 128GB")
                .category("mobile-phones")
                .brandSlug("samsung")
                .price(new BigDecimal("18500.00"))
                .inStock(7)
                .build());
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findBySku(String sku) {
        return products.stream()
                .filter(product -> product.getSku().equalsIgnoreCase(sku))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }
}
