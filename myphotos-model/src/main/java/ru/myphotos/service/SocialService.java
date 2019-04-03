package ru.myphotos.service;

import ru.myphotos.exception.RetrieveSocialDataFailedException;
import ru.myphotos.model.domain.Profile;

public interface SocialService {
    Profile fetchProfile(String code) throws RetrieveSocialDataFailedException;

    String getClientId();

    default String getAuthorizeUrl() {
        throw new UnsupportedOperationException();
    }
}
