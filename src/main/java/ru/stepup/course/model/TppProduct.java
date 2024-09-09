package ru.stepup.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tpp_product")
public class TppProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer product_code_id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "number", length = 50)
    private String number;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "date_of_conclusion")
    private Date dateOfConclusion;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "end_date_time")
    private Date endDateTime;

    @Column(name = "days")
    private Long days;

    @Column(name = "penalty_rate")
    private Float penaltyRate;

    @Column(name = "nso")
    private Float nso;

    @Column(name = "threshold_amount")
    private Float thresholdAmount;

    @Column(name = "requisite_type", length = 50)
    private String requisiteType;

    @Column(name = "interest_rate_type", length = 50)
    private String interestRateType;

    @Column(name = "tax_rate")
    private Float taxRate;

    @Column(name = "reasone_close", length = 100)
    private String reasoneClose;

    @Column(name = "state", length = 50)
    private String state;
}
