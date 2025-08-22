package com.zhaobiao.controller;

import com.zhaobiao.dto.TenderProjectDTO;
import com.zhaobiao.service.TenderProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tender-projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TenderProjectController {
    
    private final TenderProjectService tenderProjectService;
    
    @PostMapping
    public ResponseEntity<TenderProjectDTO> createProject(@Valid @RequestBody TenderProjectDTO projectDTO) {
        TenderProjectDTO created = tenderProjectService.createProject(projectDTO);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TenderProjectDTO> updateProject(@PathVariable Long id, 
                                                         @Valid @RequestBody TenderProjectDTO projectDTO) {
        TenderProjectDTO updated = tenderProjectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TenderProjectDTO> getProjectById(@PathVariable Long id) {
        TenderProjectDTO project = tenderProjectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
    
    @GetMapping("/project-id/{projectId}")
    public ResponseEntity<TenderProjectDTO> getProjectByProjectId(@PathVariable String projectId) {
        TenderProjectDTO project = tenderProjectService.getProjectByProjectId(projectId);
        return ResponseEntity.ok(project);
    }
    
    @GetMapping
    public ResponseEntity<Page<TenderProjectDTO>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TenderProjectDTO> projects = tenderProjectService.getAllProjects(pageable);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<TenderProjectDTO>> searchProjects(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String bidder,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;
        
        Page<TenderProjectDTO> projects = tenderProjectService.searchProjects(
                title, bidder, location, category, start, end, pageable);
        return ResponseEntity.ok(projects);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        tenderProjectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/deadline-range")
    public ResponseEntity<List<TenderProjectDTO>> getProjectsByDeadlineRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<TenderProjectDTO> projects = tenderProjectService.getProjectsByDeadlineRange(start, end);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/locations")
    public ResponseEntity<List<String>> getAllLocations() {
        List<String> locations = tenderProjectService.getAllLocations();
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = tenderProjectService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
