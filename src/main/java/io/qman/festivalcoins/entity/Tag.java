package io.qman.festivalcoins.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Random;
import java.util.UUID;

@Entity
public class Tag {

    @Id
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
