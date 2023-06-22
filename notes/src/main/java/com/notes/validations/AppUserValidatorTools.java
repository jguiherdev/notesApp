package com.notes.validations;

import com.notes.appuser.AppUser;
import com.notes.appuser.AppUserDTO;
import com.notes.passworderrortools.InvalidPasswordException;

public class AppUserValidatorTools {

    AppUserPasswordValidator passwordValidator = new AppUserPasswordValidator();

    public AppUser appUserValidator(AppUserDTO appUserDTO) {
        AppUser appUserToSave = new AppUser();
        if (appUserDTO.getNick() != null || !appUserDTO.getNick().equals("")) {
            appUserToSave.setNick(appUserDTO.getNick());
        } else {
            throw new ValidationModelError("Nick is not valid");
        }
        if (appUserDTO.getMail() != null || !appUserDTO.getMail().equals("")) {
            appUserToSave.setMail(appUserDTO.getMail());
        } else {
            throw new ValidationModelError("Mail is not valid");
        }
        if (passwordValidator.isValid(appUserDTO.getPassword())) {
            appUserToSave.setPassword(appUserDTO.getPassword());
        } else {
            throw new InvalidPasswordException("Password is not valid");
        }
        if (passwordValidator.isMatched(appUserDTO.getPassword(), appUserDTO.getConfirmPassword())) {
            appUserToSave.setPassword(appUserDTO.getPassword());
        } else {
            throw new InvalidPasswordException("Password is not matched");
        }
        if (!appUserDTO.getPhone().equals("") || appUserDTO.getPhone() != null) {
            appUserToSave.setPhone(appUserDTO.getPhone());
        } else {
            throw new ValidationModelError("Phone is not valid");
        }
        // if (appUserDTO.getAvatar() != null || !appUserDTO.getAvatar().equals("")) {
        //     appUserToSave.setAvatar(appUserDTO.getAvatar());
        // }
        return appUserToSave;
    }

    public AppUser updateAppUserValidator(AppUser appUserToUpdate, AppUserDTO appUserDTO) {

        if (appUserDTO.getNick() != null || !appUserDTO.getNick().equals("")) {
            appUserToUpdate.setNick(appUserDTO.getNick());
        }
        if (appUserDTO.getMail() != null || !appUserDTO.getMail().equals("")) {
            appUserToUpdate.setMail(appUserDTO.getMail());
        }

        if (!appUserDTO.getPhone().equals("") || appUserDTO.getPhone() != null) {
            appUserToUpdate.setPhone(appUserDTO.getPhone());
        }

        return appUserToUpdate;
    }

    public AppUser updatePasswordValidator(AppUser appUserToUpdate, AppUserDTO appUserDTO) {
        if (!passwordValidator.isValid(appUserDTO.getPassword())) {
            throw new InvalidPasswordException("Password is not valid");
        }
        if (!passwordValidator.isMatched(appUserDTO.getPassword(), appUserDTO.getConfirmPassword())) {
            throw new InvalidPasswordException("Password is not matched");
        }
        appUserToUpdate.setPassword(appUserDTO.getPassword());
        return appUserToUpdate;
    }

}
