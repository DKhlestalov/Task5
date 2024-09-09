package ru.stepup.course.repository;

import ru.stepup.course.model.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepo extends JpaRepository<Agreement, Long> {
    boolean existsAgreementByNumber(String number);
}
