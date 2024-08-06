package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return webClientBuilder.build()
                .get()
                .uri("http://app2-app2.svc.cluster.local/greet")
                .retrieve()
                .bodyToMono(String.class);
    }
}