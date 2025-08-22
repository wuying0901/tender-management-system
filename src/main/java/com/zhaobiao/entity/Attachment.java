package com.zhaobiao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private TenderProject project;
    
    @Column(nullable = false)
    private String fileName; // 文件名
    
    @Column(nullable = false)
    private String originalFileName; // 原始文件名
    
    @Column(nullable = false)
    private String fileType; // 文件类型
    
    @Column(nullable = false)
    private String filePath; // 文件路径
    
    private Long fileSize; // 文件大小（字节）
    
    @Column(nullable = false)
    private String attachmentType; // 附件类型（公告PDF、其他附件等）
    
    @Column(nullable = false)
    private LocalDateTime uploadTime; // 上传时间
    
    @Column(nullable = false)
    private Boolean isActive = true; // 是否有效
    
    @PrePersist
    protected void onCreate() {
        uploadTime = LocalDateTime.now();
    }
}
