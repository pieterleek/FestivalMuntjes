package io.qman.festivalcoins.repository;

import io.qman.festivalcoins.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TagsMemoryRepostitory implements EntityRepository<Tag> {

    EntityManager em;

    @Autowired
    public TagsMemoryRepostitory(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tag get(String uid) {
        return em.find(Tag.class, uid);
    }

    @Override
    public void add(Tag tag) {
        em.persist(tag);
    }

    @Override
    public void update(Tag tag) {
        em.merge(tag);
    }

    @Override
    public void delete(Tag tag) {
        em.remove(tag);
    }

    @Override
    public void delete(int id) {
        em.remove(id);
    }

    @Override
    public Tag[] getAll() {
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        return tags.toArray(new Tag[0]);
    }
}


