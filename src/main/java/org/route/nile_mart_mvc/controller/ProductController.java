
package org.route.nile_mart_mvc.controller;

import jakarta.validation.Valid;
import org.route.nile_mart_mvc.model.Product;
import org.route.nile_mart_mvc.service.BrandServices;
import org.route.nile_mart_mvc.service.ProductService;
import org.route.nile_mart_mvc.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final BrandServices brandService;
    private final CategoryService categoryService;

    public ProductController(
            ProductService productService,
            BrandServices brandService,
            CategoryService categoryService) {

        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    // Display all products
    @GetMapping
    public String getProducts(Model model) {

        var products = productService.findAll();

        Map<String, String> categoryNames =
                products.stream().collect(Collectors.toMap(
                        Product::getSku,
                        product -> categoryService
                                .findBySlug(product.getCategory())
                                .getName()
                ));

        Map<String, String> brandNames =
                products.stream().collect(Collectors.toMap(
                        Product::getSku,
                        product -> {
                            if (product.getBrandSlug() == null ||
                                    product.getBrandSlug().isBlank()) {
                                return "—";
                            }

                            return brandService
                                    .findBySlug(product.getBrandSlug())
                                    .getName();
                        }
                ));

        model.addAttribute("products", products);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("brandNames", brandNames);

        return "products";
    }

    // Display product form
    @GetMapping("/new")
    public String showProductForm(Model model) {

        model.addAttribute("product", new Product());

        model.addAttribute(
                "categories",
                categoryService.findActiveSubCategories()
        );

        model.addAttribute(
                "brands",
                brandService.findActiveBrands()
        );

        return "productForm";
    }

    // Save product
    @PostMapping
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model) {

        if (!result.hasErrors()) {

            String error = productService.validateProduct(product);

            if (error != null) {
                result.reject("productError", error);
            }
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "categories",
                    categoryService.findActiveSubCategories()
            );

            model.addAttribute(
                    "brands",
                    brandService.findActiveBrands()
            );

            return "productForm";
        }

        productService.saveProduct(product);

        return "redirect:/products";
    }
}
