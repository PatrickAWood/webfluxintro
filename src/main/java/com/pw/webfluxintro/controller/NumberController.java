package com.pw.webfluxintro.controller;

import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

@Controller
@ResponseBody
@RequestMapping("/number")
@Slf4j
public class NumberController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> get() {
        return getNumbers();
    }

    @GetMapping("/list")
    public Mono<List<Integer>> getList() {
        return getNumbers().collectList();
    }

    private static Flux<Integer> getNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000))
                .doOnSubscribe(subscription -> log.info("Retrieving numbers"));
    }
}