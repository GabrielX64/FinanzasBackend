package com.upc.finanzasbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAppDTO {
    private Long userID;
    private String names;
    private String surnames;
    private String username;
    private String password;
}
