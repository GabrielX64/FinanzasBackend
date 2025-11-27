package com.upc.finanzasbackend.Interfaces;

import com.upc.finanzasbackend.entities.UserApp;

import java.util.List;

public interface IUserAppService {
    public List<UserApp> getUserApps();
    public UserApp registerUserApp(UserApp userApp);
    public UserApp updateUserApp(UserApp userApp);
    public void deleteUserApp(Long userID);
}
