package ru.myphotos.service;

import ru.myphotos.exception.AccessForbiddenException;
import ru.myphotos.exception.InvalidAccessTokenException;
import ru.myphotos.model.domain.AccessToken;
import ru.myphotos.model.domain.Profile;

public interface AccessTokenService {
    AccessToken generateAccessToken(Profile profile);

    Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException;

    void invalidateAccessToken(String token) throws InvalidAccessTokenException;
}
