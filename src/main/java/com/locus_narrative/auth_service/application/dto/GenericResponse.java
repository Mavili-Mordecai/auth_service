package com.locus_narrative.auth_service.application.dto;

import lombok.Value;

@Value
public class GenericResponse<T> implements IResponse<T> {
    T content;
    int status;
}
