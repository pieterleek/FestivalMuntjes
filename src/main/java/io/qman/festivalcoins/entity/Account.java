package io.qman.festivalcoins.entity;

import com.fasterxml.jackson.annotation.JsonView;
import io.qman.festivalcoins.serialization.ViewClasses;
import io.qman.festivalcoins.util.SecureHasher;
import jakarta.persistence.*;
import java.util.Objects;


/**
 * Account entity class.
 * Represents an account in the system with fields for id, name, email, role, and hashed password.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Accounts_find_by_email",
                query = "select a from Account a where a.email = ?1")
})
public class Account {


    // unique id-s are generated by the back-end persistence layer
    @SequenceGenerator(name = "Account_ids", initialValue = 100001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Account_ids")
    @Id
    private long id = 0L;     // identification of an Author

    @JsonView(ViewClasses.Shallow.class)
    private String name;

    @JsonView(ViewClasses.Summary.class)
    private String email = "";

    @JsonView(ViewClasses.Summary.class)
    private String role = "Regular";

    private String hashedPassword = null;

    /**
     * Default constructor for Account.
     */
    public Account() {
    }

    /**
     * Constructor for Account with id.
     * @param id Identifier of the Account.
     */
    public Account(long id) {
        this.id = id;
    }

    /**
     * Constructor for Account with id, email, and name.
     * @param id Identifier of the Account.
     * @param email Email of the Account.
     * @param name Name of the Account.
     */
    public Account(long id, String email, String name) {
        this(id);
        this.setEmail(email);
        this.setName(name);
    }

    /**
     * Hashes the given password in combination with the account identification (id)
     * and some extra characters for extra security.
     * Different accounts with the same password will deliver different hashes
     *
     * @param password Password to be hashed.
     * @return Hashed password.
     */
    public String hashPassword(String password) {
        return SecureHasher.secureHash("Id-" + this.getId() + ":" + password);
    }

    /**
     * Sets the password for the Account.
     * The password is hashed before being stored.
     *
     * @param newPassword New password for the Account.
     */
    public void setPassword(String newPassword) {
        this.setHashedPassword(this.hashPassword(newPassword));
    }

    /**
     * Verifies whether the hash of the given password
     * matches the correct hash of the account's true password
     * (without actually knowing the correct password: only its hash has been kept in store)
     *
     * @param password Password to be verified.
     * @return True if the password is correct, false otherwise.
     */
    public boolean verifyPassword(String password) {
        return this.hashPassword(password).equals(this.getHashedPassword());
    }

    // Getter and setter methods for the fields are omitted for brevity.

    /**
     * Checks if the given object is equal to this Account.
     * Two Accounts are equal if their ids are equal.
     *
     * @param o Object to be compared with this Account.
     * @return True if the given object is an Account and its id is equal to this Account's id, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        return id == ((Account) o).id;
    }

    /**
     * Generates a hash code for this Account.
     * The hash code is based on the Account's id.
     *
     * @return Hash code for this Account.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this Account.
     * The string representation includes the email, name, and id of the Account.
     *
     * @return String representation of this Account.
     */
    @Override
    public String toString() {
        return String.format("{ login=%s, callName=%s, id=%d }", this.email, this.name, this.id);
    }


    // Getters and setters for the fields


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}