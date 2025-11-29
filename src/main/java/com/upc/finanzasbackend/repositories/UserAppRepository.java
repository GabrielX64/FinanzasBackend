package com.upc.finanzasbackend.repositories;

import com.upc.finanzasbackend.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByUserAppID(Long userAppID);
    public boolean existsByEmail(String email);
}
