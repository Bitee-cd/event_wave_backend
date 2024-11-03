package com.bitee.event.Event;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public interface EventService {

    ResponseEntity<ApiResponse<String>> createEvent();


    ResponseEntity<ApiResponse<String>> editEvent();


    ResponseEntity<ApiResponse<ArrayList<Event>>> getAllEvents();


    ResponseEntity<ApiResponse<Event>> getSingleEvent(@RequestParam String eventId);

}
