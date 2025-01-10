package com.example.kuhidbs.entity;

import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date(); // 생성 시 현재 시간 설정
        this.updatedAt = new Date(); // 생성 시 현재 시간 설정
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date(); // 업데이트 시 현재 시간 설정
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
