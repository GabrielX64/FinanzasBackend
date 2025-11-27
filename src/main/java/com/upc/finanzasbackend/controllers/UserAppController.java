package com.upc.finanzasbackend.controllers;

import com.upc.finanzasbackend.Interfaces.IUserAppService;
import com.upc.finanzasbackend.dtos.UserAppDTO;
import com.upc.finanzasbackend.entities.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
@Slf4j
public class UserAppController {
    @Autowired
    private IUserAppService userAppService;

    @GetMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<UserAppDTO> getUsers(){
        ModelMapper mapper = new ModelMapper();
        List<UserApp> userApps = userAppService.getUserApps();
        List<UserAppDTO> userAppDTO = Arrays.asList(mapper.map(userApps, UserAppDTO[].class));
        return userAppDTO;
    }
    @PostMapping("/user")
    public ResponseEntity<UserAppDTO> registerUser(@RequestBody UserAppDTO userAppDTO){
        ModelMapper mapper = new ModelMapper();
        UserApp userApp = mapper.map(userAppDTO, UserApp.class);
        userApp = userAppService.registerUserApp(userApp);
        userAppDTO = mapper.map(userApp, UserAppDTO.class);
        return ResponseEntity.ok(userAppDTO);
    }

    @PutMapping("/user/update")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserAppDTO> updateUser(@RequestBody UserAppDTO userAppDTO){
        ModelMapper mapper = new ModelMapper();
        UserApp userApp = mapper.map(userAppDTO, UserApp.class);
        userApp = userAppService.updateUserApp(userApp);
        userAppDTO = mapper.map(userApp, UserAppDTO.class);
        return ResponseEntity.ok(userAppDTO);
    }

    @DeleteMapping("/user/delete/{id}")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deleteUser(@PathVariable Long id) throws Exception{
        userAppService.deleteUserApp(id);
    }
}
