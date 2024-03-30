package io.qman.festivalcoins.service;

import io.qman.festivalcoins.repository.EntityRepository;
import io.qman.festivalcoins.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final EntityRepository<Tag> tagRepository;

    @Autowired
    public TagService(EntityRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Get tag value by UUID
    public Tag getTag(String tagUUID) {
        return tagRepository.get(tagUUID);
    }

    // Deduct money from tag
    public boolean pay(int amount, Tag tag) {

        Tag tag1 = getTag(tag.getTagUUID());
        // Check for negative amounts
        if (amount < 0) {
            return false;
        }

        synchronized (tag1) {
            // Not enough money
            if (amount > tag1.getTagValue()) {
                return false;
            }

            // Update tag value
            tag1.setTagValue(tag1.getTagValue() - amount);

            // Write to database
            updateTagInDatabase(tag1);
            return true;
        }
    }

    // Add money to tag (placeholder implementation)
    public boolean addMoney(int amount, Tag tag) {
        // Check for negative amounts
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }

        Tag tag1 = getTag(tag.getTagUUID());
        tag1.setTagValue(tag1.getTagValue() + amount);
        System.out.println("TAG" + tag1.getTagValue());
        updateTagInDatabase(tag1);
        return true;
    }

    // Add a new tag to the repository
    public void addTag(Tag tag) {
        tagRepository.add(tag);
    }

    // Get a list of all tags
    public List<Tag> getAllTags() {
        return List.of(tagRepository.getAll());
    }

    private void updateTagInDatabase(Tag tag) {
        // Logic to update tag in the database
        tagRepository.update(tag);
    }
}
