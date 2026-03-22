package com.smartcv.builder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CVDto {
    private Long id;
    private Long templateId;
    private String title;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String summary;
    private String profileImagePath;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<EducationDto> education;
    private List<ExperienceDto> experience;
    private List<SkillDto> skills;
    private List<ProjectDto> projects;
    private List<CertificationDto> certifications;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EducationDto {
        private Long id;
        private String school;
        private String degree;
        private String fieldOfStudy;
        private String startDate;
        private String endDate;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExperienceDto {
        private Long id;
        private String company;
        private String location;
        private String position;
        private String startDate;
        private String endDate;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillDto {
        private Long id;
        private String name;
        private String level;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDto {
        private Long id;
        private String title;
        private String description;
        private String link;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificationDto {
        private Long id;
        private String name;
        private String issuer;
        private String date;
    }
}
