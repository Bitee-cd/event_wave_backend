package com.bitee.event.EventTests;

import com.bitee.event.Event.CreateEventRequestDto;
import com.bitee.event.Event.Event;
import com.bitee.event.Event.EventServiceImpl;
import com.bitee.event.Event.EventType;
import com.bitee.event.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Date;

import static com.bitee.event.TestUtils.TEST_EVENT_LINK;
import static com.bitee.event.TestUtils.TEST_EVENT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventServiceImpl eventService;

    private String createTestToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, "test-secret-key")
                .compact();
    }
    @Test
    public void testCreateEvent_success() throws Exception {
        CreateEventRequestDto requestDto = new CreateEventRequestDto();
        Event event = new Event();
        event.setType(TEST_EVENT_TYPE);
        event.setMeetingLink(TEST_EVENT_LINK);
        String testToken = "Bearer test-jwt-token";

        when(eventService.createEvent(any(CreateEventRequestDto.class)))
                .thenReturn(new ResponseEntity<>(ApiResponse.success("201","Event created successfully",event), HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("201"));
        verify(eventService, times(1)).createEvent(any(CreateEventRequestDto.class));
    }

    @Test
    public void testCreateEvent_invalidRequest() throws Exception {
        CreateEventRequestDto invalidRequestDto = new CreateEventRequestDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequestDto)))
                .andExpect(status().isBadRequest());

        verify(eventService, never()).createEvent(any(CreateEventRequestDto.class));
    }

//    @Test
//    public void testEditEvent_success() throws Exception {
//        Long eventId = 1L;
//        CreateEventRequestDto requestDto = new CreateEventRequestDto();
//        Event updatedEvent = new Event();
//
//        when(eventService.editEvent(eq(eventId), any(CreateEventRequestDto.class)))
//                .thenReturn(ResponseEntity.ok(ApiResponse.success("200", "Event updated successfully", updatedEvent)));
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/event/{eventId}", eventId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.name").value("Updated Event"))
//                .andExpect(jsonPath("$.statusCode").value("200"));
//
//        verify(eventService, times(1)).editEvent(eq(eventId), any(CreateEventRequestDto.class));
//    }

//    @Test
//    public void testGetAllEvents_success() throws Exception {
//        List<Event> events = List.of(
//                new Event(1L, "Event 1", "Virtual", LocalDate.parse("2025-01-01")),
//                new Event(2L, "Event 2", "Physical", LocalDate.parse("2025-02-01"))
//        );
//
//        when(eventService.getAllEvents()).thenReturn(ResponseEntity.ok(ApiResponse.success("200", "Events retrieved successfully", events)));
//
//        mockMvc.perform(get("/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0].name").value("Event 1"))
//                .andExpect(jsonPath("$.statusCode").value("200"));
//
//        verify(eventService, times(1)).getAllEvents();
//    }

//    @Test
//    public void testGetSingleEvent_notFound() throws Exception {
//        Long eventId = 99L;
//
//        when(eventService.getSingleEvent(eventId))
//                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.error("400", "Event not found", null)));
//
//        mockMvc.perform(get("/event/{eventId}", eventId))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.statusCode").value("400"))
//                .andExpect(jsonPath("$.message").value("Event not found"));
//
//        verify(eventService, times(1)).getSingleEvent(eventId);
//    }

//    @Test
//    public void testDeleteSingleEvent_success() throws Exception {
//        Long eventId = 1L;
//
//        when(eventService.deleteSingleEvent(eventId))
//                .thenReturn(ResponseEntity.ok(ApiResponse.success("200", "Event deleted successfully", null)));
//
//        mockMvc.perform(delete("/event/{eventId}", eventId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value("200"))
//                .andExpect(jsonPath("$.message").value("Event deleted successfully"));
//
//        verify(eventService, times(1)).deleteSingleEvent(eventId);
//    }

//    @Test
//    public void testGetEventTypes_success() throws Exception {
//        List<Map<String, String>> eventTypes = List.of(
//                Map.of("type", "Virtual"),
//                Map.of("type", "Physical")
//        );
//
//        when(eventService.getEventTypes()).thenReturn(ResponseEntity.ok(ApiResponse.success("200", "Event types retrieved successfully", eventTypes)));
//
//        mockMvc.perform(get("/event/types"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0].type").value("Virtual"))
//                .andExpect(jsonPath("$.statusCode").value("200"));
//
//        verify(eventService, times(1)).getEventTypes();
//    }
}
