package io.qman.festivalcoins.service;

import io.qman.festivalcoins.entity.Tag;
import io.qman.festivalcoins.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides services related to Tag entities.
 * It is annotated as a Service, meaning it's a Spring-managed bean.
 */
@Service
public class TagService {

    // Repository for Tag entities
    private final EntityRepository<Tag> tagRepository;

    /**
     * Constructor for the TagService class.
     * @param tagRepository an instance of EntityRepository for Tag entities
     */
    @Autowired
    public TagService(EntityRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * This method retrieves a Tag entity by its UUID.
     * @param tagUUID the UUID of the tag
     * @return the Tag entity with the given UUID
     */
    public Tag getTag(String tagUUID) {
        return tagRepository.get(tagUUID);
    }

    /**
     * This method deducts a certain amount from a tag's value.
     * @param amount the amount to be deducted
     * @param tag the tag from which the amount will be deducted
     * @return a boolean indicating whether the deduction was successful
     */
    public boolean pay(int amount, Tag tag) {
        Tag tag1 = getTag(tag.getTagUUID());
        if (amount < 0 || amount > tag1.getTagValue()) {
            return false;
        }
        tag1.setTagValue(tag1.getTagValue() - amount);
        tagRepository.update(tag1);
        return true;
    }

    /**
     * This method adds a certain amount to a tag's value.
     * @param amount the amount to be added
     * @param tag the tag to which the amount will be added
     * @return a boolean indicating whether the addition was successful
     */
    public boolean addMoney(int amount, Tag tag) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        Tag tag1 = getTag(tag.getTagUUID());
        tag1.setTagValue(tag1.getTagValue() + amount);
        tagRepository.update(tag1);
        return true;
    }

    /**
     * This method adds a new tag to the repository.
     * @param tag the tag to be added
     */
    public void addTag(Tag tag) {
        tagRepository.add(tag);
    }

    /**
     * This method retrieves all tags from the repository.
     * @return a list of all tags
     */
    public List<Tag> getAllTags() {
        return List.of(tagRepository.getAll());
    }
}