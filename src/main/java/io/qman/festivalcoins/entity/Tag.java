package io.qman.festivalcoins.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Random;
import java.util.UUID;

/**
 * This class represents a Tag entity in the system.
 * It is annotated as a JPA Entity, meaning it will be mapped to a database table.
 */
@Entity
public class Tag {

    @Id
    @Column
    // UUID of the tag, serves as the primary key in the database table
    private String tagUUID;

    @Column
    // Name of the tag
    private String tagName;

    @Column
    // Secret key associated with the tag
    private String secretKey;

    @Column
    // Value of the tag
    private int tagValue;

    /**
     * Default constructor for the Tag class.
     * Initializes the tagUUID with a random UUID, and other fields with default values.
     */
    public Tag() {
        this.tagUUID = UUID.randomUUID().toString();
        this.tagName = "";
        this.secretKey = "";
        this.tagValue = 0;
    }

    /**
     * Static factory method to create a sample Tag instance.
     * @param i an integer to differentiate multiple sample tags
     * @return a new Tag instance with sample values
     */
    public static Tag createSample(int i) {
        Tag tag = new Tag();
        Random rand = new Random();
        tag.setTagName("Tag " + i);
        tag.setSecretKey("SecretKey " + i);
        tag.setTagValue(rand.nextInt(10));
        return tag;
    }

    // Getter and setter methods for the fields

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

    /**
     * Overrides the default toString method.
     * @return a string representation of the Tag instance
     */
    @Override
    public String toString() {
        return "Tag{" +
                ", tagUUID='" + tagUUID + '\'' +
                ", tagName='" + tagName + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", tagValue=" + tagValue +
                '}';
    }
}