package ru.stepup.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
@Getter
@Entity
@Table(name = "tpp_ref_product_class")
public class TppRefProductClass {
    @Id
    @Column(name = "internal_id", nullable = false)
    private Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "gbi_code", length = 50)
    private String gbiCode;

    @Column(name = "gbi_name", length = 100)
    private String gbiName;

    @Column(name = "product_row_code", length = 50)
    private String productRowCode;

    @Column(name = "product_row_name", length = 100)
    private String productRowName;

    @Column(name = "subclass_code", length = 50)
    private String subclassCode;

    @Column(name = "subclass_name", length = 100)
    private String subclassName;


}
