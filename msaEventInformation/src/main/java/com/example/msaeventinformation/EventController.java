package com.example.msaeventinformation;

import com.example.msaeventinformation.auth.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final TokenProvider tokenProvider;

    public EventController(EventService eventService, TokenProvider tokenProvider) {
        this.eventService = eventService;
        this.tokenProvider = tokenProvider;
    }
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    @PreAuthorize("hasAuthority('PROVIDER')")
    public Event createEvent(@RequestBody Event event, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        Long memberId = tokenProvider.getMemberIdFromToken(token);
        String memberEmail = tokenProvider.getEmailFromToken(token);

        return eventService.createEvent(event, memberId, memberEmail);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PROVIDER')")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails, @RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            Long memberId = tokenProvider.getMemberIdFromToken(token);
            String memberEmail = tokenProvider.getEmailFromToken(token);

            Event updatedEvent = eventService.updateEvent(id, eventDetails, memberId, memberEmail);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}