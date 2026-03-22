package com.smartcv.builder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "education")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_detail_id")
    private CVDetail cvDetail;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private String startDate;
    private String endDate;
    @Column(columnDefinition = "TEXT")
    private String description;
}
