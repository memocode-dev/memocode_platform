package dev.memocode.memocode_platform.domain.account;

import dev.memocode.memocode_platform.domain.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SQLRestriction("is_deleted = false")
public class Account extends BaseEntity {
}
