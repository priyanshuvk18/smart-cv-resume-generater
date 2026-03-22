package com.smartcv.builder.service;

import com.smartcv.builder.dto.TemplateDto;
import com.smartcv.builder.entity.Template;
import com.smartcv.builder.exception.ResourceNotFoundException;
import com.smartcv.builder.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public List<TemplateDto> getAllActiveTemplates() {
        return templateRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TemplateDto getTemplateById(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template", "id", id));
        return mapToDto(template);
    }

    public TemplateDto saveTemplate(TemplateDto templateDto) {
        Template template = Template.builder()
                .name(templateDto.getName())
                .description(templateDto.getDescription())
                .htmlStructure(templateDto.getHtmlStructure())
                .cssStyle(templateDto.getCssStyle())
                .active(true)
                .build();
        Template saved = templateRepository.save(template);
        return mapToDto(saved);
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    private TemplateDto mapToDto(Template template) {
        return TemplateDto.builder()
                .id(template.getId())
                .name(template.getName())
                .description(template.getDescription())
                .htmlStructure(template.getHtmlStructure())
                .cssStyle(template.getCssStyle())
                .active(template.isActive())
                .build();
    }
}
