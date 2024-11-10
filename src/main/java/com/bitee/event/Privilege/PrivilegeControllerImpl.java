package com.bitee.event.Privilege;

import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PrivilegeControllerImpl implements  PrivilegeController{
    @Autowired
    PrivilegeService privilegeService;
    @Override
    public ResponseEntity<ApiResponse<List<Privilege>>> getAllPrivileges() {
        return privilegeService.getAllPrivileges();
    }

    @Override
    public ResponseEntity<ApiResponse<Privilege>> createPrivilege(CreatePrivilegeRequestDto createPrivilegeRequestDto) {
        return privilegeService.createPrivilege(createPrivilegeRequestDto);
    }

    @Override
    public ResponseEntity<ApiResponse<Privilege>> getPrivilege(Long privilegeId) {
        return privilegeService.getPrivilege(privilegeId);
    }

    @Override
    public ResponseEntity<ApiResponse<Privilege>> editPrivilege(Long privilegeId, CreatePrivilegeRequestDto editPrivilegeDto) {
        return privilegeService.editPrivilege(privilegeId,editPrivilegeDto);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deletePrivilege(Long privilegeId) {
        return privilegeService.deletePrivilege(privilegeId);
    }
}
