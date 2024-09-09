package ru.stepup.course.repository;

import ru.stepup.course.model.TppProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppProductRepo extends JpaRepository<TppProduct, Long> {
    boolean existsTppProductByNumber(String number);

    TppProduct getTppProductById(Long id);
}
