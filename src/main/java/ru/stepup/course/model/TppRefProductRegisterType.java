package ru.stepup.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Entity
@Table(name = "tpp_ref_product_register_type")
public class TppRefProductRegisterType {
    @Id
    @Column(name = "internal_id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

    @Column(name = "register_type_name", nullable = false, length = 100)
    private String registerTypeName;

    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "product_class_code", nullable = false, referencedColumnName = "value")
    @Column(name = "product_class_code")
    private String productClassCode;

    @Column(name = "register_type_start_date")
    private Date registerTypeStartDate;

    @Column(name = "register_type_end_date")
    private Date registerTypeEndDate;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_type", referencedColumnName = "value")
    @Column(name = "account_type")
    private String accountType;

}
