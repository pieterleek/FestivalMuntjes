package nl.pieterleek.festivalmuntjes.service;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    EntityRepository<Tag> tagRepository;

    @Autowired
    public TagService(EntityRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getTagValue(String tagUUID) {
        return tagRepository.get(tagUUID);
    }

    public boolean pay(int amount, Tag tag) {
        tagRepository.update(tag);
        return false;
    }


    public boolean addMoney(int amount, Tag tag) {
        boolean added = false;
        return added;
    }

    public String addTag(Tag tag) {
        return tagRepository.add(tag);
    }

    public List<Tag> getAllTags() {
        return List.of(tagRepository.getAll());
    }
}
