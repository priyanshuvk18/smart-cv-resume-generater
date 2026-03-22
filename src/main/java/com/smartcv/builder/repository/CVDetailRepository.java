package com.smartcv.builder.repository;

import com.smartcv.builder.entity.CVDetail;
import com.smartcv.builder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVDetailRepository extends JpaRepository<CVDetail, Long> {
    List<CVDetail> findByUser(User user);
    Optional<CVDetail> findByIdAndUser(Long id, User user);
    List<CVDetail> findByTitleContainingIgnoreCaseAndUser(String title, User user);
}
