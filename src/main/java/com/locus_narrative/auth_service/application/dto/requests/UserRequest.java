package com.locus_narrative.auth_service.application.dto.requests;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Request;
import com.locus_narrative.auth_service.application.dto.Responses;

import java.util.Optional;

public class UserRequest extends Request {
    private String login;
    private String password;

    public UserRequest() {

    }

    public UserRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Optional<IResponse<String>> validate() {
        if (login == null || login.isBlank())
            return Optional.of(Responses.badRequest("The field `login` cannot be blank."));

        if (password == null || password.isBlank())
            return Optional.of(Responses.badRequest("The field `password` cannot be blank."));

        return super.validate();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
