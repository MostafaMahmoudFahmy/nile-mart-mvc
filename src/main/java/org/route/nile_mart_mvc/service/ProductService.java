package org.route.nile_mart_mvc.service;

import org.route.nile_mart_mvc.model.Brand;
import org.route.nile_mart_mvc.model.Category;
import org.route.nile_mart_mvc.model.Product;
import org.route.nile_mart_mvc.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final IProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandServices brandService;

    public ProductService(IProductRepository productRepository, CategoryService categoryService, BrandServices brandService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findAll().stream()
                .filter(p -> p.getInStock() > 0)
                .toList();
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku) != null;
    }

    public String validateProduct(Product product) {

        if (isProductExist(product.getSku())) {
            return "Product already exists";
        }

        Category category = categoryService.findBySlug(product.getCategory());
        if (category == null || !category.isActive() || category.getParentSlug() == null || category.getParentSlug().isEmpty()) {
            return "Product must belong to an active sub-category";
        }

        if (product.getBrandSlug() != null && !product.getBrandSlug().isEmpty()) {

            Brand brand = brandService.findBySlug(product.getBrandSlug());
            if (brand == null || !brand.isActive()) {
                return "Brand does not exist or is inactive";
            }
        }

        return null;
    }

    public String getCategoryName(String categorySlug) {
        Category category = categoryService.findBySlug(categorySlug);
        return category == null ? "_" : category.getName();
    }

    public String getBrandName(String brandSlug) {
        if (brandSlug == null || brandSlug.isBlank()) {
            return "_";
        }
        Brand brand = brandService.findBySlug(brandSlug);

        return brand == null ? "_" : brand.getName();

    }


    public void saveProduct(Product product) {
        productRepository.save(product);
    }


}