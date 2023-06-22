package com.notes.appuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.multipart.MultipartFile;

import com.notes.notfounderrortools.NotFoundValuesException;
import com.notes.passworderrortools.InvalidPasswordException;
import com.notes.validations.ValidationModelError;

@RestController
@RequestMapping("/app-users")
@CrossOrigin(origins = "http://localhost:3000")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Exception handler for AppUserPasswordValidator
    @ExceptionHandler({ InvalidPasswordException.class })
    public ResponseEntity<Object> handleAppUserPasswordValidatorException(InvalidPasswordException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Exception handler for NotFoundValues
    @ExceptionHandler({ NotFoundValuesException.class })
    public ResponseEntity<Object> handleNotFoundValuesException(NotFoundValuesException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ ValidationModelError.class })
    public ResponseEntity<Object> handleNotFoundValuesException(ValidationModelError ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> findAll() {
        List<AppUser> appUsers = appUserService.findAll();
        if (!appUsers.isEmpty()) {
            return new ResponseEntity<>(appUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findById(@PathVariable Long id) {
        Optional<AppUser> appUser = appUserService.findById(id);
        if (appUser.isPresent()) {
            return new ResponseEntity<>(appUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping
    public ResponseEntity<AppUser> save(@RequestBody AppUserDTO appUserDTO) throws ConstraintViolationException {
        AppUser savedAppUser = appUserService.save(appUserDTO);
        return new ResponseEntity<>(savedAppUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppUser> deleteById(@PathVariable Long id) {
        appUserService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateAppUser(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO) {
        AppUser updatedAppUser = appUserService.updatAppUser(id, appUserDTO);
        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }

    @PutMapping("/upload-avatar/{id}")
    public ResponseEntity<AppUser> handleAvatarUpload(@RequestParam("avatar") MultipartFile avatar,
            @PathVariable("id") Long id) {

        String avatarUrl = appUserService.saveAvatarToServer(avatar);

        Optional<AppUser> appUserOptional = appUserService.findById(id);
        if (!appUserOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setAvatar(avatarUrl);
        AppUser updatedAppUser = appUserService.updatAppUser(id, appUserDTO);

        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }

    @PutMapping("update-password/{id}")
    public ResponseEntity<AppUser> updatePassword(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO) {
        AppUser updatedAppUser = appUserService.updatePassword(id, appUserDTO);
        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }

}
