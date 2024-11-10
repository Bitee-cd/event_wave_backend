package com.bitee.event.Privilege;

import com.bitee.event.Role.Role;
import com.bitee.event.Role.RoleRepository;
import com.bitee.event.User.UserRepository;
import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getAllPrivileges() {
        List<Privilege> privileges = privilegeRepository.findAll();

        return new ResponseEntity<>(ApiResponse.success("200", "Retrieved all privileges successfully", privileges), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<ApiResponse<Privilege>> createPrivilege(CreatePrivilegeRequestDto createPrivilegeRequestDto) {
        // Check if the user and role exist
        Optional<Role> optionalRole = roleRepository.findById(createPrivilegeRequestDto.getRoleid());

        if ( optionalRole.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", " Role not found", null), HttpStatus.BAD_REQUEST);
        }

        // Create new Privilege object
        Privilege privilege = new Privilege();
        privilege.setDescription(createPrivilegeRequestDto.getDescription());
        privilege.setRoleid(createPrivilegeRequestDto.getRoleid());
        privilege.setData(createPrivilegeRequestDto.getData());
        privilege.setRole(optionalRole.get());

        // Save the privilege
        Privilege savedPrivilege = privilegeRepository.save(privilege);

        return new ResponseEntity<>(ApiResponse.success("201", "Privilege created successfully", savedPrivilege), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Privilege>> getPrivilege(Long privilegeId) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(privilegeId);
        if (optionalPrivilege.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "Privilege not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.success("200", "Privilege retrieved successfully", optionalPrivilege.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Privilege>> editPrivilege(Long privilegeId, CreatePrivilegeRequestDto editPrivilegeDto) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(privilegeId);
        if (optionalPrivilege.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "Privilege not found", null), HttpStatus.NOT_FOUND);
        }

        // Check if the user and role exist
        Optional<Role> optionalRole = roleRepository.findById(editPrivilegeDto.getRoleid());

        if ( optionalRole.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Role not found", null), HttpStatus.BAD_REQUEST);
        }

        // Update the privilege
        Privilege privilege = optionalPrivilege.get();
        privilege.setDescription(editPrivilegeDto.getDescription());
        privilege.setRoleid(editPrivilegeDto.getRoleid());
        privilege.setData(editPrivilegeDto.getData());
        privilege.setRole(optionalRole.get());

        // Save the updated privilege
        Privilege updatedPrivilege = privilegeRepository.save(privilege);

        return new ResponseEntity<>(ApiResponse.success("200", "Privilege updated successfully", updatedPrivilege), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deletePrivilege(Long privilegeId) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(privilegeId);
        if (optionalPrivilege.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "Privilege not found", null), HttpStatus.NOT_FOUND);
        }

        // Delete the privilege
        privilegeRepository.deleteById(privilegeId);
        return new ResponseEntity<>(ApiResponse.success("200", "Privilege deleted successfully", null), HttpStatus.OK);
    }
}
