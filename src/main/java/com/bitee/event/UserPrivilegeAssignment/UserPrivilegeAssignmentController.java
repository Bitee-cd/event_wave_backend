package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.User.User;
import com.bitee.event.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("")
public interface UserPrivilegeAssignmentController {
    @GetMapping("user-privilege-assignment")
    ResponseEntity<ApiResponse<List<UserPrivilegeAssignment>>> getAllUserPrivilegeAssignment();
    @PostMapping("user-privilege-assignment")
    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> createPrivilege(@Valid @RequestBody CreateUserPrivilegeAssignmentDto createUserPrivilegeAssignmentDto);

    @GetMapping("user-privilege-assignment/{id}")
    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> getPrivilege(@PathVariable("id") Long id);

    @PutMapping("user-privilege-assignment/{id}")
    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> editPrivilege(@PathVariable("id") Long id,@Valid @RequestBody CreateUserPrivilegeAssignmentDto editUserPrivilegeAssignmentDto);

    @DeleteMapping("user-privilege-assignment/{id}")
    ResponseEntity<ApiResponse<String>> deletePrivilege(@PathVariable("id") Long id);

    @PostMapping("user/{userId}/privileges")
    ResponseEntity<ApiResponse<String>> createUserPrivileges(@PathVariable("userId") Long userId,@RequestBody  List<Long> privilegeIds);

    @GetMapping("user/{userId}/privileges")
    ResponseEntity<ApiResponse<List<Privilege>>> getUserPrivileges(@PathVariable("userId") Long userId);

    @GetMapping("privilege/{privilegeId}/users")
    ResponseEntity<ApiResponse<List<User>>> getPrivilegeUsers(@PathVariable("privilegeId") Long privilegeId);

    @DeleteMapping("user/{userId}/privileges")
    ResponseEntity<ApiResponse<String>> deleteUserPrivileges(@PathVariable("userId") Long userId);
}
