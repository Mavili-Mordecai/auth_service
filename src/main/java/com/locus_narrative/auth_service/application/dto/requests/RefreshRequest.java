package com.locus_narrative.auth_service.application.dto.requests;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Request;
import com.locus_narrative.auth_service.application.dto.Responses;

import java.util.Optional;

public class RefreshRequest extends Request {
    private String refresh;

    public RefreshRequest() {
    }

    public RefreshRequest(String refresh) {
        this.refresh = refresh;
    }

    @Override
    public Optional<IResponse<String>> validate() {
        if (refresh == null || refresh.isBlank())
            return Optional.of(Responses.badRequest("The field `refresh` cannot be blank."));

        return super.validate();
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}
