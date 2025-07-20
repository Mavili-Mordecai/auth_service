package com.locus_narrative.auth_service.persistence.adapters;

import com.locus_narrative.auth_service.domain.Either;
import com.locus_narrative.auth_service.domain.entities.EUserSignUpError;
import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.ports.IUserPort;
import com.locus_narrative.auth_service.persistence.mappers.UserMapper;
import com.locus_narrative.auth_service.persistence.models.UserModel;
import com.locus_narrative.auth_service.persistence.repositories.IUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements IUserPort {
    private final UserMapper mapper;
    private final IUserJpaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) {
        Optional<UserModel> model = repository.findByLogin(login);
        if (model.isEmpty()) return new UserEntity();
        return mapper.toEntity(model.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUuid(UUID uuid) {
        Optional<UserModel> model = repository.findByUuid(uuid);
        if (model.isEmpty()) return new UserEntity();
        return mapper.toEntity(model.get());
    }

    @Override
    @Transactional
    public Either<EUserSignUpError, UserEntity> insert(UserEntity entity) {
        Optional<UserModel> model = repository.findByLogin(entity.getLogin());
        if (model.isPresent()) return Either.left(EUserSignUpError.LOGIN_BUSY);

        return Either.right(mapper.toEntity(repository.save(mapper.toModel(entity))));
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity entity) {
        assert entity.getUuid() != null;

        Optional<UserModel> model = repository.findByUuid(entity.getUuid());
        if (model.isEmpty()) return new UserEntity();

        if (entity.getLogin() != null && !entity.getLogin().isBlank())
            model.get().setLogin(entity.getLogin());

        if (entity.getPassword() != null && !entity.getPassword().isBlank())
            model.get().setPassword(entity.getPassword());

        return mapper.toEntity(repository.save(model.get()));
    }

    @Override
    @Transactional
    public boolean deleteByUuid(UUID uuid) {
        return repository.deleteByUuid(uuid) > 0;
    }
}
