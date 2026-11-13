package org.route.nile_mart_mvc.service;

import org.route.nile_mart_mvc.model.Brand;
import org.route.nile_mart_mvc.repository.InMemoryBrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandServices {

    private final InMemoryBrandRepository inMemoryBrandRepository;

    public BrandServices(InMemoryBrandRepository inMemoryBrandRepository) {
        this.inMemoryBrandRepository = inMemoryBrandRepository;
    }

    public List<Brand> findAll() {
        return inMemoryBrandRepository.findAll();
    }

    public List<Brand> findActiveBrands() {
        return inMemoryBrandRepository.findAll().stream()
                .filter(Brand::isActive)
                .toList();
    }

    public Brand findBySlug(String slug) {
        return inMemoryBrandRepository.finBySlug(slug);
    }

    public String validateBrand(Brand brand) {
        for (Brand b : inMemoryBrandRepository.findAll()) {
            if (b.getSlug().equals(brand.getSlug())) {
                return "Slug already exists";
            }

            if (b.getName().equals(brand.getName())) {
                return "Name already exists";
            }
        }
        return null;
    }

    public void save(Brand brand) {
        inMemoryBrandRepository.save(brand);
    }


}
