package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.User.User;
import com.bitee.event.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Transactional
public class UserPrivilegeAssignmentControllerImpl implements UserPrivilegeAssignmentController{

    @Autowired
    UserPrivilegeAssignmentService userPrivilegeAssignmentService;
    @Override
    public ResponseEntity<ApiResponse<List<UserPrivilegeAssignment>>> getAllUserPrivilegeAssignment() {
        return userPrivilegeAssignmentService.getAllUserPrivilegeAssignment();
    }

    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> createPrivilege(CreateUserPrivilegeAssignmentDto createUserPrivilegeAssignmentDto) {
        return userPrivilegeAssignmentService.createPrivilege(createUserPrivilegeAssignmentDto);
    }

    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> getPrivilege(Long id) {
        return userPrivilegeAssignmentService.getPrivilege(id);
    }

    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> editPrivilege(Long id,CreateUserPrivilegeAssignmentDto editUserPrivilegeAssignmentDto) {
        return userPrivilegeAssignmentService.editPrivilege(id,editUserPrivilegeAssignmentDto);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deletePrivilege(Long id) {
        return userPrivilegeAssignmentService.deletePrivilege(id);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> createUserPrivileges(Long userid, List<Long> privilegeIds) {
        return userPrivilegeAssignmentService.createUserPrivileges(userid,privilegeIds);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getUserPrivileges(Long userid) {
        return userPrivilegeAssignmentService.getUserPrivileges(userid);
    }

    @Override
    public ResponseEntity<ApiResponse<List<User>>> getPrivilegeUsers(Long privilegeId) {
        return userPrivilegeAssignmentService.getPrivilegeUsers(privilegeId);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteUserPrivileges(Long userId) {
        return userPrivilegeAssignmentService.deleteUserPrivileges(userId);
    }
}
