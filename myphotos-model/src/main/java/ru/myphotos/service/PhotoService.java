package ru.myphotos.service;

import ru.myphotos.exception.ObjectNotFoundException;
import ru.myphotos.model.*;
import ru.myphotos.model.domain.Photo;
import ru.myphotos.model.domain.Profile;

import java.util.List;

public interface PhotoService {

    List<Photo> findProfilePhotos(Long profileId, Pageable pageable);

    List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);

    long countAllPhotos();

    String viewLargePhoto(Long photoId) throws ObjectNotFoundException;

    OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException;

    void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation);
}
