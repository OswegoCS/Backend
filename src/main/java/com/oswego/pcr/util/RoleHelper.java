package com.oswego.pcr.util;

import java.util.List;

import com.oswego.pcr.models.Role;

public class RoleHelper {

    public static boolean hasRole(List<Role> roles, int roleId) {
        for (var role : roles) {
            if (role.getRoleId() == roleId)
                return true;
        }
        return false;
    }

}
