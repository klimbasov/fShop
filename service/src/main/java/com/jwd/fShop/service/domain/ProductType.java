package com.jwd.fShop.service.domain;

import com.sun.istack.internal.NotNull;

public enum ProductType {
    ROD(new String[]{"length", "material", "test"}),
    LINE(new String[]{"length", "material", "diameter"}),
    HOOK(new String[]{"weight", "material"}),
    ANY(new String[]{});

    @NotNull
    final String[] paramsArray;

    ProductType(String[] paramsArray){
        this.paramsArray = paramsArray;
    }
}
