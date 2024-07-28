package com.example.msaeventinformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event, Long memberId, String memberEmail) {
        event.setMemberId(memberId);
        event.setMemberEmail(memberEmail);
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails, Long memberId, String memberEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no event with : " + id));

        // 이벤트를 생성한 사용자만 수정할 수 있도록 체크
        if (!event.getMemberId().equals(memberId)) {
            throw new RuntimeException("no permission");
        }

        event.setName(eventDetails.getName());
        event.setImage(eventDetails.getImage());
        event.setCast(eventDetails.getCast());
        event.setDescription(eventDetails.getDescription());
        event.setVenue(eventDetails.getVenue());
        event.setSeatsAndPrices(eventDetails.getSeatsAndPrices());
        event.setEventTime(eventDetails.getEventTime());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setBookingStartDate(eventDetails.getBookingStartDate());
        event.setBookingEndDate(eventDetails.getBookingEndDate());

        return eventRepository.save(event);
    }
}