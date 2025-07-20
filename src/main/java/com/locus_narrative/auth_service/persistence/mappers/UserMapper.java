package com.locus_narrative.auth_service.persistence.mappers;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.persistence.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserModel model) {
        return new UserEntity(
                model.getId(),
                model.getUuid(),
                model.getLogin(),
                model.getPassword()
        );
    }

    public UserModel toModel(UserEntity entity) {
        return UserModel.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }
}
