package com.smartcv.builder.service;

import com.smartcv.builder.dto.CVDto;
import com.smartcv.builder.entity.*;
import com.smartcv.builder.exception.ResourceNotFoundException;
import com.smartcv.builder.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CVService {

    @Autowired
    private CVDetailRepository cvDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Transactional
    public CVDto saveCV(CVDto cvDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        Template template = templateRepository.findById(cvDto.getTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Template", "id", cvDto.getTemplateId()));

        CVDetail cvDetail;
        if (cvDto.getId() != null) {
            cvDetail = cvDetailRepository.findByIdAndUser(cvDto.getId(), user)
                    .orElseThrow(() -> new ResourceNotFoundException("CV", "id", cvDto.getId()));
            
            // Clear existing lists for update (Hibernate will handle orphans)
            cvDetail.getEducation().clear();
            cvDetail.getExperience().clear();
            cvDetail.getSkills().clear();
            cvDetail.getProjects().clear();
            cvDetail.getCertifications().clear();
        } else {
            cvDetail = new CVDetail();
            cvDetail.setUser(user);
        }

        cvDetail.setTemplate(template);
        cvDetail.setTitle(cvDto.getTitle());
        cvDetail.setFullName(cvDto.getFullName());
        cvDetail.setEmail(cvDto.getEmail());
        cvDetail.setPhone(cvDto.getPhone());
        cvDetail.setAddress(cvDto.getAddress());
        cvDetail.setSummary(cvDto.getSummary());
        cvDetail.setProfileImagePath(cvDto.getProfileImagePath());
        cvDetail.setStatus(CVDetail.CVStatus.valueOf(cvDto.getStatus()));

        // Map child entities
        if (cvDto.getEducation() != null) {
            for (CVDto.EducationDto eduDto : cvDto.getEducation()) {
                Education edu = Education.builder()
                        .school(eduDto.getSchool())
                        .degree(eduDto.getDegree())
                        .fieldOfStudy(eduDto.getFieldOfStudy())
                        .startDate(eduDto.getStartDate())
                        .endDate(eduDto.getEndDate())
                        .description(eduDto.getDescription())
                        .cvDetail(cvDetail)
                        .build();
                cvDetail.getEducation().add(edu);
            }
        }
        
        if (cvDto.getExperience() != null) {
            for (CVDto.ExperienceDto expDto : cvDto.getExperience()) {
                Experience exp = Experience.builder()
                        .company(expDto.getCompany())
                        .location(expDto.getLocation())
                        .position(expDto.getPosition())
                        .startDate(expDto.getStartDate())
                        .endDate(expDto.getEndDate())
                        .description(expDto.getDescription())
                        .cvDetail(cvDetail)
                        .build();
                cvDetail.getExperience().add(exp);
            }
        }

        if (cvDto.getSkills() != null) {
            for (CVDto.SkillDto skillDto : cvDto.getSkills()) {
                Skill skill = Skill.builder()
                        .name(skillDto.getName())
                        .level(skillDto.getLevel())
                        .cvDetail(cvDetail)
                        .build();
                cvDetail.getSkills().add(skill);
            }
        }

        if (cvDto.getProjects() != null) {
            for (CVDto.ProjectDto projDto : cvDto.getProjects()) {
                Project proj = Project.builder()
                        .title(projDto.getTitle())
                        .description(projDto.getDescription())
                        .link(projDto.getLink())
                        .cvDetail(cvDetail)
                        .build();
                cvDetail.getProjects().add(proj);
            }
        }

        if (cvDto.getCertifications() != null) {
            for (CVDto.CertificationDto certDto : cvDto.getCertifications()) {
                Certification cert = Certification.builder()
                        .name(certDto.getName())
                        .issuer(certDto.getIssuer())
                        .date(certDto.getDate())
                        .cvDetail(cvDetail)
                        .build();
                cvDetail.getCertifications().add(cert);
            }
        }

        CVDetail saved = cvDetailRepository.save(cvDetail);
        return mapToDto(saved);
    }

    public List<CVDto> getUserCVs(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
        return cvDetailRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CVDto getCVById(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
        CVDetail cv = cvDetailRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("CV", "id", id));
        return mapToDto(cv);
    }

    public void deleteCV(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
        CVDetail cv = cvDetailRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("CV", "id", id));
        cvDetailRepository.delete(cv);
    }

    private CVDto mapToDto(CVDetail cv) {
        return CVDto.builder()
                .id(cv.getId())
                .templateId(cv.getTemplate().getId())
                .title(cv.getTitle())
                .fullName(cv.getFullName())
                .email(cv.getEmail())
                .phone(cv.getPhone())
                .address(cv.getAddress())
                .summary(cv.getSummary())
                .profileImagePath(cv.getProfileImagePath())
                .status(cv.getStatus().name())
                .createdAt(cv.getCreatedAt())
                .updatedAt(cv.getUpdatedAt())
                .education(cv.getEducation().stream().map(e -> CVDto.EducationDto.builder()
                        .id(e.getId()).school(e.getSchool()).degree(e.getDegree())
                        .fieldOfStudy(e.getFieldOfStudy()).startDate(e.getStartDate()).endDate(e.getEndDate())
                        .description(e.getDescription()).build()).collect(Collectors.toList()))
                .experience(cv.getExperience().stream().map(e -> CVDto.ExperienceDto.builder()
                        .id(e.getId()).company(e.getCompany()).location(e.getLocation()).position(e.getPosition())
                        .startDate(e.getStartDate()).endDate(e.getEndDate()).description(e.getDescription()).build()).collect(Collectors.toList()))
                .skills(cv.getSkills().stream().map(s -> CVDto.SkillDto.builder()
                        .id(s.getId()).name(s.getName()).level(s.getLevel()).build()).collect(Collectors.toList()))
                .projects(cv.getProjects().stream().map(p -> CVDto.ProjectDto.builder()
                        .id(p.getId()).title(p.getTitle()).description(p.getDescription()).link(p.getLink()).build()).collect(Collectors.toList()))
                .certifications(cv.getCertifications().stream().map(c -> CVDto.CertificationDto.builder()
                        .id(c.getId()).name(c.getName()).issuer(c.getIssuer()).date(c.getDate()).build()).collect(Collectors.toList()))
                .build();
    }
}
