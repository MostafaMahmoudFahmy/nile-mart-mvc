package org.route.nile_mart_mvc.service;

import org.route.nile_mart_mvc.model.Brand;
import org.route.nile_mart_mvc.model.Category;
import org.route.nile_mart_mvc.repository.InMemoryCategoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryService {

    private final InMemoryCategoryRepository inMemoryCategoryRepository;

    public CategoryService(InMemoryCategoryRepository inMemoryCategoryRepository) {
        this.inMemoryCategoryRepository = inMemoryCategoryRepository;
    }


    List<Category> findAll() {
        return inMemoryCategoryRepository.findAll();
    }

    public Category findBySlug(String slug) {
        return inMemoryCategoryRepository.findBySlug(slug);
    }


    public List<Category> findTopLevelCategories() {
        return inMemoryCategoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() == null ||
                        c.getParentSlug().isBlank())
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
    }

    public List<Category> findSubCategories() {
        return inMemoryCategoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() != null &&
                        !c.getParentSlug().isBlank())
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
    }

    public List<Category> findActiveSubCategories() {
        return findSubCategories().stream()
                .filter(Category::isActive)
                .toList();
    }

    public List<Category> findCategoriesForDisplay() {
        List<Category> result = new ArrayList<>();
        for (Category parent : findTopLevelCategories()) {
            result.add(parent);

            List<Category> children = findAll().stream()
                    .filter(category ->
                            parent.getSlug().equals(category.getParentSlug()))
                    .sorted(Comparator.comparing(Category::getDisplayOrder))
                    .toList();
            result.addAll(children);
        }

        return result;
    }


    public String getParentName(Category category) {

        if (category.getParentSlug() == null ||
                category.getParentSlug().isBlank()) {
            return "—";
        }

        Category parent = findBySlug(category.getParentSlug());

        return parent == null ? "—" : parent.getName();
    }




    public String ValidateCategory(Category category) {
        String parentSlug = category.getParentSlug() == null ? "" : category.getParentSlug();

        // parent must exist and must be top level
        if (!parentSlug.isEmpty()) {
            Category parent = findBySlug(parentSlug);
            if (parent == null) {
                return "Selected Parent doesn't exist";
            }
            if (parent.getParentSlug() != null && !parent.getParentSlug().isBlank()) {
                return "Parent must be a top-level category";
            }
        }

        for (Category existing : inMemoryCategoryRepository.findAll()) {
            if (existing.getSlug().equals(category.getSlug())) {
                return "Slug already exists";
            }

            String existingParent = existing.getParentSlug() == null ? "" : existing.getParentSlug();
            if (existingParent.equals(parentSlug) && existing.getName().equalsIgnoreCase(category.getName())) {

                String parentName = parentSlug.isEmpty()
                        ? "top level"
                        : findBySlug(parentSlug).getName();

                return "A category with this name already exists under "
                        + parentName;
            }
        }
        return null;
    }

    public void save(Category category) {

        if (category.getParentSlug() == null) {
            category.setParentSlug("");
        }

        inMemoryCategoryRepository.save(category);
    }


}







