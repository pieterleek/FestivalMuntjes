package io.qman.festivalcoins.controller;

import io.qman.festivalcoins.entity.Tag;
import io.qman.festivalcoins.notifications.NotificationDistributor;
import io.qman.festivalcoins.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a controller for handling tag related requests.
 * It is annotated as a RestController, meaning it's used to build REST API.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final NotificationDistributor notificationDistributor;

    /**
     * Constructor for the TagController class.
     * @param tagService an instance of TagService class
     * @param notificationDistributor an instance of NotificationDistributor class
     */
    @Autowired
    public TagController(TagService tagService, NotificationDistributor notificationDistributor) {
        this.tagService = tagService;
        this.notificationDistributor = notificationDistributor;
    }

    /**
     * This method is used to get all tags.
     * It is mapped to the /all endpoint and the HTTP GET method.
     * @return a list of all tags
     */
    @GetMapping(path = "all", produces = "application/json")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    /**
     * This method is used to get the value of a specific tag.
     * It is mapped to the /{tagId} endpoint and the HTTP GET method.
     * @param tagId the id of the tag
     * @return the value of the tag
     */
    @GetMapping(path = "{tagId}", produces = "application/json")
    public int getTagValue(@PathVariable String tagId) {
        Tag tag = tagService.getTag(tagId);
        return (tag != null) ? tag.getTagValue() : 0;
    }

    /**
     * This method is used to pay money from a tag.
     * It is mapped to the /pay/{amount} endpoint and the HTTP PUT method.
     * @param amount the amount to be paid
     * @param tag the tag from which the amount will be paid
     * @return a boolean indicating whether the payment was successful
     */
    @PutMapping(path = "pay/{amount}", produces = "application/json")
    public boolean payMoney(@PathVariable int amount, @RequestBody Tag tag) {
        notificationDistributor.broadcastToAll();
        return tagService.pay(amount, tag);
    }

    /**
     * This method is used to add money to a tag.
     * It is mapped to the /add/{amount} endpoint and the HTTP POST method.
     * @param amount the amount to be added
     * @param tag the tag to which the amount will be added
     * @return a boolean indicating whether the addition was successful
     */
    @PostMapping(path = "add/{amount}", produces = "application/json")
    public boolean addMoney(@PathVariable int amount, @RequestBody Tag tag) {
        notificationDistributor.broadcastToAll();
        return tagService.addMoney(amount, tag);
    }

    /**
     * This method is used to create a new tag.
     * It is mapped to the /newtag endpoint and the HTTP POST method.
     * @param tag the tag to be created
     */
    @PostMapping(path = "newtag", produces = "application/json")
    public void newTag(@RequestBody Tag tag) {
        notificationDistributor.broadcastToAll();
        Tag tag1 = new Tag();
        tag1.setTagUUID(tag.getTagUUID());
        tag1.setTagName(tag.getTagName());
        tag1.setTagValue(5);
        tagService.addTag(tag1);
    }
}