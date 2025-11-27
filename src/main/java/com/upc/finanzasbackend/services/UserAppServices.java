package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.IUserAppService;
import com.upc.finanzasbackend.entities.UserApp;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppServices implements IUserAppService {
    @Autowired
    private UserAppRepository userAppRepository;

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
        //UserApp existingUserApp = userAppRepository.findByUserID(userApp.getUserID());
        /*
        if (userAppRepository.findById(userApp.getUserID()).isPresent()) {
            return userAppRepository.save(userApp);
        } else {
            throw new RequestException("E-006", HttpStatus.NOT_FOUND,"El usuario a actualizar no existe");
        }
        */
        return userAppRepository.save(userApp);
    }

    @Override
    public void deleteUserApp(Long userID) {
        if (userAppRepository.existsById(userID)) {
            userAppRepository.deleteById(userID);
        }else{
            throw new RequestException("E-006", HttpStatus.NOT_FOUND,"El usuario a eliminar no existe");
        }
    }
}