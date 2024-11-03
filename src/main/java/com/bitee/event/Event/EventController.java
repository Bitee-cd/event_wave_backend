package com.bitee.event.Event;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("api/v1/event")
public interface EventController {
    @PostMapping("create")
    ResponseEntity<ApiResponse<String>> createEvent();

    @PutMapping("edit")
    ResponseEntity<ApiResponse<String>> editEvent();

    @GetMapping("")
    ResponseEntity<ApiResponse<ArrayList<Event>>> getAllEvents();

    @GetMapping("")
    ResponseEntity<ApiResponse<Event>> getSingleEvent(@RequestParam String eventId);


}
