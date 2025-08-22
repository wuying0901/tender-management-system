package com.zhaobiao.service.impl;

import com.zhaobiao.dto.TenderProjectDTO;
import com.zhaobiao.entity.TenderProject;
import com.zhaobiao.repository.TenderProjectRepository;
import com.zhaobiao.service.TenderProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TenderProjectServiceImpl implements TenderProjectService {
    
    private final TenderProjectRepository tenderProjectRepository;
    
    @Override
    public TenderProjectDTO createProject(TenderProjectDTO projectDTO) {
        TenderProject project = new TenderProject();
        BeanUtils.copyProperties(projectDTO, project);
        project = tenderProjectRepository.save(project);
        return convertToDTO(project);
    }
    
    @Override
    public TenderProjectDTO updateProject(Long id, TenderProjectDTO projectDTO) {
        TenderProject project = tenderProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("项目不存在"));
        
        BeanUtils.copyProperties(projectDTO, project, "id", "createTime", "publishTime");
        project = tenderProjectRepository.save(project);
        return convertToDTO(project);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TenderProjectDTO getProjectById(Long id) {
        TenderProject project = tenderProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("项目不存在"));
        return convertToDTO(project);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TenderProjectDTO getProjectByProjectId(String projectId) {
        TenderProject project = tenderProjectRepository.findByProjectIdAndIsActiveTrue(projectId)
                .orElseThrow(() -> new RuntimeException("项目不存在"));
        return convertToDTO(project);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TenderProjectDTO> getAllProjects(Pageable pageable) {
        return tenderProjectRepository.findByIsActiveTrue(pageable)
                .map(this::convertToDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TenderProjectDTO> searchProjects(String title, String bidder, String location, 
                                                String category, LocalDateTime startDate, 
                                                LocalDateTime endDate, Pageable pageable) {
        return tenderProjectRepository.findByConditions(title, bidder, location, category, startDate, endDate, pageable)
                .map(this::convertToDTO);
    }
    
    @Override
    public void deleteProject(Long id) {
        TenderProject project = tenderProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("项目不存在"));
        project.setIsActive(false);
        tenderProjectRepository.save(project);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TenderProjectDTO> getProjectsByDeadlineRange(LocalDateTime start, LocalDateTime end) {
        return tenderProjectRepository.findByBidDeadlineBetweenAndIsActiveTrue(start, end)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllLocations() {
        return tenderProjectRepository.findAllDistinctLocations();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return tenderProjectRepository.findAllDistinctCategories();
    }
    
    private TenderProjectDTO convertToDTO(TenderProject project) {
        TenderProjectDTO dto = new TenderProjectDTO();
        BeanUtils.copyProperties(project, dto);
        return dto;
    }
}
