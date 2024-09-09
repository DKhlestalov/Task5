package ru.stepup.course.controller;

import org.springframework.validation.annotation.Validated;
import ru.stepup.course.dto.CreateRegisterRequest;
import ru.stepup.course.dto.CreateRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stepup.course.service.RegisterService;

@RestController
public class RegisterController {

    CreateRegisterResponse createRegisterResponse;
    RegisterService registerService;

    @Autowired
    public RegisterController(CreateRegisterResponse createRegisterResponse, RegisterService registerService) {
        this.createRegisterResponse = createRegisterResponse;
        this.registerService = registerService;
    }

    @PostMapping("/corporate-settlement-account/create")
    public ResponseEntity<CreateRegisterResponse> handleAccountCreate(@Validated @RequestBody CreateRegisterRequest createRegisterRequest) {
        registerService.addRegister(createRegisterRequest, createRegisterResponse);
        if (createRegisterResponse.getStatus()==400) return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(createRegisterResponse);
        if (createRegisterResponse.getStatus()==404) return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(createRegisterResponse);
        if (createRegisterResponse.getStatus()==500) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(createRegisterResponse);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(createRegisterResponse);
    }

}
