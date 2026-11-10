package org.route.nile_mart_mvc.repository;

import org.route.nile_mart_mvc.model.Category;

import java.util.List;

public interface ICategoryRepository {

    List<Category> findAll();

    Category findBySlug(String slug);

    void save(Category category);


}
