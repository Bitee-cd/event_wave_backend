package com.bitee.event.Privilege;

import com.bitee.event.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(name="privilege")
public interface PrivilegeController {
    @GetMapping("")
    ResponseEntity<ApiResponse<List<Privilege>>> getAllPrivileges();
    @PutMapping("")
    ResponseEntity<ApiResponse<Privilege>> createPrivilege(@Valid @RequestBody CreatePrivilegeRequestDto createPrivilegeRequestDto);

    @GetMapping("{privilegeId}")
    ResponseEntity<ApiResponse<Privilege>> getPrivilege(@PathVariable("privilegeId") Long PrivilegeId);

    @PutMapping("{privilegeId}")
    ResponseEntity<ApiResponse<Privilege>> editPrivilege(@PathVariable("privilegeId") Long PrivilegeId, @Valid @RequestBody CreatePrivilegeRequestDto editPrivilegeDto);

    @DeleteMapping("{privilegeId}")
    ResponseEntity<ApiResponse<String>> deletePrivilege(@PathVariable("privilegeId") Long PrivilegeId);



}
