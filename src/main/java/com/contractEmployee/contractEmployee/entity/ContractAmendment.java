package com.contractEmployee.contractEmployee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contract_amendment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractAmendment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amendment_id")
    private Long amendmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    // วันที่บันทึก amendment (auto-generate ตอน insert)
    @CreationTimestamp
    @Column(name = "amendment_date", nullable = false, updatable = false)
    private LocalDate amendmentDate;

    // วันที่มีผลจริงของสัญญาใหม่ → set จาก request
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "change_type", nullable = false)
    private String changeType;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "approved_by")
    private String approvedBy;

    private String source;

    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
