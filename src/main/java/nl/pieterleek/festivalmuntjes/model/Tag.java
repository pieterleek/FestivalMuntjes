package nl.pieterleek.festivalmuntjes.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Random;
import java.util.UUID;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0L;
    private String tagUUID;
    private String tagName;
    private String secretKey;
    private int tagValue;

    public Tag() {
    }

    public static Tag createSample(int i) {
        Tag tag = new Tag();
        Random rand = new Random();
        tag.setTagName("Tag " + i);
        tag.setSecretKey("SecretKey " + i);
        tag.setTagValue(rand.nextInt(100));
        tag.tagUUID = UUID.randomUUID().toString();
        return tag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagUUID() {
        return tagUUID;
    }

    public void setTagUUID(String tagId) {
        this.tagUUID = tagId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getTagValue() {
        return tagValue;
    }

    public void setTagValue(int value) {
        this.tagValue = value;
    }

    public String getId() {
        return tagUUID;
    }

}
