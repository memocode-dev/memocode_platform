package dev.memocode.memocode_platform.domain.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Builder.Default
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted = false;

    @Version
    @Column(name = "lock_version", nullable = false)
    private Integer lockVersion;

    protected void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.isDeleted = null;
    }

    public void restore() {
        this.deletedAt = null;
        this.isDeleted = false;
    }

    public boolean isDeleted() {
        return this.isDeleted == null;
    }

    public boolean isNotDeleted() {
        return Boolean.FALSE.equals(this.isDeleted);
    }
}
