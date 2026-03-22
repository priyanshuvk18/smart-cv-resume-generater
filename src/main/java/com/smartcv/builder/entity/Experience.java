package com.smartcv.builder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experience")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_detail_id")
    private CVDetail cvDetail;
    private String company;
    private String location;
    private String position;
    private String startDate;
    private String endDate;
    @Column(columnDefinition = "TEXT")
    private String description;
}
