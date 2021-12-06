package com.jwd.fShop.controller.command;

import com.jwd.fShop.controller.command.impl.*;

import java.util.HashMap;

import static com.jwd.fShop.controller.command.CommandMap.CommandAliases.*;

public class CommandMap {
    private HashMap<String, Command> commandMap;

    public CommandMap() {
        commandMap = new HashMap<>();

        commandMap.put(DEFAULT_ALIAS.getAlias(), new ShowDefaultPageCommand());
        commandMap.put(SHOW_DEFAULT_PAGE_COMMAND_ALIAS.getAlias(), new ShowDefaultPageCommand());
        commandMap.put(SHOW_PRODUCT_INFO_PAGE_COMMAND_ALIAS.getAlias(), new ShowProductInfoPageCommand());
        commandMap.put(SHOW_PRODUCT_LIST_PAGE_COMMAND_ALIAS.getAlias(), new ShowProductListPageCommand());
        commandMap.put(GetProductListPageCommandAlias.getAlias(), new GetProductListPageCommand());
        commandMap.put(ShowAddProductPageAlias.getAlias(), new ShowAddProductPageCommand());
        commandMap.put(ShowAdministrationPageAlias.getAlias(), new ShowAdministrationPageCommand());
        commandMap.put(AddProductCommandAlias.getAlias(), new AddProductCommand());
        commandMap.put(ShowAuthenticationPageAlias.getAlias(), new ShowAuthenticationPageCommand());
        commandMap.put(ShowRegistrationPageAlias.getAlias(), new ShowRegistrationPageCommand());
        commandMap.put(AuthenticationAlias.getAlias(), new AuthenticateUserCommand());
        commandMap.put(RegistrationAlias.getAlias(), new RegisterUserCommand());
        commandMap.put(LogoutAlias.getAlias(), new LogoutUserCommand());
        commandMap.put(GetProductAlias.getAlias(), new GetProductCommand());

    }

    public Command getCommandByAlias(String alias) {
        return commandMap.get(alias);
    }

    enum CommandAliases {
        DEFAULT_ALIAS(null),
        SHOW_DEFAULT_PAGE_COMMAND_ALIAS("ShowDefaultPage"),
        SHOW_PRODUCT_INFO_PAGE_COMMAND_ALIAS("ShowProductInfoPage"),
        SHOW_PRODUCT_LIST_PAGE_COMMAND_ALIAS("ShowProductListPage"),
        ShowAddProductPageAlias("ShowAddProductPage"),
        ShowAdministrationPageAlias("ShowAdministrationPage"),

        GetProductListPageCommandAlias("GetProductListPage"),
        AddProductCommandAlias("AddProduct"),
        ShowRegistrationPageAlias("ShowRegistrationPage"),
        ShowAuthenticationPageAlias("ShowAuthenticationPage"),
        AuthenticationAlias("Authenticate"),
        RegistrationAlias("Register"),
        LogoutAlias("Logout"),
        GetProductAlias("GetProduct");

        private String alias;

        CommandAliases(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }
    }
}
