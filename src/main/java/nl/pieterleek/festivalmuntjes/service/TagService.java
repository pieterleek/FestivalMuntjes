package nl.pieterleek.festivalmuntjes.service;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    EntityRepository<Tag> tagRepository;

    @Autowired
    public TagService(EntityRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

   public int getTagValue(long tagId) {
        return 0;

    }

    public boolean pay(int amount, Tag tag) {
       tagRepository.update(tag);
        return false;
    }


    public boolean add(int amount, Tag tag) {
        boolean added = false;
        return added;
    }
}
