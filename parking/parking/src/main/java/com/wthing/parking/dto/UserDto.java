package com.wthing.parking.dto;

import com.wthing.parking.enums.AuthoritiesEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private AuthoritiesEnum role;
    private Integer iin;
    private boolean enabled;
}
