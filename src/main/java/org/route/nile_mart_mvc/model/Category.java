package org.route.nile_mart_mvc.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @NotBlank(message = "Category name is required")
    @Size(min = 2 , max = 50 , message = "Category name must be between 2 and 50 characters")
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^[a-z0-9-]+$",
            message = "Slug can contain only lowercase letters, numbers and hyphens"
    )
    private String slug;

    @Builder.Default
    private String parentSlug = "";

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order must be zero or greater")
    private Integer displayOrder;

    @Builder.Default
    private boolean active = true ;

}
