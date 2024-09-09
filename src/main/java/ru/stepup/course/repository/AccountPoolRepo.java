package ru.stepup.course.repository;

import ru.stepup.course.model.AccountPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountPoolRepo extends JpaRepository<AccountPool, Long> {
    @Query("SELECT ap FROM AccountPool ap WHERE ap.branchCode = ?1 and ap.currencyCode = ?2 and ap.mdmCode=?3 and ap.priorityCode=?4 and ap.registryTypeCode = ?5")
    List<AccountPool> getPool(String branchCode,
                              String currencyCode,
                              String mdmCode,
                              String priorityCode,
                              String registryTypeCode);
}
