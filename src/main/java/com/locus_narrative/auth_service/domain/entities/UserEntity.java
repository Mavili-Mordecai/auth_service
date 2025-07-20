package com.locus_narrative.auth_service.domain.entities;

import java.util.UUID;

public class UserEntity {
    private Long id = null;
    private UUID uuid;
    private String login;
    private String password;

    public UserEntity() {
    }

    public UserEntity(Long id, UUID uuid, String login, String password) {
        this.id = id;
        this.uuid = uuid;
        this.login = login;
        this.password = password;
    }

    public UserEntity(UUID uuid, String login, String password) {
        this.uuid = uuid;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
