package ru.stepup.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
    Long instanceId;
    @NotBlank
    String productType;
    @NotNull
    Integer productCode;
    @NotNull
    Integer registerType;
    @NotNull
    String mdmCode;
    @NotBlank
    String contractNumber;
    @NotNull
    Date contractDate;
    @NotNull
    Integer priority;
    Float interestRatePenalty;
    Float minimalBalance;
    Float thresholdAmount;
    String accountingDetails;
    String rateType;
    Float taxPercentageRate;
    Float technicalOverdraftLimitAmount;
    @NotNull
    Integer contractId;
    @NotBlank
    String branchCode;
    @NotBlank
    String isoCurrencyCode;
    @NotBlank
    String urgencyCode;
    Integer referenceCode;
    List<AdditionalPropertiesVipDto> additionalPropertiesVip;
    List<AgreementDto> instanceArrangement;

}
