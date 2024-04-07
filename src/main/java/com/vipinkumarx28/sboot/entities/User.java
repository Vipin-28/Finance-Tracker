package com.vipinkumarx28.sboot.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Entity
@Table(
        name = "user_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "emailId", columnNames = "email_address"),
                @UniqueConstraint(name = "userName", columnNames = "user_name"),
                @UniqueConstraint(name = "phoneNumber", columnNames = "phone_number")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /*
       {userId, userName, emailId, phoneNumber, password}
     */
    @Id
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_generator", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long userId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailId;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ensure password is only serialized (not deserialized)
    @NotNull @Size(min = 8, max = 255) // Example minimum and maximum password length
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword; // Transient field for password confirmation


    private Boolean enabled = false;
}
