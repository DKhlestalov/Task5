package ru.stepup.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.course.dto.*;
import ru.stepup.course.model.*;
import ru.stepup.course.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    TppProductRepo productRepo;
    AgreementRepo agreementRepo;
    TppRefProductClassRepo tppRefProductClassRepo;
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    TppProductRegisterRepo tppProductRegisterRepo;
    AccountService accountService;

    @Autowired
    public ProductService(TppProductRepo productRepo, AgreementRepo agreementRepo, TppRefProductClassRepo tppRefProductClassRepo, TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo, TppProductRegisterRepo tppProductRegisterRepo, AccountService accountService) {
        this.productRepo = productRepo;
        this.agreementRepo = agreementRepo;
        this.tppRefProductClassRepo = tppRefProductClassRepo;
        this.tppRefProductRegisterTypeRepo = tppRefProductRegisterTypeRepo;
        this.tppProductRegisterRepo = tppProductRegisterRepo;
        this.accountService = accountService;
    }

    private void fillResponse(CreateProductResponse response,
                              Long idProd,
                              List<Long> arrAgr,
                              List<Long> arrReg,
                              Integer status,
                              String errMsg
    ) {
        response.setInstanceId(idProd);
        response.setStatus(status);
        response.setErrMsg(errMsg);

        List<Long> registers = response.getRegisterId();
        if (registers != null && registers.size() > 0) registers.clear();

        if (arrReg!=null) {
            for (Long idReg : arrReg) {
                registers.add(idReg);
            }
        }


        List<Long> agreements = response.getSupplementaryAgreementId();
        if (agreements != null && agreements.size() > 0) agreements.clear();

        if (arrAgr!=null) {
            for (Long idAgr : arrAgr) {
                agreements.add(idAgr);
            }
        }
    }

    private void clearResponse(CreateProductResponse createProductResponse) {
        fillResponse(createProductResponse,null,null,null,null,null);
    }

    private boolean agreementExists(CreateProductRequest createProductRequest, CreateProductResponse createProductResponse) {
        List<AgreementDto> agrList;
        agrList = createProductRequest.getInstanceArrangement();
        if (agrList != null) {
            for (AgreementDto agr : agrList) {
                boolean agrExists = agreementRepo.existsAgreementByNumber(agr.getNumber());
                if (agrExists) {
                    String strErr = "Параметр № Дополнительного соглашения (сделки) Number " + agr.getNumber() + " уже существует";
                    fillResponse(createProductResponse, null, null, null, 400, strErr);
                    return true;
                }
            }
        }
        return false;
    }

    private List<Long> addAgreements(Long productId, CreateProductRequest createProductRequest, CreateProductResponse createProductResponse){
        List<Long> arrAgr = null;
        List<AgreementDto> lstAgr;
        lstAgr = createProductRequest.getInstanceArrangement();
        if (lstAgr != null) {
            arrAgr = new ArrayList<>();
            for (AgreementDto agr : lstAgr) {
                try {
                    Agreement new_agr = agreementRepo.saveAndFlush(new Agreement(null,
                                    productId,
                                    agr.getGeneralAgreementId(),
                                    agr.getSupplementaryAgreementId(),
                                    agr.getArrangementType(),
                                    agr.getShedulerJobId(),
                                    agr.getNumber(),
                                    agr.getOpeningDate(),
                                    agr.getClosingDate(),
                                    agr.getCancelDate(),
                                    agr.getValidityDuration(),
                                    null,
                                    agr.getStatus(),
                                    agr.getInterestCalculationDate(),
                                    agr.getInterestRate(),
                                    agr.getCoefficient(),
                                    agr.getCoefficientAction(),
                                    agr.getMinimumInterestRate(),
                                    agr.getMinimumInterestRateCoefficient(),
                                    agr.getMinimumInterestRateCoefficientAction(),
                                    agr.getMaximalnterestRate(),
                                    agr.getMaximalnterestRateCoefficient(),
                                    agr.getMaximalnterestRateCoefficientAction()
                            )
                    );
                    arrAgr.add(new_agr.getId());
                }
                catch (Exception e) {
                    fillResponse(createProductResponse, null, null, null, 500, "Ошибка при добавлении ДС "+e.getMessage());
                }
            }
        }
        return arrAgr;
    }


    public void addProduct(CreateProductRequest createProductRequest, CreateProductResponse createProductResponse) {
        clearResponse(createProductResponse);
        // Шаг 2
        Long id;
        id = createProductRequest.getInstanceId();
        if (id == null){
            // 1 Создание продукт
            // 1.1 Проверка таблицы ЭП (tpp_product) на дубли
            try {
                boolean fl = productRepo.existsTppProductByNumber(createProductRequest.getContractNumber());
                if (fl) {
                    String strErr = "Параметр ContractNumber № договора "+createProductRequest.getContractNumber()+" уже существует";
                    fillResponse(createProductResponse, null, null, null, 400, strErr);
                    return;
                }
            }
            catch (Exception e) {
                fillResponse(createProductResponse, null, null, null, 500, "Ошибка в existsTpp_productByNumber "+e.getMessage());
                return;
            }
            // 1.2 Проверка таблицы ДС (agreement) на дубли
            try {
                if (agreementExists(createProductRequest, createProductResponse)) return;
            }
            catch (Exception e) {
                fillResponse(createProductResponse, null, null, null, 500, "Ошибка при проверке соглашений на дубли "+e.getMessage());
                return;
            }
            // 1.3 По КодуПродукта найти связные записи в Каталоге Типа регистра
            TppRefProductClass prodClass = null;
            List <TppRefProductRegisterType> lstRegistryType = null;
            try {
                lstRegistryType = tppRefProductRegisterTypeRepo.findTppRefProductRegisterTypeByAccountType("Клиентский");
                prodClass = tppRefProductClassRepo.getTppRefProductClassById(createProductRequest.getProductCode());

                if (prodClass == null) {
                    String strErr = "КодПродукта " + createProductRequest.getProductCode() + " не найдено в Каталоге продуктов tpp_ref_product_class";
                    fillResponse(createProductResponse, null, null, null, 404, strErr);
                    return;
                }

                for (int j=0; j<lstRegistryType.size(); j++) {
                    boolean found = false;

                    if (lstRegistryType.get(j).getProductClassCode() == prodClass.getValue()) {
                        found = true;
                    }
                    if (!found) {
                        lstRegistryType.remove(j);
                    }
                }
            }
            catch (Exception e) {
                fillResponse(createProductResponse, null, null, null, 500, "Ошибка при поиске связных регистров "+e.getMessage());
                return;
            }

            // 1.4 Добавить строку в таблицу tpp_product
            TppProduct prod;
            try {
                prod = productRepo.saveAndFlush(new TppProduct(null,
                        createProductRequest.getProductCode(),
                        createProductRequest.getMdmCode(),
                        createProductRequest.getProductType(),
                        createProductRequest.getContractNumber(),
                        createProductRequest.getPriority(),
                        createProductRequest.getContractDate(),
                        createProductRequest.getContractDate(),
                        null,
                        null,
                        createProductRequest.getInterestRatePenalty(),
                        createProductRequest.getMinimalBalance(),
                        createProductRequest.getThresholdAmount(),
                        null,
                        null,
                        createProductRequest.getTaxPercentageRate(),
                        null,
                        null
                        )
                );
            }
            catch (Exception e) {
                fillResponse(createProductResponse, null, null, null, 500, "Ошибка при добавлении продукта "+e.getMessage());
                return;
            }

            id = prod.getId();
            if (id == null) {
                fillResponse(createProductResponse, null, null, null, 400, "Ошибка. Не удалось добавить продукт");
                return;
            }
            // 1.5 Добавить в таблицу ПР (tpp_product_registry) строки
            List<Long> arrRegistr = null;
            if (!lstRegistryType.isEmpty()) arrRegistr = new ArrayList<>();
            for(TppRefProductRegisterType registerType : lstRegistryType){
                Account account = accountService.getAccount(createProductRequest.getBranchCode(),createProductRequest.getIsoCurrencyCode(),createProductRequest.getMdmCode(),"00",registerType.getValue());
                if (account == null) {
                    fillResponse(createProductResponse, null, null, null, 404, "Не найден счет с такими параметрами в пуле: " + createProductRequest.getBranchCode() + " " + createProductRequest.getIsoCurrencyCode() + " " + createProductRequest.getMdmCode() + " 00 " + registerType.getValue());
                    return;
                }
                else {
                    accountService.setAccountBusy(account);
                }
                TppProductRegister register;
                try {
                    register = tppProductRegisterRepo.saveAndFlush(new TppProductRegister(
                            null,
                            id,
                            registerType.getValue(),
                            account.getId(),
                            createProductRequest.getIsoCurrencyCode(),
                            "0",
                            account.getAccountNumber()
                            )
                    );
                    arrRegistr.add(register.getId());
                }
                catch (Exception e) {
                    fillResponse(createProductResponse, null, null, null,500, "Ошибка при добавлении в tppProductRegisterRepo.saveAndFlush " + e.getMessage());
                    return;
                }
            }
            // 1.5 Добавить в таблицу ДС (agreement) строки
            List<Long> arrAgr = addAgreements(id, createProductRequest,createProductResponse);
            if(createProductResponse.getErrMsg() != null) return;
            // Ответ
            fillResponse(createProductResponse, id, arrAgr, arrRegistr, 200, null);
        }
        else{
            // изменяется состав ДС
            // 2.1 Проверка таблицы ЭП (tpp_product) на существование ЭП
            try {
                TppProduct product = productRepo.getTppProductById(id);
                if (product == null) {
                    fillResponse(createProductResponse, null, null, null, 404, "Экземпляр продукта с параметром instanceId "+id.toString()+" не найден");
                    return;
                }
            }
            catch (Exception e) {
                fillResponse(createProductResponse, null, null, null, 500, "Ошибка в productRepo.getTpp_productById "+e.getMessage());
                return;
            }
            // 2.2 Проверка таблицы ДС (agreement) на дубли
            if (agreementExists(createProductRequest, createProductResponse)) return;
            // 8 Добавить строку в таблицу ДС (agreement)
            List<Long> arrAgr = addAgreements(id, createProductRequest,createProductResponse);
            if(createProductResponse.getErrMsg() != null) return;
            // Ответ
            fillResponse(createProductResponse, id, arrAgr, null, 200, null);


        }


    }
}
