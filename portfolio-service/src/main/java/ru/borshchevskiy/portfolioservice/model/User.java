package ru.borshchevskiy.portfolioservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
@Data
public class User {
    @Id
    private Long id;
    @Column("username")
    private String username;
    @Column("firstname")
    private String firstname;
    @Column("lastname")
    private String lastname;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("created_date")
    @CreatedDate
    private LocalDateTime createdDate;
    @Column("last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}

