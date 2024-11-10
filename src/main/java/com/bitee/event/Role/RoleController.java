package com.bitee.event.Role;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("role")
public interface RoleController {
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Role>>> getAllRoles();
    @PutMapping("")
    ResponseEntity<ApiResponse<Role>> createPrivilege( @RequestBody CreateRoleRequestDto role);

    @GetMapping("{roleId}")
    ResponseEntity<ApiResponse<Role>> getRole(@PathVariable Long roleId);

    @PutMapping("{roleId}")
    ResponseEntity<ApiResponse<Role>> editRole(@PathVariable Long roleId, @RequestBody CreateRoleRequestDto role);

    @DeleteMapping("{roleId}")
    ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Long roleId);

    @PostMapping("{roleId}/assign/user/{userId}")
    ResponseEntity<ApiResponse<String>> assignUserRolePrivileges(@PathVariable("roleId") Long roleId,@PathVariable("userId") Long userId);

    @DeleteMapping("{roleId}/assign/user/{userId}")
    ResponseEntity<ApiResponse<String>> unAssignUserRolePrivileges(@PathVariable("roleId") Long roleId,@PathVariable("userId") Long userId);

    @GetMapping("{roleId}/privileges")
    ResponseEntity<ApiResponse<List<Privilege>>> getRolePrivileges(@PathVariable("roleId") Long roleId);

}
