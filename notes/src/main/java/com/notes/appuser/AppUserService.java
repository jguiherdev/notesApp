package com.notes.appuser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.notes.notfounderrortools.NotFoundValuesException;
import com.notes.validations.AppUserValidatorTools;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    AppUserValidatorTools validator = new AppUserValidatorTools();

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser save(AppUserDTO appUserDTO) {
        String encodedPasword = passwordEncoder.encode(appUserDTO.getPassword());
        appUserDTO.setPassword(encodedPasword);
        return appUserRepository.save(validator.appUserValidator(appUserDTO));
    }

    public void deleteById(Long id) {
        if (appUserRepository.findById(id).isPresent()) {
            appUserRepository.deleteById(id);
        } else {
            throw new NotFoundValuesException("AppUser not found");
        }
    }

    public AppUser updatAppUser(Long id, AppUserDTO appUserDTO) {
        if (appUserRepository.findById(id).isPresent()) {
            AppUser appUserToUpdate = appUserRepository.findById(id).get();
            return appUserRepository.save(validator.updateAppUserValidator(appUserToUpdate, appUserDTO));
        } else {
            throw new NotFoundValuesException("AppUser not found");
        }

    }

    public AppUser setAvatar(Long id, String avatar) {
        AppUser appUserToUpdate = appUserRepository.findById(id).get();
        appUserToUpdate.setAvatar(avatar);
        return appUserRepository.save(appUserToUpdate);
    }

    public String saveAvatarToServer(MultipartFile avatar) {
        String filename = avatar.getOriginalFilename();
        File file = new File("C:/Users/Javier.Guillen/notesapp/avatars/" + filename);

        try {
            avatar.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "C:/Users/Javier.Guillen/notesapp/avatars/" + filename;
    }

    public AppUser updatePassword(Long id, AppUserDTO appUserDTO) {
        if (appUserRepository.findById(id).isPresent()) {
            AppUser appUserToUpdate = appUserRepository.findById(id).get();
            return appUserRepository.save(validator.updatePasswordValidator(appUserToUpdate, appUserDTO));
        } else {
            throw new NotFoundValuesException("AppUser not found");
        }
    }

}
