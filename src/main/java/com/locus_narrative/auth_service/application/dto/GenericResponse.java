package com.locus_narrative.auth_service.application.dto;

public record GenericResponse<T>(T content, int status) implements IResponse<T> {
}
