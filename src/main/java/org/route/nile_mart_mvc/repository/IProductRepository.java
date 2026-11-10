package org.route.nile_mart_mvc.repository;

import org.route.nile_mart_mvc.model.Product;

import java.util.List;

public interface IProductRepository {

    List<Product> findAll();

    Product findBySku(String sku);

    void save(Product product);
}
