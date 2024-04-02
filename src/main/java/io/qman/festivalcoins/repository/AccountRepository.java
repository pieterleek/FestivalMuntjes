package io.qman.festivalcoins.repository;

import io.qman.festivalcoins.entity.Account;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for Account entity.
 * Provides CRUD operations for Account.
 */
@Repository
public class AccountRepository {
    private final EntityManager em;

    /**
     * Constructor for AccountRepository.
     * @param em EntityManager for performing database operations.
     */
    @Autowired
    public AccountRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Fetches an Account by its uid.
     * @param uid Unique identifier of the Account.
     * @return Account object if found, null otherwise.
     */
    public Account get(String uid) {
        return em.find(Account.class, uid);
    }

    /**
     * Adds a new Account to the database.
     * @param account Account object to be added.
     */
    public void add(Account account) {
        em.persist(account);
    }

    /**
     * Updates an existing Account in the database.
     * @param account Account object to be updated.
     */
    public void update(Account account) {
        em.merge(account);
    }

    /**
     * Deletes an Account from the database.
     * @param account Account object to be deleted.
     */
    public void delete(Account account) {
        em.remove(account);
    }

    /**
     * Fetches an Account by its id.
     * @param id Identifier of the Account.
     * @return Account object if found, null otherwise.
     */
    public Account findById(long id) {
        return em.find(Account.class, id);
    }

    /**
     * Fetches a list of Accounts by a named query and parameters.
     * @param jpqlName Name of the JPQL query.
     * @param params Parameters for the query.
     * @return List of Account objects.
     */
    public List<Account> findByQuery(String jpqlName, Object... params) {
        var query = em.createNamedQuery(jpqlName, Account.class);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }

    /**
     * Fetches all Accounts from the database.
     * @return Array of all Account objects.
     */
    public Account[] getAll() {
        var accounts = em.createQuery("SELECT t FROM User t", Account.class).getResultList();
        return accounts.toArray(new Account[0]);
    }
}