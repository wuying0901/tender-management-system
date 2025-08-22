package com.zhaobiao.repository;

import com.zhaobiao.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    
    List<Attachment> findByProjectIdAndIsActiveTrue(Long projectId);
    
    List<Attachment> findByProjectIdAndAttachmentTypeAndIsActiveTrue(Long projectId, String attachmentType);
    
    void deleteByProjectId(Long projectId);
}
