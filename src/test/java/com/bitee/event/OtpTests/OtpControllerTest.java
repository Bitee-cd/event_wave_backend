package com.bitee.event.OtpTests;

import com.bitee.event.Otp.OtpService;
import com.bitee.event.utils.ApiResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OtpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OtpService otpService;

    @Test
    void regenerateOtp() throws Exception{
        String userDtoJson = "{\"email\": \"test@gmail.com\"}";


        when(otpService.regenerateOtp(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("200", "Success", null), HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/otp/regenerate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(jsonPath("$.responseMessage").value("Success"))
                .andExpect(jsonPath("$.responseCode").value("200"));
    }
    @Test
    void verifyOtp() throws Exception{
        String userDtoJson = "{\"email\": \"test@gmail.com\",\"otp\":\"013456\"}";


        when(otpService.verifyOtp(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("200", "Success", null), HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(jsonPath("$.responseMessage").value("Success"))
                .andExpect(jsonPath("$.responseCode").value("200"));
    }
}
