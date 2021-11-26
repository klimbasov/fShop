package com.jwd.fShop.controller.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.exception.ServiceException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Objects;

public class GetProductCommand extends AbstractCommand implements Command{
    public GetProductCommand(ServicePack servicePack) {
        super(servicePack, Role.UNREGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        int productId = Integer.parseInt(req.getParameter("product_id"));
        Product product = null;
        String jsonProductList;
        PrintWriter out;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            product = this.servicePack.getProductService().getProduct(productId);
            if(Objects.nonNull(product)){
                jsonProductList = objectMapper.writeValueAsString(product);
                out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(jsonProductList);
                out.flush();
            }else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception exception) {
            throw new CommandException("In " + this.getClass().getName() + " while getting product", exception);
        }
    }
}
