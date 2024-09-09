package ru.stepup.course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tpp_ref_account_type")
public class TppRefAccountType {
    @Id
    private Integer id;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

}
