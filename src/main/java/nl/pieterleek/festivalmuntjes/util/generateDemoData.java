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

        // demo tags
        Tag tag = Tag.createSample(10);
        tag.setTagUUID("150437957093");
        this.tagRepository.add(tag);

        tag = Tag.createSample(11);
        tag.setTagUUID("83213421993");
        this.tagRepository.add(tag);

        tag = Tag.createSample(11);
        tag.setTagUUID("90614513592");
        this.tagRepository.add(tag);


        for (int i = 0; i < 5; i++) {
            this.tagRepository.add(Tag.createSample(i));
        }
    }
}
