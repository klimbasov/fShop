package com.jwd.fShop.controller.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.ServiceFactory;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductFilter;
import com.jwd.fShop.service.domain.ProductType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Objects;

public class GetProductListPageCommand extends AbstractCommand implements Command {

    private static final Role ROLE = Role.UNREGISTERED_USER;
    private ProductService productService = ServiceFactory.getInstance().getProductService();;

    public GetProductListPageCommand() {
        super(Role.UNREGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        LinkedList<Product> products;
        int pageQuantity;
        String parameterNum = req.getParameter("pageNum");
        int pageNumber = parameterNum.isEmpty() ? 1 : Integer.parseInt(parameterNum);
        String jsonProductList;
        PrintWriter out;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ProductFilter productFilter = createProductFilter(req.getParameter("subName"),
                    req.getParameter("productType"),
                    req.getParameter("lowPrice"),
                    req.getParameter("highPrice"));
            products = productService.getProductsPage(productFilter, pageNumber);
            pageQuantity = productService.getPageQuantity(productFilter);
            if(products.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
            else {
                jsonProductList = objectMapper.writeValueAsString(products);
                out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(jsonProductList);
                out.flush();
                req.setAttribute("pageNum", pageNumber);
                req.setAttribute("pageQuantity", pageQuantity);
                System.out.println("[INFO] command: getProductList, list size: " + products.size());
            }
        }catch (Exception exception){
            throw new CommandException("in GetProductListPageCommand: in execute while get products from product service", exception);
        }

    }

    private ProductFilter createProductFilter(String subName, String productType, String lowPrice, String highPrice) throws IllegalArgumentException{
        ProductFilter.Builder builder = new ProductFilter.Builder();
        float lowPriceVal, highPriceVal;
        if(Objects.nonNull(subName)) {
            builder.setName(subName);
        }
        if(Objects.nonNull(productType))
            builder.setProductType(ProductType.valueOf(productType));
        if(Objects.nonNull(lowPrice))
            lowPriceVal = Float.parseFloat(lowPrice);
        else
            lowPriceVal = 0;
        if(Objects.nonNull(highPrice))
            highPriceVal = Float.parseFloat(highPrice);
        else
            highPriceVal = Float.MAX_VALUE;
        if(lowPriceVal != 0 || highPriceVal != Float.MAX_VALUE)
            builder.setPriceRange(lowPriceVal, highPriceVal);
        return builder.build();


    }
}
