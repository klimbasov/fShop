package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductCommand extends AbstractCommand implements Command {

    private static final Role role = Role.ADMIN;

    public AddProductCommand(ServicePack servicePack) {
        super(servicePack, role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {

        try {
            super.validateSessionRole((String) (req.getSession().getAttribute("role")));
        }catch (InvalidSessionException exception) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new CommandException("in AddProductCommand, while validate session", exception);
        }

        String name =req.getParameter("name");
        ProductType productType = ProductType.valueOf(req.getParameter("productType"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        float price = Float.parseFloat(req.getParameter("price"));

        try{
            Product product = new Product.
                    Builder().
                    setId(0).
                    setName(name).
                    setPrice(price).
                    setQuantity(quantity).
                    setParams(null).
                    setProductType(productType).
                    build();
            servicePack.getProductService().addProduct(product);
        }catch (Exception exception){
            throw new CommandException("in AddProductCommand, while building product", exception);
        }
    }
}
