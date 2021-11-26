package com.jwd.fShop.controller.command;

import java.util.Objects;

public enum Role {
    UNREGISTERED_USER,
    REGISTERED_USER,
    ADMIN;

    public boolean hasAccess(final Role role){
        boolean result = false;
        if(Objects.nonNull(role)){
            if(this.equals(role)){
                result = true;
            }
        }else {
            if(this.equals(Role.UNREGISTERED_USER)){
                result = true;
            }
        }
        return result;
    }
    public boolean hasAccess(final Integer role){
        boolean result = false;
        if(Objects.nonNull(role)){
            result = this.equals(Role.UNREGISTERED_USER);
        }else {
            if(this.equals(Role.values()[role])){
                result = true;
            }
        }
        return result;
    }
    public boolean hasAccess(final String role){
        boolean result = false;
        if(Objects.isNull(role)){
            result = this.equals(Role.UNREGISTERED_USER);
        }else {
            Role roleToCheck = Role.valueOf(role);
            if(this.ordinal()<= roleToCheck.ordinal())
                result = true;
            else
                result = false;
        }
        return result;
    }
}
