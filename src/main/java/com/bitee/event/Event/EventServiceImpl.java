package com.bitee.event.Event;

import com.bitee.event.dao.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService{


    @Override
    public ResponseEntity<ApiResponse<String>> createEvent() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<String>> editEvent() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<ArrayList<Event>>> getAllEvents() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Event>> getSingleEvent(Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes() {

       List<Map<String,String>> eventTypes = Arrays.stream(EventType.values()).map(eventType -> {
            Map<String,String> option = new HashMap<>();
            option.put("label", eventType.name().charAt(0) + eventType.name().substring(1).toLowerCase());
            option.put("value",eventType.toString());
            return option;
        }).toList();
        return new ResponseEntity<>(ApiResponse.success("200","Event types retrieved successfully",eventTypes), HttpStatus.OK);
    }

}
