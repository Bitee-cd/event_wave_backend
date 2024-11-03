package com.bitee.event.Event;

import com.bitee.event.Tag.Tag;
import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    public ResponseEntity<ApiResponse<Event>> getSingleEvent(String eventId) {
        return null;
    }

}
