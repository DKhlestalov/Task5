package ru.stepup.course.repository;

import ru.stepup.course.model.TppProductRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppProductRegisterRepo extends JpaRepository<TppProductRegister, Long> {
    TppProductRegister getTppProductRegisterByProductIdAndType(Long productId, String type);
}
