package com.bitee.event.Role;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.Privilege.PrivilegeRepository;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.UserPrivilegeAssignment.UserPrivilegeAssignment;
import com.bitee.event.UserPrivilegeAssignment.UserPrivilegeAssignmentRepository;
import com.bitee.event.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//TODO remove DI of services in services
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserPrivilegeAssignmentRepository userPrivilegeAssignmentRepository;

    @Override
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<>(ApiResponse.success("200", "Roles retrieved successfully", roles), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> createPrivilege(CreateRoleRequestDto createRoleRequestDto) {

        Optional<Role> existingRole = roleRepository.findByData(createRoleRequestDto.getData());

        if (existingRole.isPresent()) {
            return new ResponseEntity<>(ApiResponse.error("409", "Role with the given data already exists", null), HttpStatus.CONFLICT);
        }

        Role role = new Role();
        role.setDescription(createRoleRequestDto.getDescription());
        role.setData(createRoleRequestDto.getData());

        roleRepository.save(role);

        return new ResponseEntity<>(ApiResponse.success("201", "Role created successfully", role), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> getRole(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        return optionalRole.map(role -> new ResponseEntity<>(ApiResponse.success("200", "Role retrieved successfully", role), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(ApiResponse.error("404", "Role not found", null), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Role>> editRole(Long roleId, CreateRoleRequestDto editRoleDto) {
        Optional<Role> existingRole = roleRepository.findById(roleId);
        if (existingRole.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "Role not found", null), HttpStatus.NOT_FOUND);
        }
        Role role = existingRole.get();
        role.setDescription(editRoleDto.getDescription());
        role.setData(editRoleDto.getData());

        roleRepository.save(role);

        return new ResponseEntity<>(ApiResponse.success("200", "Role updated successfully", role), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteRole(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "Role not found", null), HttpStatus.NOT_FOUND);
        }
        roleRepository.deleteById(roleId);
        return new ResponseEntity<>(ApiResponse.success("200", "Role deleted successfully", null), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> assignUserRolePrivileges(Long roleId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty() || optionalUser.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Details", null), HttpStatus.BAD_REQUEST);
        }
        List<Privilege> privileges = privilegeRepository.findByRoleId(roleId);
        User user = optionalUser.get();

        List<Long> privilegeIds = privileges.stream()
                .map(Privilege::getId)
                .collect(Collectors.toList());

        // Delete all existing privilege assignments for this user and these privilege IDs in one query
        userPrivilegeAssignmentRepository.deleteByUserIdAndPrivilegeIds(userId, privilegeIds);


        List<UserPrivilegeAssignment> assignments = privileges.stream().map(
                privilege -> {
                    UserPrivilegeAssignment userPrivilegeAssignment = new UserPrivilegeAssignment();
                    userPrivilegeAssignment.setUser(user);
                    userPrivilegeAssignment.setPrivilege(privilege);
                    userPrivilegeAssignment.setPrivilegeid(privilege.getId());
                    userPrivilegeAssignment.setUserid(userId);
                    return userPrivilegeAssignment;
                }
        ).toList();

        userPrivilegeAssignmentRepository.saveAll(assignments);

          return new ResponseEntity<>(ApiResponse.success("200", "Assigned All Privileges for Role Successful", null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> unAssignUserRolePrivileges(Long roleId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty() || optionalUser.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Details", null), HttpStatus.BAD_REQUEST);
        }
        List<Privilege> privileges = privilegeRepository.findByRoleId(roleId);
        User user = optionalUser.get();

        List<Long> privilegeIds = privileges.stream()
                .map(Privilege::getId)
                .collect(Collectors.toList());

        // Delete all existing privilege assignments for this user and these privilege IDs in one query
        userPrivilegeAssignmentRepository.deleteByUserIdAndPrivilegeIds(userId, privilegeIds);
        return new ResponseEntity<>(ApiResponse.success("200", "Deleted All Privileges for Role Successful", null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getRolePrivileges(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty() ) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Details", null), HttpStatus.BAD_REQUEST);
        }
        List<Privilege> privileges = privilegeRepository.findByRoleId(roleId);
        return new ResponseEntity<>(ApiResponse.success("200", "Retrieved Privileges for Role Successful", privileges), HttpStatus.OK);
    }
}
