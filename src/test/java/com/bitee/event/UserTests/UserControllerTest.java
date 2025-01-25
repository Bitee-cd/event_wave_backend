package com.bitee.event.UserTests;


import com.bitee.event.User.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@WebMvcTest(controllers = UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void registerUserSuccess() throws Exception {
        String userDtoJson = "{\"firstName\": \"Test\",\"lastName\": \"Test\", \"email\": \"test@gmail.com\", \"password\": \"Password01?\", \"phoneNumber\":\"07062314249\"}";


        when(userService.signup(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("201", "OTP sent to email for verification", null), HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(jsonPath("$.responseMessage").value("OTP sent to email for verification"))
                .andExpect(jsonPath("$.responseCode").value("201"));
    }

    @Test
    public void loginUserSuccess() throws Exception {
        String loginDtoJson = "{\"email\":\"Test@gmail.com\",\"password\":\"Password01?\" }";
        Map<String,String> data = new HashMap<>();
        data.put("token","sample-jwt-token");

        when(userService.login(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("200","Success",data),HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.responseCode").value("200"))
                .andExpect(jsonPath("$.responseMessage").value("Success"))
                .andExpect(jsonPath("$.data.token").value("sample-jwt-token"));
    }

    @Test
    public void forgotPassword() throws Exception{
        String forgotPasswordDto="{\"email\":\"Test@gmail.com\"}";

        when(userService.forgotPassword(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("200","Email sent for confirmation",null),HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/password/forgot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(forgotPasswordDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.responseCode").value("200"))
                .andExpect(jsonPath("$.responseMessage").value("Email sent for confirmation"));

    }

    @Test
    public void changePassword() throws Exception{
        String changePasswordDto ="{\"otp\":\"000000\",\"password\":\"TestPassword0?\",\"email\":\"Test@gmail.com\"}";

        when(userService.changePassword(any())).thenReturn(new ResponseEntity<>(ApiResponse.success("200","Success",null),HttpStatus.OK));
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/password/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(changePasswordDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.responseCode").value("200"))
                .andExpect(jsonPath("$.responseMessage").value("Success"));
    }
}
