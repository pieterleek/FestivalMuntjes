package nl.pieterleek.festivalmuntjes.service;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.repository.EntityRepository;
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
    public Tag getTagValue(String tagUUID) {
        return tagRepository.get(tagUUID);
    }

    // Deduct money from tag
    public boolean pay(int amount, Tag tag) {
        // Check for negative amounts
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }

        synchronized (tag) {
            // Not enough money
            if (amount > tag.getTagValue()) {
                throw new IllegalStateException("Insufficient funds");
            }

            // Update tag value
            tag.setTagValue(tag.getTagValue() - amount);

            // Write to database
            updateTagInDatabase(tag);
            return true;
        }
    }

    // Add money to tag (placeholder implementation)
    public boolean addMoney(int amount, Tag tag) {
        // Check for negative amounts
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }

        // Implement logic for adding money to the tag here

        // Placeholder implementation always returns false
        return false;
    }

    // Add a new tag to the repository
    public String addTag(Tag tag) {
        return tagRepository.add(tag);
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
