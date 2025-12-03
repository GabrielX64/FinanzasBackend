package com.upc.finanzasbackend.security.services;


import com.upc.finanzasbackend.security.entities.Role;
import com.upc.finanzasbackend.security.entities.User;
import com.upc.finanzasbackend.security.repositories.RoleRepository;
import com.upc.finanzasbackend.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserServices  {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void save(User user) {
        Role roleUser = roleRepository.findById(1);
        user.setRole(roleUser);
        userRepository.save(user);
    }

    public Integer insertUserRol(Long user_id, Long rol_id) {
        Integer result = 0;
        userRepository.insertUserRol(user_id, rol_id);
        return 1;
    }
}
