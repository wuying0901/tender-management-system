package com.zhaobiao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tender_projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenderProject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String projectId; // 项目编号
    
    @Column(nullable = false)
    private String title; // 项目标题
    
    @Column(length = 1000)
    private String description; // 项目描述
    
    @Column(nullable = false)
    private String bidder; // 招标单位
    
    private String agency; // 代理单位
    
    @Column(nullable = false)
    private String location; // 地区
    
    @Column(nullable = false)
    private String category; // 招标类型
    
    private String fundSource; // 资金来源
    
    private String projectScope; // 招标范围
    
    private String qualification; // 投标人资格要求
    
    private LocalDateTime registrationStartTime; // 报名开始时间
    
    private LocalDateTime registrationEndTime; // 报名截止时间
    
    private LocalDateTime documentStartTime; // 文件获取开始时间
    
    private LocalDateTime documentEndTime; // 文件获取截止时间
    
    private LocalDateTime clarificationEndTime; // 供应商提出澄清截止时间
    
    @Column(nullable = false)
    private LocalDateTime bidDeadline; // 投标截止时间
    
    private String estimatedAmount; // 预估金额
    
    private String supervisorName; // 监督人姓名
    
    private String supervisorPhone; // 监督人电话
    
    private String contactName; // 联系人姓名
    
    private String contactPhone; // 联系人电话
    
    private String contactEmail; // 联系人邮箱
    
    private String contactAddress; // 联系地址
    
    private String department; // 部门
    
    private String position; // 职位
    
    @Column(nullable = false)
    private LocalDateTime publishTime; // 发布时间
    
    @Column(nullable = false)
    private LocalDateTime createTime; // 创建时间
    
    private LocalDateTime updateTime; // 更新时间
    
    @Column(nullable = false)
    private Boolean isActive = true; // 是否有效
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        publishTime = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
