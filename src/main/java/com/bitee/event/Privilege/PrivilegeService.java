package com.bitee.event.Privilege;

import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PrivilegeService {

    ResponseEntity<ApiResponse<List<Privilege>>> getAllPrivileges();


    ResponseEntity<ApiResponse<Privilege>> createPrivilege( CreatePrivilegeRequestDto createPrivilegeRequestDto);

    ResponseEntity<ApiResponse<Privilege>> getPrivilege( Long PrivilegeId);

    ResponseEntity<ApiResponse<Privilege>> editPrivilege( Long PrivilegeId,  CreatePrivilegeRequestDto createPrivilegeRequestDto);

    ResponseEntity<ApiResponse<String>> deletePrivilege( Long PrivilegeId);
}
