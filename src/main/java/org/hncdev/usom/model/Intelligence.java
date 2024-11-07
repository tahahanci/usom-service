package org.hncdev.usom.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "intelligences")
public class Intelligence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long intelligenceID;
    private String intelligenceUrl;
    private String intelligenceType;
    private String description;
    private String source;
    private LocalDateTime intelligenceDate;
    private Integer criticalityLevel;
    private String connectionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }
}
