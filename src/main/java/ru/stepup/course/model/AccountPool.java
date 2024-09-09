package ru.stepup.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "account_pool")
public class AccountPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('account_pool_id_seq'")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "branch_code", length = 50)
    private String branchCode;

    @Column(name = "currency_code", length = 30)
    private String currencyCode;

    @Column(name = "mdm_code", length = 50)
    private String mdmCode;

    @Column(name = "priority_code", length = 30)
    private String priorityCode;

    @Column(name = "registry_type_code", length = 50)
    private String registryTypeCode;

}