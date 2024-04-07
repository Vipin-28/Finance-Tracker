package com.vipinkumarx28.sboot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(
        name = "expense_table"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @SequenceGenerator(name = "expense_id_generator", sequenceName = "EXPENSE_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "expense_id_generator", strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @NotEmpty(message = "Name of expense can't be empty.")
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Many expenses belong to one category
//    @JoinColumn(name = "expense_id") // Foreign key column in Expense table
//    private Category category; // writing way is this but storing the id only

    @Min(1)
    @Column(name = "amount", nullable = false)
    private Double amount;

    @PastOrPresent
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    private String comments;
}
