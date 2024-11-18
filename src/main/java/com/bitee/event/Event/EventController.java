package com.bitee.event.Event;

import com.bitee.event.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/event")
public interface EventController {
    @PostMapping("")
    ResponseEntity<ApiResponse<Event>> createEvent(@Valid @RequestBody CreateEventRequestDto createEventRequestDto);

    @PutMapping("{eventId}")
    ResponseEntity<ApiResponse<Event>> editEvent(@PathVariable("eventId") Long eventId ,@Valid @RequestBody CreateEventRequestDto editEventRequestDto );

    @GetMapping("")
    ResponseEntity<ApiResponse<List<Event>>> getAllEvents();

    @GetMapping("{eventId}")
    ResponseEntity<ApiResponse<Event>> getSingleEvent(@Valid @PathVariable("eventId") Long eventId);

    @DeleteMapping("{eventId}")
    ResponseEntity<ApiResponse<String>> deleteSingleEvent(@Valid @PathVariable("eventId") Long eventId);

    @GetMapping("types")
    ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes();


}
