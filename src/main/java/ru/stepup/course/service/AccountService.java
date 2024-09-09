package ru.stepup.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.course.model.Account;
import ru.stepup.course.model.AccountPool;
import ru.stepup.course.repository.AccountPoolRepo;
import ru.stepup.course.repository.AccountRepo;

import java.util.List;

@Service
public class AccountService {
    AccountPoolRepo accountPoolRepo;
    AccountRepo accountRepo;
    @Autowired
    public AccountService(AccountPoolRepo accountPoolRepo, AccountRepo accountRepo) {
        this.accountPoolRepo = accountPoolRepo;
        this.accountRepo = accountRepo;
    }

    public Account getAccount(
            String branchCode,
            String currencyCode,
            String mdmCode,
            String priorityCode,
            String registryTypeCode
    ) {
        List<AccountPool> lstAccPool = null;
        try {
            lstAccPool = accountPoolRepo.getPool(branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode);
            if (lstAccPool == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        List<Account> lstAcc = null;
        try {
            lstAcc = accountRepo.getAccountByAccountPool(lstAccPool.get(0).getId());
            if (lstAcc == null || lstAcc.isEmpty()) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
        return lstAcc.get(0);
    }
    // Сделать счет занятым
    public void setAccountBusy(Account account){
        account.setBusy(true);
        accountRepo.saveAndFlush(account);

    }
}
