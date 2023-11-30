package nl.pieterleek.festivalmuntjes.repository;

import jakarta.persistence.EntityManager;
import nl.pieterleek.festivalmuntjes.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.List;

@Repository
public class TagsMemoryRepostitory  implements EntityRepository<Tag> {

    EntityManager em;

    @Autowired
    public TagsMemoryRepostitory(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tag get(int id) {
        return em.find(Tag.class, id);
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
        em.flush();
    }

    @Override
    public void delete(int id) {
        em.remove(get(id));
        em.flush();
    }

    @Override
    public Tag[] getAll() {
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        return tags.toArray(new Tag[0]);
    }
}


