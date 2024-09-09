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
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('account_id_seq'")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_pool_id")
    private Long accountPool;

    @Column(name = "account_number", length = 25)
    private String accountNumber;

    @Column(name = "busy")
    private Boolean busy;

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }
}
