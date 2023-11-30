package nl.pieterleek.festivalmuntjes.controller;

import nl.pieterleek.festivalmuntjes.model.Tag;
import nl.pieterleek.festivalmuntjes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "{tagId}", produces = "application/json")
    public int getTagValue(@PathVariable() long tagId)  {
        return tagService.getTagValue(tagId);
    }

    @PutMapping(path = "{amount}", produces = "application/json")
    public boolean pay(@PathVariable() int amount, @RequestBody Tag tag) {
        return tagService.pay(amount, tag);
    }


    @PostMapping (path = "{amount}", produces = "application/json")
    public boolean add(@PathVariable() int amount, @RequestBody Tag tag) {
        return tagService.add(amount, tag);
    }





}
