package com.example.demo;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PodController {

    @PostMapping("/createPodYaml")
    public ResponseEntity<String> createPodYaml(@RequestBody Pod pod) throws IOException {

        String name = pod.getName();
        String containerName = pod.getContainerName();
        String image = pod.getImage();

        // YAML 콘텐츠 생성
        String podYaml = """
                apiVersion: v1
                kind: Pod
                metadata:
                  name: %s
                spec:
                  containers:
                  - name: %s
                    image: %s
                """.formatted(name, containerName, image);

        Path resourceDirectory = Paths.get("src", "main", "resources");
        File file = new File(resourceDirectory.toFile(), name + ".yaml");

        if (!Files.exists(resourceDirectory)) {
            Files.createDirectories(resourceDirectory);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(podYaml);
        }

        String successMessage = "ok";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }
}