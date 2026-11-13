package org.route.nile_mart_mvc.controller;


import org.route.nile_mart_mvc.service.BrandServices;
import org.route.nile_mart_mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


}
