package ru.stepup.course.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CreateRegisterResponse {
    private Long accountId;
    @JsonIgnore
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMsg;

}
