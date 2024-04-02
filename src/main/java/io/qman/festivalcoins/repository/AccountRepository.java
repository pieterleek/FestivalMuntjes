package io.qman.festivalcoins.repository;

import io.qman.festivalcoins.entity.Account;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {

    private final EntityManager em;

    @Autowired
    public AccountRepository(EntityManager em) {
        this.em = em;
    }

    public Account get(String uid) {
        return em.find(Account.class, uid);
    }

    public void add(Account account) {
        em.persist(account);
    }

    public void update(Account account) {
        em.merge(account);
    }

    public void delete(Account account) {
        em.remove(account);
    }

    public Account findById(long id) {
        return em.find(Account.class, id);
    }

    public List<Account> findByQuery(String jpqlName, Object... params) {
        var query = em.createNamedQuery(jpqlName, Account.class);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }

    public Account[] getAll() {
        var accounts = em.createQuery("SELECT t FROM User t", Account.class).getResultList();
        return accounts.toArray(new Account[0]);
    }
}