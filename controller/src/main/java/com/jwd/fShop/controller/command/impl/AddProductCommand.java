package com.jwd.fShop.controller.command.impl;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.ServiceFactory;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductCommand extends AbstractCommand implements Command {

    private static final Role role = Role.ADMIN;
    private final ProductService productService = ServiceFactory.getInstance().getProductService();

    public AddProductCommand() {
        super(role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {

        validateRole(req, resp, HttpServletResponse.SC_FORBIDDEN, "in AddProductCommand, while validate session");

        String name = req.getParameter("name");
        String productTypeString = req.getParameter("productType");
        String quantityString = req.getParameter("quantity");
        String priceString = req.getParameter("price");

        validate(name, productTypeString, quantityString, priceString);
        try {
            Product product = new Product.
                    Builder().
                    setId(0).
                    setName(name).
                    setPrice(Float.parseFloat(priceString)).
                    setQuantity(Integer.parseInt(quantityString)).
                    setParams(null).
                    setProductType(ProductType.valueOf(productTypeString)).
                    build();

            productService.addProduct(product);
        } catch (Exception exception) {
            throw new CommandException("in AddProductCommand, while building product", exception);
        }
    }

    private void validate(String name, String productTypeString, String quantityString, String priceString) throws CommandException {
        validate(name);
        validateType(productTypeString);
        validateQuantity(quantityString);
        validatePrice(priceString);
    }

    private void validate(String name) throws CommandException {
        if (name.isEmpty()) {
            throw new CommandException("name=" + name);
        }
    }

    private void validateQuantity(String quantityString) {

    }

    private void validatePrice(String priceString) {

    }

    private void validateType(String productTypeString) {
    }
}
