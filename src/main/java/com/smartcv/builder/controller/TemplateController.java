package com.smartcv.builder.controller;

import com.smartcv.builder.dto.TemplateDto;
import com.smartcv.builder.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/all")
    public ResponseEntity<List<TemplateDto>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllActiveTemplates());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemplateDto> addTemplate(@RequestBody TemplateDto templateDto) {
        return ResponseEntity.ok(templateService.saveTemplate(templateDto));
    }
}
