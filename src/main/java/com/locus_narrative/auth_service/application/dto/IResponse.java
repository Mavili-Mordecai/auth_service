package com.locus_narrative.auth_service.application.dto;

public interface IResponse<T> {
    T getContent();
    int getStatus();
}
