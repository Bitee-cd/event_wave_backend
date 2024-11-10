package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.Privilege.Privilege;
import com.bitee.event.Privilege.PrivilegeRepository;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserPrivilegeAssignmentServiceImpl implements UserPrivilegeAssignmentService {
    @Autowired
    UserPrivilegeAssignmentRepository userPrivilegeAssignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public ResponseEntity<ApiResponse<List<UserPrivilegeAssignment>>> getAllUserPrivilegeAssignment() {
        List<UserPrivilegeAssignment> assignments = userPrivilegeAssignmentRepository.findAll();


        return new ResponseEntity<>(ApiResponse.success("200", "Retrieved all user privilege assignments", assignments), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> createPrivilege(CreateUserPrivilegeAssignmentDto createUserPrivilegeAssignmentDto) {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(createUserPrivilegeAssignmentDto.getUserid());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);
        }

        // Check if the privilege exists
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(createUserPrivilegeAssignmentDto.getPrivilegeid());
        if (optionalPrivilege.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Privilege not found", null), HttpStatus.BAD_REQUEST);
        }

        // Create and save new user privilege assignment
        UserPrivilegeAssignment assignment = new UserPrivilegeAssignment();
        assignment.setUser(optionalUser.get());
        assignment.setUserid(createUserPrivilegeAssignmentDto.getUserid());
        assignment.setPrivilegeid(createUserPrivilegeAssignmentDto.getPrivilegeid());
        assignment.setPrivilege(optionalPrivilege.get());

        UserPrivilegeAssignment savedAssignment = userPrivilegeAssignmentRepository.save(assignment);

        return new ResponseEntity<>(ApiResponse.success("201", "User privilege assignment created successfully", savedAssignment), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> getPrivilege(Long id) {
        Optional<UserPrivilegeAssignment> assignment = userPrivilegeAssignmentRepository.findById(id);
        if (assignment.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "User privilege assignment not found", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.success("200", "User privilege assignment retrieved successfully", assignment.get()), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse<UserPrivilegeAssignment>> editPrivilege(Long id, CreateUserPrivilegeAssignmentDto editUserPrivilegeAssignmentDto) {
        Optional<UserPrivilegeAssignment> existingAssignment = userPrivilegeAssignmentRepository.findById(id);
        if (existingAssignment.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "User privilege assignment not found", null), HttpStatus.NOT_FOUND);
        }

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(editUserPrivilegeAssignmentDto.getUserid());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);
        }

        // Check if the privilege exists
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(editUserPrivilegeAssignmentDto.getPrivilegeid());
        if (optionalPrivilege.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Privilege not found", null), HttpStatus.BAD_REQUEST);
        }

        // Update the assignment
        UserPrivilegeAssignment assignment = existingAssignment.get();
        assignment.setUser(optionalUser.get());
        assignment.setUserid(editUserPrivilegeAssignmentDto.getUserid());
        assignment.setPrivilege(optionalPrivilege.get());
        assignment.setPrivilegeid(editUserPrivilegeAssignmentDto.getPrivilegeid());

        UserPrivilegeAssignment updatedAssignment = userPrivilegeAssignmentRepository.save(assignment);

        return new ResponseEntity<>(ApiResponse.success("200", "User privilege assignment updated successfully", updatedAssignment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deletePrivilege(Long id) {
        Optional<UserPrivilegeAssignment> assignment = userPrivilegeAssignmentRepository.findById(id);
        if (assignment.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("404", "User privilege assignment not found", null), HttpStatus.NOT_FOUND);
        }

        // Delete the assignment
        userPrivilegeAssignmentRepository.deleteById(id);

        return new ResponseEntity<>(ApiResponse.success("200", "User privilege assignment deleted successfully", null), HttpStatus.OK);
    }



    @Transactional
    public ResponseEntity<ApiResponse<String>> createUserPrivileges(Long userId, List<Long> privilegeIds) {
        try {
            // Fetch the user by ID
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);

            }

            // Delete existing privileges assigned to the user
            userPrivilegeAssignmentRepository.deleteByUserId(userId);
            User user = optionalUser.get();

            // Fetch privileges by IDs and cross-check
            List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);
            if (privileges.size() != privilegeIds.size()) {
                return new ResponseEntity<>(ApiResponse.error("400", "Privileges  not found", null), HttpStatus.BAD_REQUEST);

            }

            // Map privileges to UserPrivilegeAssignment entities
            List<UserPrivilegeAssignment> userPrivilegeAssignments = privileges.stream()
                    .map(privilege -> {
                        UserPrivilegeAssignment assignment = new UserPrivilegeAssignment();
                        assignment.setUser(user);
                        assignment.setUserid(user.getId());
                        assignment.setPrivilege(privilege);
                        assignment.setPrivilegeid(privilege.getId());
                        return assignment;
                    })
                    .toList();

            // Save all assignments
            userPrivilegeAssignmentRepository.saveAll(userPrivilegeAssignments);

            return new ResponseEntity<>(ApiResponse.success("201", "Created", null), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("500", "Something went wrong", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getUserPrivileges(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);

        }
        User user = optionalUser.get();
        List<UserPrivilegeAssignment> assignments = userPrivilegeAssignmentRepository.findByUserId(userId);

        System.out.println("assignments for user:"+ "userId"+" "+ ": "+assignments);


        List<Privilege> privileges = assignments.stream().map(UserPrivilegeAssignment::getPrivilege).toList();

        return new ResponseEntity<>(ApiResponse.success("200", "Retrieved User Privileges Successfully", privileges), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<User>>> getPrivilegeUsers(Long privilegeId) {
        List<UserPrivilegeAssignment> assignments = userPrivilegeAssignmentRepository.findByPrivilegeId(privilegeId);

        List<User> users = assignments.stream().map(UserPrivilegeAssignment::getUser).toList();

        return new ResponseEntity<>(ApiResponse.success("200", "Retrieved Users for Privilege Successfully", users), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> deleteUserPrivileges(Long userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>(ApiResponse.error("400", "User not found", null), HttpStatus.BAD_REQUEST);

            }
            userPrivilegeAssignmentRepository.deleteByUserId(userId);
            return new ResponseEntity<>(ApiResponse.success("200", "User Privileges successfully cleared", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("500", "Something went wrong", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
