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
    private Long expenseId;

    @NotEmpty(message = "Name of expense can't be empty.")
    @Column(name = "name", nullable = false)
    private String expenseName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Many expenses belong to one category
    @JoinColumn(name = "FK_CATEGORY_ID", nullable = false) // Foreign key column in Expense table
    private Category category; // writing way is this but storing the id only

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // by fetchType.LAZY only the copy is fetched
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    private User user;


    @Min(1)
    @Column(name = "amount", nullable = false)
    private Double amount;

    @PastOrPresent
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    private String comments;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
    
    
    
}
