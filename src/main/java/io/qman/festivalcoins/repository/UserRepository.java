package io.qman.festivalcoins.repository;

import io.qman.festivalcoins.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepository implements EntityRepository<User> {

    EntityManager em;

    @Autowired
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User get(String uid) {
        return em.find(User.class, uid);
    }

    @Override
    public void add(User user) {
        em.persist(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    public void delete(int id) {
        em.remove(id);
    }

    @Override
    public User[] getAll() {
        List<User> users = em.createQuery("SELECT t FROM User t", User.class).getResultList();
        return users.toArray(new User[0]);
    }
}
