package com.locus_narrative.auth_service.application.dto;

public interface IResponse<T> {
    T content();
    int status();
}
