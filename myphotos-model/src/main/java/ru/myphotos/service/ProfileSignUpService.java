package ru.myphotos.service;

import ru.myphotos.exception.ObjectNotFoundException;
import ru.myphotos.model.domain.Profile;

public interface ProfileSignUpService {

    void createSignUpProfile(Profile profile);

    Profile getCurrentProfile() throws ObjectNotFoundException;

    void completeSignUp();

    void cancel();
}
