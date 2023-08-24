package com.notes.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notes.notfounderrortools.NotFoundValuesException;

@RestController
@RequestMapping("/collection")
@CrossOrigin(origins = "http://localhost:5173")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @ExceptionHandler({ NotFoundValuesException.class })
    public ResponseEntity<Object> handleNotFoundValuesException(NotFoundValuesException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping
    public ResponseEntity<Iterable<Collection>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Iterable<Collection> allCollections = collectionService.findAll();

        List<Collection> collectionList = new ArrayList<>();
        allCollections.forEach(collectionList::add);

        collectionList.sort((c1, c2) -> Long.compare(c2.getId(), c1.getId()));

        int start = page * size;
        int end = Math.min(start + size, collectionList.size());
        List<Collection> paginatedList = collectionList.subList(start, end);

        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> findAllAndCount() {
    Iterable<Collection> collections = collectionService.findAll();
    List<Collection> allCollections = new ArrayList<>(); // Declarar la lista con tipo genérico
    for (Collection collection : collections) {
        allCollections.add(collection);
    }
    return new ResponseEntity<>(allCollections.size(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> findById(@PathVariable Long id) {
        Optional<Collection> collection = collectionService.findById(id);
        if (collection.isPresent()) {
            return new ResponseEntity<>(collection.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Collection> save(@RequestBody CollectionDTO collectionDTO) {
        Collection savedCollection = collectionService.save(collectionDTO);
        return new ResponseEntity<>(savedCollection, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Collection> deleteById(@PathVariable Long id) {
        Optional<Collection> collection = collectionService.findById(id);
        if (!collection.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        collectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
        Collection updatedCollection = collectionService.updateCollection(id, collectionDTO);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

}
