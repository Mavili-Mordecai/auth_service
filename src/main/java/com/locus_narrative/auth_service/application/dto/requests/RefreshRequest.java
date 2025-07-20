package com.locus_narrative.auth_service.application.dto.requests;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Request;
import com.locus_narrative.auth_service.application.dto.Responses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest extends Request {
    private String refresh;

    @Override
    public Optional<IResponse<String>> validate() {
        if (refresh == null || refresh.isBlank())
            return Optional.of(Responses.badRequest("The field `refresh` cannot be blank."));

        return super.validate();
    }
}
