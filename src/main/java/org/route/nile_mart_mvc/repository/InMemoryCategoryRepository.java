package org.route.nile_mart_mvc.repository;

import jakarta.annotation.PostConstruct;
import org.route.nile_mart_mvc.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class InMemoryCategoryRepository implements ICategoryRepository {
    private final List<Category> categories = new ArrayList<>();

    @PostConstruct
    public void init() {

        // Top-level categories
        categories.add(Category.builder()
                .name("Electronics")
                .slug("electronics")
                .parentSlug("")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Home & Kitchen")
                .slug("home-kitchen")
                .parentSlug("")
                .displayOrder(2)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Fashion")
                .slug("fashion")
                .parentSlug("")
                .displayOrder(3)
                .active(false)
                .build());

        // Electronics subcategories
        categories.add(Category.builder()
                .name("Mobile Phones")
                .slug("mobile-phones")
                .parentSlug("electronics")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Laptops")
                .slug("laptops")
                .parentSlug("electronics")
                .displayOrder(2)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Accessories")
                .slug("electronics-accessories")
                .parentSlug("electronics")
                .displayOrder(3)
                .active(true)
                .build());

        // Home & Kitchen subcategories
        categories.add(Category.builder()
                .name("Cookware")
                .slug("cookware")
                .parentSlug("home-kitchen")
                .displayOrder(1)
                .active(true)
                .build());

        categories.add(Category.builder()
                .name("Lighting")
                .slug("lighting")
                .parentSlug("home-kitchen")
                .displayOrder(2)
                .active(true)
                .build());

        // Fashion subcategory
        categories.add(Category.builder()
                .name("Men's Clothing")
                .slug("mens-clothing")
                .parentSlug("fashion")
                .displayOrder(1)
                .active(true)
                .build());
    }


    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }

    @Override
    public Category findBySlug(String slug) {
        return categories.stream().filter(c -> c.getSlug().equals(slug)).findFirst().orElse(null);
    }

    @Override
    public void save(Category category) {

        categories.add(category);
    }
}
