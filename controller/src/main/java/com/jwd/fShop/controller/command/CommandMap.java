package com.jwd.fShop.controller.command;

import com.jwd.fShop.controller.command.commands.*;
import com.jwd.fShop.controller.domain.ServicePack;

import java.util.HashMap;

public class CommandMap {
    private HashMap<String, Command> commandMap;

    public CommandMap(final ServicePack servicePack){
        commandMap = new HashMap<>();

        commandMap.put(CommandAliases.DefaultAlias, new ShowDefaultPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowDefaultPageCommandAlias, new ShowDefaultPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowProductInfoPageCommandAlias, new ShowProductInfoPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowProductListPageCommandAlias, new ShowProductListPageCommand(servicePack));
        commandMap.put(CommandAliases.GetProductListPageCommandAlias, new GetProductListPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowAddProductPageAlias, new ShowAddProductPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowAdministrationPageAlias, new ShowAdministrationPageCommand(servicePack));
        commandMap.put(CommandAliases.AddProductCommandAlias, new AddProductCommand(servicePack));
        commandMap.put(CommandAliases.ShowAuthenticationPageAlias, new ShowAuthenticationPageCommand(servicePack));
        commandMap.put(CommandAliases.ShowRegistrationPageAlias, new ShowRegistrationPageCommand(servicePack));
        commandMap.put(CommandAliases.AuthenticationAlias, new AuthenticateUserCommand(servicePack));
        commandMap.put(CommandAliases.RegistrationAlias, new RegisterUserCommand(servicePack));
        commandMap.put(CommandAliases.LogoutAlias, new LogoutUserCommand(servicePack));
        commandMap.put(CommandAliases.GetProductAlias, new GetProductCommand(servicePack));

    }

    public Command getCommandByAlias(String alias){
        return commandMap.get(alias);
    }
}

class CommandAliases {
    static String DefaultAlias = null;
    static String ShowDefaultPageCommandAlias = "ShowDefaultPage";
    static String ShowProductInfoPageCommandAlias = "ShowProductInfoPage";
    static String ShowProductListPageCommandAlias = "ShowProductListPage";
    static String ShowAddProductPageAlias = "ShowAddProductPage";
    static String ShowAdministrationPageAlias = "ShowAdministrationPage";


    static String GetProductListPageCommandAlias = "GetProductListPage";
    static String AddProductCommandAlias = "AddProduct";
    static String ShowRegistrationPageAlias = "ShowRegistrationPage";
    static String ShowAuthenticationPageAlias = "ShowAuthenticationPage";
    static String AuthenticationAlias = "Authenticate";
    static String RegistrationAlias = "Register";
    static String LogoutAlias = "Logout";
    static String GetProductAlias = "GetProduct";

}
