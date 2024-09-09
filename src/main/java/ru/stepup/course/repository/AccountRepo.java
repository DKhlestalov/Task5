package ru.stepup.course.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.stepup.course.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account,Long> {
    @Query("SELECT a FROM Account a WHERE a.accountPool = ?1 and a.busy = false ")
    List<Account> getAccountByAccountPool(Long poolID);

    @Modifying
    @Query("update Account a set a.busy = true where a.id = ?1")
    void setBusy(Long accountId);
}
