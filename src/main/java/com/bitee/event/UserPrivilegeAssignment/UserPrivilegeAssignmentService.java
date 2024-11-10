package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.User.User;
import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserPrivilegeAssignmentService {

    ResponseEntity<ApiResponse<List<UserPrivilegeAssignment>>> getAllUserPrivilegeAssignment();

    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> createPrivilege(CreateUserPrivilegeAssignmentDto createUserPrivilegeAssignmentDto);

    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> getPrivilege(Long id);

    ResponseEntity<ApiResponse<UserPrivilegeAssignment>> editPrivilege(Long id, CreateUserPrivilegeAssignmentDto editUserPrivilegeAssignmentDto);

    ResponseEntity<ApiResponse<String>> deletePrivilege(Long id);

    ResponseEntity<ApiResponse<String>> createPrivileges(Long userId, List<Privilege> privileges);

    ResponseEntity<ApiResponse<List<Privilege>>> getUserPrivileges(Long userId);

    ResponseEntity<ApiResponse<List<User>>> getPrivilegeUsers(Long privilegeId);

    ResponseEntity<ApiResponse<String>> deleteUserPrivileges(Long UserId);
}
