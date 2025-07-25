package com.locus_narrative.auth_service.persistence.repositories;

import com.locus_narrative.auth_service.persistence.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserModel, Long> {
    @Modifying
    @Query("DELETE FROM UserModel um WHERE um.uuid = :uuid")
    int deleteByUuid(@Param("uuid") UUID uuid);

    Optional<UserModel> findByLogin(String login);
    Optional<UserModel> findByUuid(UUID uuid);
}
