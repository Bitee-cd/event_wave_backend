package com.bitee.event.Event;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class EventControllerImpl implements EventController{
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
    public ResponseEntity<ApiResponse<Event>> getSingleEvent(String eventId) {
        return null;
    }

}
