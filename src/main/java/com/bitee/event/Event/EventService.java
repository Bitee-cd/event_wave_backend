package com.bitee.event.Event;

import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EventService {

    ResponseEntity<ApiResponse<Event>> createEvent(CreateEventRequestDto createEventRequestDto);


    ResponseEntity<ApiResponse<Event>> editEvent(Long eventId, CreateEventRequestDto editEventRequestDto);


    ResponseEntity<ApiResponse<List<Event>>> getAllEvents();


    ResponseEntity<ApiResponse<Event>> getSingleEvent(Long eventId);

    ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes();

}
