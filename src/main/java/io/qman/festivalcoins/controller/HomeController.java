package io.qman.festivalcoins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    private static final String PATH = "/index";

    @RequestMapping(value = PATH)
    public String error() {
        return "index.html";
    }
}
