package com.bitee.event.User;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface UserService {
    ResponseEntity<ApiResponse<User>> signup(@RequestBody UserRequest userRequest);
}
