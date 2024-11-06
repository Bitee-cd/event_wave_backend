package com.bitee.event;

import com.bitee.event.dao.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloWorld {
    @GetMapping
    public ResponseEntity<ApiResponse<String>> helloWorld() {
        ApiResponse response = ApiResponse.success("200","success","Hello world");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
