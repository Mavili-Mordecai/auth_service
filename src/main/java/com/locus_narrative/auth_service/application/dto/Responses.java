package com.locus_narrative.auth_service.application.dto;

public final class Responses {
    public static <T> IResponse<T> ok(T data) {
        return new GenericResponse<>(data, RequestStatus.OK.status());
    }

    public static IResponse<String> ok() {
        return new GenericResponse<>("ok", RequestStatus.OK.status());
    }

    public static <T> IResponse<T> created(T data) {
        return new GenericResponse<>(data, RequestStatus.CREATED.status());
    }

    public static IResponse<String> error(String message, int status) {
        return new GenericResponse<>(message, status);
    }

    public static IResponse<String> badRequest(String message) {
        return error(message, RequestStatus.BAD_REQUEST.status());
    }

    public static IResponse<String> notFound(String message) {
        return error(message, RequestStatus.NOT_FOUND.status());
    }

    public static IResponse<String> unauthorized(String message) {
        return error(message, RequestStatus.UNAUTHORIZED.status());
    }

    public static IResponse<String> forbidden(String message) {
        return error(message, RequestStatus.FORBIDDEN.status());
    }

    public static IResponse<String> conflict(String message) {
        return error(message, RequestStatus.CONFLICT.status());
    }

    public static IResponse<String> internalError() {
        return error("Internal server error.", RequestStatus.INTERNAL_SERVER_ERROR.status());
    }
}
