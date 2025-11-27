package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByUserID(long userID);
}
