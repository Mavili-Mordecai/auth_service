package com.locus_narrative.auth_service.application.dto.responses;

public class TokenResponse {
    private String access;
    private String refresh;

    public TokenResponse(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
