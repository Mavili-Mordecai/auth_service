package com.locus_narrative.auth_service.presentation.schemes;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.responses.TokenResponse;

public class JwtTokensResponseScheme implements IResponse<TokenResponse> {
    @Override
    public TokenResponse content() {
        return null;
    }

    @Override
    public int status() {
        return 0;
    }
}
