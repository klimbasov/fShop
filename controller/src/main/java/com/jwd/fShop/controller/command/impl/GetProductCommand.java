package com.jwd.fShop.controller.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.ServiceFactory;
import com.jwd.fShop.service.domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

public class GetProductCommand extends AbstractCommand implements Command {

    private final ProductService productService = ServiceFactory.getInstance().getProductService();

    public GetProductCommand() {
        super(Role.UNREGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        int productId = Integer.parseInt(req.getParameter("product_id"));
        String jsonProductList;
        PrintWriter out;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Optional<Product> productOptional = productService.getProduct(productId);
            if (productOptional.isPresent()) {
                jsonProductList = objectMapper.writeValueAsString(productOptional.get());
                out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(jsonProductList);
                out.flush();
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception exception) {
            throw new CommandException("In " + this.getClass().getName() + " while getting product", exception);
        }
    }
}
