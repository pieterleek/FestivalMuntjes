package nl.pieterleek.festivalmuntjes.controller;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.notifications.NotificationDistributor;
import nl.pieterleek.festivalmuntjes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final NotificationDistributor notificationDistributor;

    @Autowired
    public TagController(TagService tagService, NotificationDistributor notificationDistributor) {
        this.tagService = tagService;
        this.notificationDistributor = notificationDistributor;
    }

    @GetMapping(path = "all", produces = "application/json")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(path = "{tagId}", produces = "application/json")
    public int getTagValue(@PathVariable String tagId) {
        notificationDistributor.broadcastToAll();
        Tag tag = tagService.getTagValue(tagId);

        return (tag != null) ? tag.getTagValue() : 0;
    }

    @PutMapping(path = "pay/{amount}", produces = "application/json")
    public boolean payMoney(@PathVariable int amount, @RequestBody Tag tag) {
        return tagService.pay(amount, tag);
    }

    @PostMapping(path = "add/{amount}", produces = "application/json")
    public boolean addMoney(@PathVariable int amount, @RequestBody Tag tag) {
        return tagService.addMoney(amount, tag);

    }
}
