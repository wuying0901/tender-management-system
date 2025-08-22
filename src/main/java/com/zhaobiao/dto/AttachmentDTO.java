package com.zhaobiao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    
    private Long id;
    
    private String fileName;
    
    private String originalFileName;
    
    private String fileType;
    
    private String filePath;
    
    private Long fileSize;
    
    private String attachmentType;
    
    private LocalDateTime uploadTime;
    
    private String downloadUrl;
}
