package com.smartcv.builder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {
    private Long id;
    private String name;
    private String description;
    private String htmlStructure;
    private String cssStyle;
    private boolean active;
}
