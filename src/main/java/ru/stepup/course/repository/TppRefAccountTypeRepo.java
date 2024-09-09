package ru.stepup.course.repository;

import ru.stepup.course.model.TppRefAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppRefAccountTypeRepo extends JpaRepository<TppRefAccountType, Long> {
    TppRefAccountType getTppRefAccountTypeByValue(String value);
}
