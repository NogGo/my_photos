package ru.myphotos.service;

import ru.myphotos.exception.ObjectNotFoundException;
import ru.myphotos.model.AsyncOperation;
import ru.myphotos.model.ImageResource;
import ru.myphotos.model.domain.Profile;

import java.util.Optional;

public interface ProfileService {

    //характер. структуры хранения данных!
    Profile findById(Long id) throws ObjectNotFoundException; //TODO: позже переделать, получить объект у него

    Profile findByUid(String uid) throws ObjectNotFoundException;

    Optional<Profile> findByEmail(String email);

    void signUp(Profile profile, boolean uploadProfileAvatar);

    void translitSocialProfile(Profile profile);

    void update(Profile profile);

    void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation);
}
