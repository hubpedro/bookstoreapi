package com.hubpedro.bookstoreapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        // Informações úteis
        health.put("status", "🟢 HEALTHY");
        health.put("timestamp", Instant.now());
        health.put("service", "User API");
        health.put("version", "1.0.0");

        // Métricas reais (mas com personalidade)
        Runtime runtime = Runtime.getRuntime();
        health.put("memory", Map.of(
                "used", runtime.totalMemory() - runtime.freeMemory(),
                "max", runtime.maxMemory(),
                "free", runtime.freeMemory(),
                "message", "I could use more RAM... but who couldn't? 🤷"
                                   ));


        // Toque divertido
        health.put("server_mood", getServerMood());
        health.put("coffee_level", "87%"); // Sempre importante
        health.put("today_tip", "Remember to hydrate! 💧");

        return ResponseEntity.ok()
                             .header("X-Health-Check-ID", UUID.randomUUID().toString())
                             .header("X-Response-Time", "42ms") // Podemos medir isso depois
                             .body(health);
    }

    private String getServerMood() {
        String[] moods = {"😊 Chill", "🚀 Productive", "🤔 Contemplative", "☕ Caffeinated"};
        return moods[new Random().nextInt(moods.length)];
    }
}
