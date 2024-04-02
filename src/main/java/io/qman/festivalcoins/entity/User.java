package io.qman.festivalcoins.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * This class represents a User entity in the system.
 * It is annotated as a JPA Entity, meaning it will be mapped to a database table.
 * It also implements UserDetails interface for Spring Security.
 */
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    // Unique identifier for the user
    private Integer id;

    @Column(nullable = false)
    // Full name of the user
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    // Email of the user, used as the username in Spring Security
    private String email;

    @Column(nullable = false)
    // Encrypted password of the user
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    // Timestamp of when the user was created
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    // Timestamp of the last update to the user's data
    private Date updatedAt;

    @Override
    // Returns the authorities granted to the user. Empty by default.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // Getter and setter methods for the fields

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    // Returns the username (email) of the user
    public String getUsername() {
        return email;
    }

    @Override
    // Checks if the user's account has not expired
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // Checks if the user's account is not locked
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // Checks if the user's credentials have not expired
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // Checks if the user's account is enabled
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Overrides the default toString method.
     * @return a string representation of the User instance
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}