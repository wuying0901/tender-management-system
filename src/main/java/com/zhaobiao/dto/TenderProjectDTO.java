package com.zhaobiao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenderProjectDTO {
    
    private Long id;
    
    @NotBlank(message = "项目编号不能为空")
    private String projectId;
    
    @NotBlank(message = "项目标题不能为空")
    private String title;
    
    private String description;
    
    @NotBlank(message = "招标单位不能为空")
    private String bidder;
    
    private String agency;
    
    @NotBlank(message = "地区不能为空")
    private String location;
    
    @NotBlank(message = "招标类型不能为空")
    private String category;
    
    private String fundSource;
    
    private String projectScope;
    
    private String qualification;
    
    private LocalDateTime registrationStartTime;
    
    private LocalDateTime registrationEndTime;
    
    private LocalDateTime documentStartTime;
    
    private LocalDateTime documentEndTime;
    
    private LocalDateTime clarificationEndTime;
    
    @NotNull(message = "投标截止时间不能为空")
    private LocalDateTime bidDeadline;
    
    private String estimatedAmount;
    
    private String supervisorName;
    
    private String supervisorPhone;
    
    private String contactName;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String contactAddress;
    
    private String department;
    
    private String position;
    
    private List<AttachmentDTO> attachments;
}
