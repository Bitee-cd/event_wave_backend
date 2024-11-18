package com.bitee.event.Event;

import com.bitee.event.Config.JWTFilter;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


//TODO handle images


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTFilter jwtFilter;


        @Override
        public ResponseEntity<ApiResponse<Event>> createEvent(CreateEventRequestDto createEventRequestDto) {
            if(Objects.isNull(createEventRequestDto.getType()) ||
                    (!createEventRequestDto.getType().equals(EventType.PHYSICAL) && !createEventRequestDto.getType().equals(EventType.VIRTUAL))) {
                return new ResponseEntity<>(ApiResponse.error("400", "Event type is not valid", null), HttpStatus.BAD_REQUEST);
            }
            Event event = new Event();
            User user = userRepository.findByUserEmail(jwtFilter.getCurrentUser());

            if (user == null) {
                return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);
            }

            if (createEventRequestDto.getType().equals(EventType.PHYSICAL)) {
                if (createEventRequestDto.getAddress().isEmpty()) {
                    new ResponseEntity<>(ApiResponse.error("400", "Event Location is required", null), HttpStatus.BAD_REQUEST);
                }
                event.setAddress(createEventRequestDto.getAddress());

            } else if (createEventRequestDto.getType().equals(EventType.VIRTUAL)) {
                if (createEventRequestDto.getMeetingLink().isEmpty()) {
                    return new ResponseEntity<>(ApiResponse.error("400", "Meeting Link is required", null), HttpStatus.BAD_REQUEST);
                }
                event.setMeetingLink(createEventRequestDto.getMeetingLink());
            }
            event.setImage(event.getImage());
            event.setCreatedAt(new Date());
            event.setEndDate(createEventRequestDto.getEndDate());
            event.setStartDate(createEventRequestDto.getStartDate());
            event.setPrice(createEventRequestDto.getPrice());
            event.setUser(user);

            eventRepository.save(event);

            return new ResponseEntity<>(ApiResponse.error("200", "Event successfully created", event), HttpStatus.OK);

        }

    @Override
    public ResponseEntity<ApiResponse<Event>> editEvent(Long eventId, CreateEventRequestDto editEventRequestDto) {
        // Retrieve the event by ID
        Optional<Event> maybeEvent = eventRepository.findById(eventId);
        if (maybeEvent.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Event not found", null), HttpStatus.BAD_REQUEST);
        }

        Event event = maybeEvent.get();

        // Validate event type
        if (Objects.isNull(editEventRequestDto.getType()) ||
                (!editEventRequestDto.getType().equals(EventType.PHYSICAL) &&
                        !editEventRequestDto.getType().equals(EventType.VIRTUAL))) {
            return new ResponseEntity<>(ApiResponse.error("400", "Event type is not valid", null), HttpStatus.BAD_REQUEST);
        }

        // Check fields based on event type
        if (editEventRequestDto.getType().equals(EventType.PHYSICAL)) {
            if (editEventRequestDto.getAddress() == null || editEventRequestDto.getAddress().isEmpty()) {
                return new ResponseEntity<>(ApiResponse.error("400", "Event Location is required for a physical event", null), HttpStatus.BAD_REQUEST);
            }
            event.setAddress(editEventRequestDto.getAddress());
            event.setMeetingLink(null); // Clear meeting link if switching to physical
        } else if (editEventRequestDto.getType().equals(EventType.VIRTUAL)) {
            if (editEventRequestDto.getMeetingLink() == null || editEventRequestDto.getMeetingLink().isEmpty()) {
                return new ResponseEntity<>(ApiResponse.error("400", "Meeting Link is required for a virtual event", null), HttpStatus.BAD_REQUEST);
            }
            event.setMeetingLink(editEventRequestDto.getMeetingLink());
            event.setAddress(null); // Clear address if switching to virtual
        }

        // Update other event details
        event.setType(editEventRequestDto.getType());
        event.setLocation(editEventRequestDto.getLocation());
        event.setImage(editEventRequestDto.getImage());
        event.setDescription(editEventRequestDto.getDescription());
        event.setPrice(editEventRequestDto.getPrice());
        event.setStartDate(editEventRequestDto.getStartDate());
        event.setEndDate(editEventRequestDto.getEndDate());
        event.setTags(editEventRequestDto.getTags());

        // Save the updated event
        eventRepository.save(event);

        return new ResponseEntity<>(ApiResponse.success("200", "Event successfully updated", event), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return new ResponseEntity<>(ApiResponse.success("200", "Events retrieved successfully", events), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Event>> getSingleEvent(Long eventId) {
        Optional<Event> maybeEvent = eventRepository.findById(eventId);
        if (maybeEvent.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Event not found", null), HttpStatus.BAD_REQUEST);
        }
        Event event = maybeEvent.get();
        return new ResponseEntity<>(ApiResponse.success("200", "Events retrieved successfully", event), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getEventTypes() {
        List<Map<String, String>> eventTypes = Arrays.stream(EventType.values()).map(eventType -> {
            Map<String, String> option = new HashMap<>();
            option.put("label", eventType.name().charAt(0) + eventType.name().substring(1).toLowerCase());
            option.put("value", eventType.toString());
            return option;
        }).toList();
        return new ResponseEntity<>(ApiResponse.success("200", "Event types retrieved successfully", eventTypes), HttpStatus.OK);
    }

}
