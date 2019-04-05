package ru.myphotos.ejb.service.bean;

import ru.myphotos.ejb.repository.PhotoRepository;
import ru.myphotos.ejb.repository.ProfileRepository;
import ru.myphotos.exception.ObjectNotFoundException;
import ru.myphotos.exception.ValidationException;
import ru.myphotos.model.*;
import ru.myphotos.model.domain.Photo;
import ru.myphotos.model.domain.Profile;
import ru.myphotos.service.PhotoService;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Stateless
@LocalBean
@Local(PhotoService.class)
public class PhotoServiceBean implements PhotoService {

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Resource
    private SessionContext sessionContext;

    @Override
    public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
        return photoRepository.findProfilePhotosLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
    }

    @Override
    public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
        switch (sortMode) {
            case POPULAR_AUTHOR:
                return photoRepository.findAllOrderByAuthorRatingDesc(pageable.getOffset(), pageable.getLimit());
            case POPULAR_PHOTO:
                return photoRepository.findAllOrderByViewsDesc(pageable.getOffset(), pageable.getLimit());
            default:
                throw new ValidationException("Unsupported sort mode: " + sortMode);
        }
    }

    @Override
    public long countAllPhotos() {
        return photoRepository.countAll();
    }

    @Override
    public String viewLargePhoto(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setViews(photo.getViews() + 1);
        photoRepository.update(photo);
        return photo.getLargeUrl();
    }

    public Photo getPhoto(Long photoId) throws ObjectNotFoundException {
        Optional<Photo> photo = photoRepository.findById(photoId);
        if (!photo.isPresent()) {
            throw new ObjectNotFoundException(format("Photo not found by id: %s", photoId));
        }
        return photo.get();
    }

    @Override
    public OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setDownloads(photo.getDownloads() + 1);
        photoRepository.update(photo);

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {
        try {
            Photo photo = null; //FIXME
            asyncOperation.onSuccess(photo);
        } catch (Throwable throwable) {
            sessionContext.setRollbackOnly();
            asyncOperation.onFailed(throwable);
        }
    }

}