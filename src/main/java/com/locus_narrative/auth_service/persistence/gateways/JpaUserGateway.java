package com.locus_narrative.auth_service.persistence.gateways;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.LoginIsBusyException;
import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;
import com.locus_narrative.auth_service.domain.gateways.UserPort;
import com.locus_narrative.auth_service.persistence.mappers.UserMapper;
import com.locus_narrative.auth_service.persistence.models.UserModel;
import com.locus_narrative.auth_service.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaUserGateway implements UserPort {
    private final UserMapper mapper;
    private final JpaUserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) throws UserNotFoundException {
        Optional<UserModel> model = repository.findByLogin(login);

        if (model.isEmpty())
            throw new UserNotFoundException("User not found");

        return mapper.toEntity(model.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUuid(UUID uuid) throws UserNotFoundException {
        Optional<UserModel> model = repository.findByUuid(uuid);

        if (model.isEmpty())
            throw new UserNotFoundException("User not found");

        return mapper.toEntity(model.get());
    }

    @Override
    @Transactional
    public UserEntity insert(UserEntity entity) throws LoginIsBusyException {
        Optional<UserModel> model = repository.findByLogin(entity.getLogin());

        if (model.isPresent())
            throw new LoginIsBusyException("Login is busy");

        return mapper.toEntity(repository.save(mapper.toModel(entity)));
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity entity) throws UserNotFoundException {
        assert entity.getUuid() != null;

        Optional<UserModel> model = repository.findByUuid(entity.getUuid());

        if (model.isEmpty())
            throw new UserNotFoundException("User not found");

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
