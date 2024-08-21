package com.bitee.event.User;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController{
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<ApiResponse<String>> helloWorld() {
        ApiResponse response = ApiResponse.success("200","success","HEllo world");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<User>> signup(UserRequest userRequest) {
        return userService.signup(userRequest);
    }
}
