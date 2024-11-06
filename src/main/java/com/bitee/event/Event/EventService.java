package com.bitee.event.Event;

import com.bitee.event.dao.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface EventService {

    ResponseEntity<ApiResponse<String>> createEvent();


    ResponseEntity<ApiResponse<String>> editEvent();


    ResponseEntity<ApiResponse<ArrayList<Event>>> getAllEvents();


    ResponseEntity<ApiResponse<Event>> getSingleEvent(Long eventId);

    ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes();

}
