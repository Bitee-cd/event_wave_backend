package com.bitee.event.User;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "api/v1")
public interface UserController {

    @GetMapping(path="hello")
    ResponseEntity<ApiResponse<String>> helloWorld();

    @PostMapping(path="signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody UserRequest userRequest);
}
