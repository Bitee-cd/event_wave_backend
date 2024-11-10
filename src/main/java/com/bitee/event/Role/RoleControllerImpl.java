package com.bitee.event.Role;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Transactional
public class RoleControllerImpl implements RoleController{
    @Autowired
    private RoleService roleService;
    @Override
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        return roleService.getAllRoles();
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> createPrivilege(CreateRoleRequestDto role) {
        return roleService.createPrivilege(role);
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> getRole(Long roleId) {
        return roleService.getRole(roleId);
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> editRole(Long roleId, CreateRoleRequestDto role) {
        return roleService.editRole(roleId,role);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteRole(Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> assignUserRolePrivileges(Long roleId, Long userId) {
        return roleService.assignUserRolePrivileges(roleId,userId);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> unAssignUserRolePrivileges(Long roleId, Long userId) {
        return roleService.unAssignUserRolePrivileges(roleId,userId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getRolePrivileges(Long roleId) {
        return roleService.getRolePrivileges(roleId);
    }
}
