package com.locus_narrative.auth_service.application.dto;

public enum RequestStatus {
    OK(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);

    private final int status;

    RequestStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}
