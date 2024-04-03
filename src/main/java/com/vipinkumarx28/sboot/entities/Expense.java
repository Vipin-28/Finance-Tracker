package com.vipinkumarx28.sboot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "entity_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @NotEmpty(message = "Name of expense can't be empty.")
    private String name;

    private Long categoryId;

    @Min(1)
    private Double amount;

    @PastOrPresent
    private LocalDate creationDate;

    private String comments;
}
