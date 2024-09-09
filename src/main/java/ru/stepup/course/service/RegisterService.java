package ru.stepup.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.stepup.course.dto.CreateRegisterRequest;
import ru.stepup.course.dto.CreateRegisterResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.stepup.course.model.Account;
import ru.stepup.course.model.TppProductRegister;
import ru.stepup.course.model.TppRefProductRegisterType;
import ru.stepup.course.repository.TppProductRegisterRepo;
import ru.stepup.course.repository.TppRefProductRegisterTypeRepo;

import java.util.List;

@Service
@Getter
@Setter
public class RegisterService {

    TppProductRegisterRepo tppProductRegisterRepo;
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    AccountService accountService;

    @Autowired
    public RegisterService(TppProductRegisterRepo tppProductRegisterRepo, TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo, AccountService accountService) {
        this.tppProductRegisterRepo = tppProductRegisterRepo;
        this.tppRefProductRegisterTypeRepo = tppRefProductRegisterTypeRepo;
        this.accountService = accountService;
    }

    private void fillResponse(CreateRegisterResponse response,
                              Long idAcc,
                              Integer status,
                              String errMsg
    ) {
        response.setAccountId(idAcc);
        response.setStatus(status);
        response.setErrorMsg(errMsg);
    }

    public void addRegister(CreateRegisterRequest createRegisterRequest, CreateRegisterResponse createRegisterResponse) {
        //Шаг 2
        TppProductRegister register = null;
        try {
            register = tppProductRegisterRepo.getTppProductRegisterByProductIdAndType(createRegisterRequest.getInstanceId(), createRegisterRequest.getRegistryTypeCode());
            if (register!=null) {
                    String strErr;
                    strErr = "Параметр registryTypeCode тип регистра "+createRegisterRequest.getRegistryTypeCode()+" уже существует для ЭП с ИД "+createRegisterRequest.getInstanceId();
                    fillResponse(createRegisterResponse, null, 400, strErr);
                    return;
            }
        }
        catch (Exception e) {
            fillResponse(createRegisterResponse, null, 500, "Ошибка в tppProductRegisterRepo "+e.getMessage());
            return;
        }
        //Шаг 3
        TppRefProductRegisterType refProductRegisterType = null;
        try {
            refProductRegisterType = tppRefProductRegisterTypeRepo.getTppRefProductRegisterTypeByValue(createRegisterRequest.getRegistryTypeCode());
            if (refProductRegisterType==null) {
                fillResponse(createRegisterResponse, null, 404, "Код Продукта "+createRegisterRequest.getRegistryTypeCode()+" не найдено в Каталоге продуктов tpp_ref_product_register_type для данного типа Регистра");
                return;
            }
        }
        catch (Exception e) {
            fillResponse(createRegisterResponse, null, 500, "Ошибка в tppRefProductRegisterTypeRepo.getTppRefProductRegisterTypeByValue "+e.getMessage());
            return;
        }
        // Шаг 4
        Account acc = accountService.getAccount(createRegisterRequest.getBranchCode(),
                createRegisterRequest.getCurrencyCode(),
                createRegisterRequest.getMdmCode(),
                createRegisterRequest.getPriorityCode(),
                createRegisterRequest.getRegistryTypeCode());
        if (acc==null) {
            fillResponse(createRegisterResponse, null, 404, "Не найден счет с такими параметрами в пуле: "+createRegisterRequest.getBranchCode()+" "+createRegisterRequest.getCurrencyCode()+" "+createRegisterRequest.getMdmCode()+" "+createRegisterRequest.getPriorityCode()+" "+createRegisterRequest.getRegistryTypeCode());
            return;
        }
        else{
            accountService.setAccountBusy(acc);
        }
        // Шаг 5
        try {
            register = tppProductRegisterRepo.saveAndFlush(new TppProductRegister(
                    null,
                    createRegisterRequest.getInstanceId(),
                    refProductRegisterType.getValue(),
                    acc.getId(),
                    createRegisterRequest.getCurrencyCode(),
                    "0",
                    acc.getAccountNumber()
                    )
            );
        }
        catch (Exception e) {
            fillResponse(createRegisterResponse, null, 500, "Ошибка при добавлении в tppProductRegisterRepo.saveAndFlush "+e.getMessage());
            return;
        }

        Long newIdRegistr = register.getId();
        if (newIdRegistr == null) {
            fillResponse(createRegisterResponse, null, 400, "Ошибка. Не удалось добавить регистр");
            return;
        }
        fillResponse(createRegisterResponse, newIdRegistr, 200, null);
    }
}
