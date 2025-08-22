package com.zhaobiao.service;

import com.zhaobiao.dto.TenderProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TenderProjectService {
    
    TenderProjectDTO createProject(TenderProjectDTO projectDTO);
    
    TenderProjectDTO updateProject(Long id, TenderProjectDTO projectDTO);
    
    TenderProjectDTO getProjectById(Long id);
    
    TenderProjectDTO getProjectByProjectId(String projectId);
    
    Page<TenderProjectDTO> getAllProjects(Pageable pageable);
    
    Page<TenderProjectDTO> searchProjects(String title, String bidder, String location, 
                                         String category, LocalDateTime startDate, 
                                         LocalDateTime endDate, Pageable pageable);
    
    void deleteProject(Long id);
    
    List<TenderProjectDTO> getProjectsByDeadlineRange(LocalDateTime start, LocalDateTime end);
    
    List<String> getAllLocations();
    
    List<String> getAllCategories();
}
