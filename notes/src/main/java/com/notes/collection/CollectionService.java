package com.notes.collection;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.appuser.AppUserRepository;
import com.notes.notfounderrortools.NotFoundValuesException;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Iterable<Collection> findAll() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> findById(Long id) {
        return collectionRepository.findById(id);
    }

    public Collection save(CollectionDTO collectionDTO) {

        if (!appUserRepository.findById(collectionDTO.getAppUserId()).isPresent()) {
            throw new NotFoundValuesException("AppUser with id: " + collectionDTO.getAppUserId() + " not found");
        }
        Collection collection = new Collection();
        if (collectionDTO.getName() == null || collectionDTO.getName().equals("")) {
            throw new NotFoundValuesException("Collection name cannot be empty");
        }

        collection.setName(collectionDTO.getName());
        collection.setAppUserId(appUserRepository.findById(collectionDTO.getAppUserId()).get());
        

        return collectionRepository.save(collection);
    }

    public void deleteById(Long id) {
        if (!collectionRepository.findById(id).isPresent()) {
            throw new NotFoundValuesException("Collection with id: " + id + " not found");
        }
        collectionRepository.deleteById(id);
    }

    public Collection updateCollection(Long id, CollectionDTO collectionDTO) {
        if (!collectionRepository.findById(id).isPresent()) {
            throw new NotFoundValuesException("Collection with id: " + id + " not found");
        }
        Collection collectionToUpdate = collectionRepository.findById(id).get();
        if (collectionDTO.getName() == null || collectionDTO.getName().equals("")) {
            throw new NotFoundValuesException("Collection name cannot be empty");
        }
        collectionToUpdate.setName(collectionDTO.getName());
        return collectionRepository.save(collectionToUpdate);
    }

}
