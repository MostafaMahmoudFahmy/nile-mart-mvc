
package org.route.nile_mart_mvc.controller;

import jakarta.validation.Valid;
import org.route.nile_mart_mvc.model.Category;
import org.route.nile_mart_mvc.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/categories")
    public String getCategories(Model model) {

        var categories = categoryService.findCategoriesForDisplay();

        Map<String, String> parentNames =
                categories.stream().collect(
                        java.util.stream.Collectors.toMap(
                                Category::getSlug,
                                categoryService::getParentName
                        )
                );

        model.addAttribute("categories", categories);
        model.addAttribute("parentNames", parentNames);

        return "categories";
    }


    @GetMapping("/categories/new")
    public String showCategoryForm(Model model) {

        model.addAttribute("category", new Category());

        model.addAttribute("parentCategories", categoryService.findTopLevelCategories());

        return "categoryForm";
    }

    @PostMapping("/categories")
    public String saveCategory(
            @Valid @ModelAttribute("category") Category category,
            BindingResult result,
            Model model) {

        if (!result.hasErrors()) {

            String error = categoryService.validateCategory(category);

            if (error != null) {

                if (error.equals("Slug already exists")) {
                    result.rejectValue("slug", "duplicate", error);

                } else if (error.startsWith("Selected parent") ||
                        error.startsWith("Parent must")) {

                    result.rejectValue("parentSlug", "invalid", error);

                } else {
                    result.rejectValue("name", "duplicate", error);
                }
            }
        }

        if (result.hasErrors()) {

            model.addAttribute("parentCategories", categoryService.findTopLevelCategories());

            return "categoryForm";
        }

        categoryService.save(category);

        return "redirect:/categories";
    }
}
