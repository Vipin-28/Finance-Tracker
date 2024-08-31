package com.vipinkumarx28.sboot.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(
            generator = "category_id_generator",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "category_id_generator",
            sequenceName="CATEGORY_SEQ",
            allocationSize=1
    )
    Long categoryId;

    @NotEmpty(message = "Category name can't be empty")
    @Schema(description = "used to give name to category")
    String categoryName;

    @Size(max=500)
    String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Many expenses belong to one category
    @JoinColumn(name = "FK_USER_ID", nullable = false) // Foreign key column in Expense table
    private User user; // writing way is this but storing the id only

    public Category(String categoryName, String description, User user) {
        this.categoryName = categoryName;
        this.description = description;
        this.user = user;
    }

    public Category() {
    	super();
    }
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
