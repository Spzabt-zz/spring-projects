package org.bohdan.springboot.booktracking2boot.util.converters;

import org.bohdan.springboot.booktracking2boot.models.enums.Role;

import java.util.Arrays;

public class SpecificRolesToArray {
    public static Role[] getRoleArray() {
        return Arrays.stream(Role.values()).filter(role -> !role.equals(Role.ADMIN)).toArray(Role[]::new);
    }
}
