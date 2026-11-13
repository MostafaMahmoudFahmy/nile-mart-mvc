package org.route.nile_mart_mvc.controller;


import jakarta.validation.Valid;
import org.route.nile_mart_mvc.model.Brand;
import org.route.nile_mart_mvc.service.BrandServices;
import org.route.nile_mart_mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BrandController {

private final BrandServices brandServices;

    public BrandController(BrandServices brandServices) {
        this.brandServices = brandServices;
    }

    @GetMapping("/brands")
    public String getBrands(Model model) {
        model.addAttribute("brands", brandServices.findAll());
        return "brands";
    }

    @GetMapping("/brands/new")
    public String showBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "brandForm";
    }

    @PostMapping("/brands")
    public String saveBrand(@Valid @ModelAttribute("brand") Brand brand , BindingResult result) {
        if(!result.hasErrors()) {
            String error = brandServices.validateBrand(brand);
            if(error != null) {
                if(error.equals("Slug already exists")) {
                    result.rejectValue("slug", "duplicate", error);
                }else {
                    result.rejectValue("name", "duplicate", error);
                }
            }
        }

        if(result.hasErrors()) {
            return "brandForm";
        }
        brandServices.save(brand);
        return "redirect:/brands";
    }


}
