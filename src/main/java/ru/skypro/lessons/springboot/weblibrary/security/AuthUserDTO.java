package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDTO {
    private Long id;
    private String userName;
    private String password;
    private RoleType roleName;

    public static AuthUserDTO fromAuthUser(AuthUser authUser) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setId(authUser.getId());
        authUserDTO.setUserName(authUser.getUserName());
        authUserDTO.setPassword(authUser.getPassword());
        authUserDTO.setRoleName(authUser.getRoleName());
        return authUserDTO;
    }
}