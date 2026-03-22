package com.smartcv.builder.controller;

import com.smartcv.builder.dto.MessageResponse;
import com.smartcv.builder.dto.TemplateDto;
import com.smartcv.builder.dto.UserResponse;
import com.smartcv.builder.service.AdminService;
import com.smartcv.builder.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TemplateService templateService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping("/templates/add")
    public ResponseEntity<TemplateDto> addTemplate(@RequestBody TemplateDto templateDto) {
        return ResponseEntity.ok(templateService.saveTemplate(templateDto));
    }

    @DeleteMapping("/templates/{id}")
    public ResponseEntity<MessageResponse> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok(new MessageResponse("Template deleted."));
    }
}
