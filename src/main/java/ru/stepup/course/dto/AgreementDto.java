package ru.stepup.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AgreementDto {
    private String generalAgreementId;
    private String supplementaryAgreementId;
    private String arrangementType;
    private Long shedulerJobId;
    @NotBlank
    private String number;
    @NotNull
    private Date openingDate;
    private Date closingDate;
    private Date cancelDate;
    private Long validityDuration;
    private String cancellationReason;
    private String status;
    private Date interestCalculationDate;
    private Float interestRate;
    private Float coefficient;
    private String coefficientAction;
    private Float minimumInterestRate;
    private Float minimumInterestRateCoefficient;
    private String minimumInterestRateCoefficientAction;
    private Float maximalnterestRate;
    private Float maximalnterestRateCoefficient;
    private String maximalnterestRateCoefficientAction;


}
