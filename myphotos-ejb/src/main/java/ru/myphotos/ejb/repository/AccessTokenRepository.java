package ru.myphotos.ejb.repository;

import ru.myphotos.model.domain.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String>{

    Optional<AccessToken> findByToken(String token);

    boolean removeAccessToken(String token);
}