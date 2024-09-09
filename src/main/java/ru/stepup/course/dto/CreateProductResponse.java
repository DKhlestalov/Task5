package ru.stepup.course.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class CreateProductResponse {
    private Long instanceId;
    private List<Long> registerId;
    private List<Long> supplementaryAgreementId;
    @JsonIgnore
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errMsg;

    public CreateProductResponse() {
        this.registerId = new ArrayList<>();
        this.supplementaryAgreementId  = new ArrayList<>();
    }
}
