package org.route.nile_mart_mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @NotBlank(message = "Brand Name is Required")
    @Size(min = 2, max = 50 , message ="Brand Name is Required" )
    private String name;


    @NotBlank(message = "Slug is Required")
    @Pattern(regexp = "^[a-z0-9-]+$" , message = "Slug can contain only lowercase letters, numbers and hyphens")
    private String slug;


    //Sets a default value of true for the 'active' field
    //to preserve it when using the Lombok @Builder patter
    @Builder.Default
    private boolean active = true ;



}
