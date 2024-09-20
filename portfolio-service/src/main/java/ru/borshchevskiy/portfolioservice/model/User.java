package ru.borshchevskiy.portfolioservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    @Column("created_date")
    private LocalDateTime createdDate;
    @Column("last_modified_date")
    private LocalDateTime lastModifiedDate;
}

