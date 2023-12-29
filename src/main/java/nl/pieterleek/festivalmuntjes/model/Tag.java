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

    @Column
    private String tagUUID;

    @Column
    private String tagName;

    @Column
    private String secretKey;

    @Column
    private int tagValue;

    public Tag() {
        // Provide default values in the constructor if needed
        this.tagUUID = UUID.randomUUID().toString();
        this.tagName = "";
        this.secretKey = "";
        this.tagValue = 0;
    }

    public static Tag createSample(int i) {
        Tag tag = new Tag();
        Random rand = new Random();
        tag.setTagName("Tag " + i);
        tag.setSecretKey("SecretKey " + i);
        tag.setTagValue(rand.nextInt(10));
        return tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagUUID() {
        return tagUUID;
    }

    public void setTagUUID(String tagUUID) {
        this.tagUUID = tagUUID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public void setTagValue(int tagValue) {
        this.tagValue = tagValue;
    }
}
