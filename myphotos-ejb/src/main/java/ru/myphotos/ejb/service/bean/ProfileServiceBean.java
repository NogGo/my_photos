package ru.myphotos.ejb.service.bean;

import ru.myphotos.common.annotation.cdi.Property;
import ru.myphotos.ejb.repository.ProfileRepository;
import ru.myphotos.exception.ObjectNotFoundException;
import ru.myphotos.model.AsyncOperation;
import ru.myphotos.model.ImageResource;
import ru.myphotos.model.domain.Profile;
import ru.myphotos.service.ProfileService;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService{
    //@LocalBean - для локального внедрения через название класса(через интерфейс можно ранее)

    @Inject
    @Property("myphotos.profile.avatar.placeholder.url")
    private String avatarPlaceHolderUrl;

    @Inject
    private ProfileRepository profileRepository;

    @Override
    public Profile findById(Long id) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by id: %s", id));
        }
        return profile.get();
    }

    @Override
    public Profile findByUid(String uid) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findByUid(uid);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by uid: %s", uid));
        }
        return profile.get();
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public void signUp(Profile profile, boolean uploadProfileAvatar) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void translitSocialProfile(Profile profile) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
        try {
            uploadNewAvatar(currentProfile, imageResource);
            asyncOperation.onSuccess(currentProfile);
        } catch (Throwable throwable) {
            setAvatarPlaceHolder(currentProfile);
            asyncOperation.onFailed(throwable);
        }
    }

    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource) {

    }

    public void setAvatarPlaceHolder(Long profileId) {
        setAvatarPlaceHolder(profileRepository.findById(profileId).get());
    }

    public void setAvatarPlaceHolder(Profile currentProfile) {
        if (currentProfile.getAvatarUrl() == null) {
            currentProfile.setAvatarUrl(avatarPlaceHolderUrl);
            profileRepository.update(currentProfile);
        }
    }

}