package com.devshowcase.api.repository;

import com.devshowcase.api.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    long countByProjectId(Long projectId);
    @Query("select coalesce(avg(f.rating), 0) from Feedback f where f.project.id = :projectId")
    Double averageRatingByProjectId(@Param("projectId") Long projectId);
}
