package com.vipinkumarx28.sboot.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(
            generator = "category_id_generate",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "category_id_generate",
            sequenceName="CATEGORY_SEQ",
            allocationSize=1
    )
    Long id;

    @NotEmpty(message = "Category name can't be empty")
    @Schema(description = "used to give name to category")
    String name;

    @Size(max=500)
    String description;
}
