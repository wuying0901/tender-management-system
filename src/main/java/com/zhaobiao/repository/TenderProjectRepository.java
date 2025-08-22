package com.zhaobiao.repository;

import com.zhaobiao.entity.TenderProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenderProjectRepository extends JpaRepository<TenderProject, Long> {
    
    Optional<TenderProject> findByProjectId(String projectId);
    
    Optional<TenderProject> findByProjectIdAndIsActiveTrue(String projectId);
    
    Page<TenderProject> findByIsActiveTrue(Pageable pageable);
    
    Page<TenderProject> findByTitleContainingAndIsActiveTrue(String title, Pageable pageable);
    
    Page<TenderProject> findByBidderContainingAndIsActiveTrue(String bidder, Pageable pageable);
    
    Page<TenderProject> findByLocationAndIsActiveTrue(String location, Pageable pageable);
    
    Page<TenderProject> findByCategoryAndIsActiveTrue(String category, Pageable pageable);
    
    @Query("SELECT t FROM TenderProject t WHERE t.isActive = true AND " +
           "(:title IS NULL OR t.title LIKE %:title%) AND " +
           "(:bidder IS NULL OR t.bidder LIKE %:bidder%) AND " +
           "(:location IS NULL OR t.location = :location) AND " +
           "(:category IS NULL OR t.category = :category) AND " +
           "(:startDate IS NULL OR t.bidDeadline >= :startDate) AND " +
           "(:endDate IS NULL OR t.bidDeadline <= :endDate)")
    Page<TenderProject> findByConditions(
            @Param("title") String title,
            @Param("bidder") String bidder,
            @Param("location") String location,
            @Param("category") String category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
    
    List<TenderProject> findByBidDeadlineBetweenAndIsActiveTrue(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT DISTINCT t.location FROM TenderProject t WHERE t.isActive = true")
    List<String> findAllDistinctLocations();
    
    @Query("SELECT DISTINCT t.category FROM TenderProject t WHERE t.isActive = true")
    List<String> findAllDistinctCategories();
}
