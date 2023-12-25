package nl.pieterleek.festivalmuntjes.util;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class generateDemoData {

    EntityRepository<Tag> tagRepository;

    @Autowired
    public generateDemoData(EntityRepository<Tag> tagRepository) {
        this.tagRepository = tagRepository;
        createInitialTags();
    }

    /**
     * Creates 50 tags with random values
     */
    public void createInitialTags() {
        for (int i = 0; i < 50; i++) {
            String savedTag = this.tagRepository.add(Tag.createSample(i));
            System.out.println("Saved tag with id " + savedTag);
        }
    }
}
