package com.zhaobiao.controller;

import com.zhaobiao.dto.TenderProjectDTO;
import com.zhaobiao.service.TenderProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tender-projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminTenderController {

    private final TenderProjectService tenderProjectService;

    @PostMapping
    public ResponseEntity<TenderProjectDTO> create(@RequestBody TenderProjectDTO dto) {
        return ResponseEntity.ok(tenderProjectService.createProject(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenderProjectDTO> update(@PathVariable Long id, @RequestBody TenderProjectDTO dto) {
        return ResponseEntity.ok(tenderProjectService.updateProject(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenderProjectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}


