package org.route.nile_mart_mvc.repository;

import org.route.nile_mart_mvc.model.Brand;

import java.util.List;

public interface IBrandRepository {
    List<Brand> findAll();

    Brand finBySlug(String slug);

    void save(Brand brand);
}
