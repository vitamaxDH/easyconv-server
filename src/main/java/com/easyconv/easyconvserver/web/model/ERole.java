package com.easyconv.easyconvserver.web.model;

import com.easyconv.easyconvserver.core.exception.RoleNotFoundException;
import org.hibernate.annotations.NotFound;

public enum ERole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private String roleStr;

    ERole(String roleStr) {
        this.roleStr = roleStr;
    }

    public String getRoleStr(){
        return roleStr;
    }

    public static ERole getRoleStr(String role){
        ERole result = null;
        for(ERole eRole : values()){
            if (eRole.getRoleStr().equals(role)){
                result = eRole;
            }
        }
        return result;
    }
}
