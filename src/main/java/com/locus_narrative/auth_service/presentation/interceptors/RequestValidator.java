package com.locus_narrative.auth_service.presentation.interceptors;

import com.locus_narrative.auth_service.application.dto.Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request request = (Request) target;
        request.validate().ifPresent(error -> {
            errors.reject("invalid.request", error.getContent());
        });
    }
}
