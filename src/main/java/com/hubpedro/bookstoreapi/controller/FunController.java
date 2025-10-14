package com.hubpedro.bookstoreapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/fun")
public class FunController {

    @GetMapping("/joke")
    public ResponseEntity<Map<String, String>> getDevJoke() {
        String[] jokes = {
                "Why do Java developers wear glasses? Because they can't C#!",
                "How many programmers does it take to change a light bulb? None, it's a hardware problem!",
                "I'd tell you a joke about UDP... but you might not get it.",
                "Why did the programmer quit his job? Because he didn't get arrays."
        };

        return ResponseEntity.ok(Map.of(
                "joke", jokes[new Random().nextInt(jokes.length)],
                "category", "programming",
                "laugh_level", "medium" // 😄
                                       ));
    }

    @GetMapping("/wisdom")
    public ResponseEntity<Map<String, String>> getWisdom() {
        return ResponseEntity.ok(Map.of(
                "advice", "Always code as if the person who ends up maintaining your code is a violent psychopath who knows where you live.",
                "author", "Anonymous Developer",
                "truth_level", "100%"
                                       ));
    }

    @GetMapping("/secret")
    public ResponseEntity<Map<String, String>> secretEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "You found the secret endpoint! 🎉",
                "achievement", "Curious Developer",
                "reward", "The satisfaction of discovery"
                                       ));
    }
}
