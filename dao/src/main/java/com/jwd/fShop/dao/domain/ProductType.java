package com.jwd.fShop.dao.domain;

import com.sun.istack.internal.NotNull;

public enum ProductType {
    ROD(ProductParams.rodParams),
    LINE(ProductParams.lineParams),
    HOOK(ProductParams.hookParams),
    ANY(ProductParams.anyParams);

    @NotNull
    final String[] paramsArray;

    ProductType(String[] paramsArray){
        this.paramsArray = paramsArray;
    }
}

class ProductParams{
    static final String[] rodParams = {"length", "material", "test"};
    static final String[] lineParams = {"length", "material", "diameter"};
    static final String[] hookParams = {"weight", "material"};
    static final String[] anyParams = {};
}
