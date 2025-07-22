package com.locus_narrative.auth_service.presentation.schemes;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.responses.TokenResponse;

public class JwtTokensResponseScheme implements IResponse<TokenResponse> {
    @Override
    public TokenResponse getContent() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }
}
