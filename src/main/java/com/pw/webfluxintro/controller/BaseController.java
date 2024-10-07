package com.pw.webfluxintro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/")
public class BaseController {

    @GetMapping
    public String get() {
        return """
            Hello and welcome to WebFluxIntro: <a href="/number">reactive stream of numbers</a>; <a href="/number/list">list of numbers</a>
        """;
    }

}
