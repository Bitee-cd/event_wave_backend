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
    @PostMapping("create")
    ResponseEntity<ApiResponse<String>> createEvent();

    @PutMapping("edit")
    ResponseEntity<ApiResponse<String>> editEvent();

    @GetMapping("")
    ResponseEntity<ApiResponse<ArrayList<Event>>> getAllEvents();

    @GetMapping("/{eventId}")
    ResponseEntity<ApiResponse<Event>> getSingleEvent(@Valid @PathVariable Long eventId);

    @GetMapping("types")
    ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes();


}
