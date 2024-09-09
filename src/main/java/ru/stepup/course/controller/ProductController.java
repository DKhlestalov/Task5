package ru.stepup.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stepup.course.dto.CreateProductRequest;
import ru.stepup.course.dto.CreateProductResponse;
import ru.stepup.course.service.ProductService;

@RestController
public class ProductController {
    CreateProductResponse createProductResponse;
    ProductService productService;
    @Autowired
    public ProductController(CreateProductResponse createProductResponse, ProductService productService) {
        this.createProductResponse = createProductResponse;
        this.productService = productService;
    }

    @PostMapping("/corporate-settlement-instance/create")
    public ResponseEntity<CreateProductResponse> handleInstanceCreate(@Validated @RequestBody CreateProductRequest product) {
        productService.addProduct(product, createProductResponse);
        if (createProductResponse.getStatus()==400) return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(createProductResponse);
        if (createProductResponse.getStatus()==404) return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(createProductResponse);
        if (createProductResponse.getStatus()==500) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(createProductResponse);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(createProductResponse);
    }

}
