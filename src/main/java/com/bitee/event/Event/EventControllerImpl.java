package com.bitee.event.Event;

import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EventControllerImpl implements EventController{
    @Autowired
    EventService eventService;
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

      return  eventService.getAllEvents();
    }

    @Override
    public ResponseEntity<ApiResponse<Event>> getSingleEvent(Long eventId) {
        return eventService.getSingleEvent(eventId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Map<String,String>>>> getEventTypes() {
        return eventService.getEventTypes();
    }

}
