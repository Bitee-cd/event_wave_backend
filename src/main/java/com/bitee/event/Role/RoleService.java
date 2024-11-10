package com.bitee.event.Role;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RoleService {

    ResponseEntity<ApiResponse<List<Role>>> getAllRoles();

    ResponseEntity<ApiResponse<Role>> createPrivilege( @RequestBody CreateRoleRequestDto role);


    ResponseEntity<ApiResponse<Role>> getRole(@PathVariable Long roleId);


    ResponseEntity<ApiResponse<Role>> editRole(@PathVariable Long roleId, @RequestBody CreateRoleRequestDto role);


    ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Long roleId);
    ResponseEntity<ApiResponse<String>> assignUserRolePrivileges(Long roleId, Long userId);

    ResponseEntity<ApiResponse<String>> unAssignUserRolePrivileges(Long roleId, Long userId);

    ResponseEntity<ApiResponse<List<Privilege>>> getRolePrivileges(@PathVariable("roleId") Long roleId);
}
