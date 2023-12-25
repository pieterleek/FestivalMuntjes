package nl.pieterleek.festivalmuntjes.controller;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "all", produces = "application/json")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(path = "{tagUid}", produces = "application/json")
    public int getTagValue(@PathVariable() String tagUid)  {

        if (tagService.getTagValue(tagUid) != null) {
            return tagService.getTagValue(tagUid).getTagValue();
        }
       return 0;
    }

    @PutMapping(path = "{amount}", produces = "application/json")
    public boolean pay(@PathVariable() int amount, @RequestBody Tag tag) {
        return tagService.pay(amount, tag);
    }

    @PostMapping (path = "{amount}", produces = "application/json")
    public boolean add(@PathVariable() int amount, @RequestBody Tag tag) {
        return tagService.addMoney(amount, tag);
    }





}
