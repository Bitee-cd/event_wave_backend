package com.bitee.event;

import com.bitee.event.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorld {
    @GetMapping
    public ResponseEntity<ApiResponse<String>> helloWorld() {
        ApiResponse response = ApiResponse.success("200","success","Hello world there, you can proceed to do what you want to do. We are live!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
