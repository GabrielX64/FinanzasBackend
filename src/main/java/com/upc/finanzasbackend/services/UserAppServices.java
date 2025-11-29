package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.IUserAppService;
import com.upc.finanzasbackend.entities.UserApp;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.UserAppRepository;
import com.upc.finanzasbackend.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserAppServices implements IUserAppService {
    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<UserApp> getUserApps() {
        return userAppRepository.findAll();
    }

    @Override
    public UserApp registerUserApp(UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    @Override
    public UserApp updateUserApp(UserApp userApp) {

        UserApp existingUserApp = userAppRepository.findByUserAppID(userApp.getUserAppID());
        if (userAppRepository.existsById(userApp.getUserAppID())) {
            return userAppRepository.save(userApp);
        } else {
            throw new RequestException("E-006", HttpStatus.NOT_FOUND,"El usuario a actualizar no existe");
        }
        //return userAppRepository.save(userApp);
    }

    @Override
    public void deleteUserApp(Long userID) {
        if (!userAppRepository.existsById(userID)) {
            throw new RequestException("E-006", HttpStatus.NOT_FOUND,"El usuario a eliminar no existe");
        }
        userAppRepository.deleteById(userID);
    }
}