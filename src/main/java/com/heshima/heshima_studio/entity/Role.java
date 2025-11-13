package com.heshima.heshima_studio.entity;

import jakarta.persistence.*;

/**
 * JPA entity that represents a security role in the system.
 *
 * In this project:
 * - Roles are used by Spring Security to control access (ex: "ADMIN", "USER").
 * - The DataInitializer seeds these roles so I can attach them to users.
 * - CustomUserDetailsService reads the Role name and turns it into a
 *   Spring Security authority (ROLE_ADMIN, ROLE_USER).
 */

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Constructors
    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    // Getters/Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

