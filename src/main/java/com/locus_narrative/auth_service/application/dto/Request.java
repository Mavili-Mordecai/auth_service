package com.locus_narrative.auth_service.application.dto;

import java.util.Optional;

abstract public class Request {
    public Optional<IResponse<String>> validate() {
        return Optional.empty();
    }
}
