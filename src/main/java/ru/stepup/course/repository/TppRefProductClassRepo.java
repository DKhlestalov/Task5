package ru.stepup.course.repository;

import ru.stepup.course.model.TppRefProductClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppRefProductClassRepo extends JpaRepository<TppRefProductClass, Long> {
    TppRefProductClass getTppRefProductClassById(Integer id);
}
