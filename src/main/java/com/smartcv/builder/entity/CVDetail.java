package com.smartcv.builder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cv_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CVDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    private String title;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String profileImagePath;

    @Enumerated(EnumType.STRING)
    private CVStatus status = CVStatus.DRAFT;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cvDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education = new ArrayList<>();

    @OneToMany(mappedBy = "cvDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experience = new ArrayList<>();

    @OneToMany(mappedBy = "cvDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "cvDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "cvDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certification> certifications = new ArrayList<>();

    public enum CVStatus {
        DRAFT,
        COMPLETED
    }
}
