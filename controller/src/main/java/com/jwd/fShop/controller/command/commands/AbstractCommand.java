package com.jwd.fShop.controller.command.commands;


import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.InvalidSessionException;

public abstract class AbstractCommand implements Command {
    protected final ServicePack servicePack;
    protected final Role role;
    protected AbstractCommand(final ServicePack servicePack, final Role role){
        this.servicePack = servicePack;
        this.role = role;
    }

    protected void validateSessionRole(final Integer role)throws InvalidSessionException{
        if(this.role.hasAccess(role)){
           return;
        }else {
            throw new InvalidSessionException();
        }
    }
    protected void validateSessionRole(final String role)throws InvalidSessionException{
        if(this.role.hasAccess(role)){
            return;
        }else {
            throw new InvalidSessionException();
        }
    }
}
