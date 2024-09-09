package ru.stepup.course.repository;

import ru.stepup.course.model.TppRefProductRegisterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TppRefProductRegisterTypeRepo extends JpaRepository<TppRefProductRegisterType, Long> {
    TppRefProductRegisterType getTppRefProductRegisterTypeByValue(String value);

    List<TppRefProductRegisterType> findTppRefProductRegisterTypeByAccountType(String accountType);
}
