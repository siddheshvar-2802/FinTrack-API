package com.FinTrack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_master")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String password;

    private String role;
}
