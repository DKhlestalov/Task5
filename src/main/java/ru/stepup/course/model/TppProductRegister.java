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
@Setter
@Entity
@Table(name = "tpp_product_register")
public class TppProductRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "type")
    private String type;

    @Column(name = "account")
    private Long account;

    @Column(name = "currency_code", length = 30)
    private String currencyCode;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "account_number", length = 25)
    private String accountNumber;

}