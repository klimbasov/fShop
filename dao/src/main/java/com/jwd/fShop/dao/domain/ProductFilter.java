package com.jwd.fShop.dao.domain;

public class ProductFilter {

    private static final String DEFAULT_NAME = "";
    private static final int DEFAULT_ID = -1;
    private static final float DEFAULT_LOW_PRICE = 0;
    private static final float DEFAULT_HIGH_PRICE = Float.MAX_VALUE;
    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.ANY;

    private final String name;
    private final boolean isName;
    private final int id;
    private final boolean isId;
    private final float lowPrice;
    private final boolean isLowPrice;
    private final float highPrice;
    private final boolean isHighPrice;
    private final ProductType productType;
    private final boolean isProductType;

    ProductFilter(final Builder builder){
        this.name = builder.name;
        this.id = builder.id;
        this.lowPrice = builder.lowPrice;
        this.highPrice = builder.highPrice;
        this.productType = builder.productType;
        this.isName = builder.isName;
        this.isId = builder.isId;
        this.isLowPrice = builder.isLowPrice;
        this.isHighPrice = builder.isHighPrice;
        this.isProductType = builder.isProductType;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    public boolean isName() {
        return isName;
    }

    public boolean isId() {
        return isId;
    }

    public boolean isLowPrice() {
        return isLowPrice;
    }

    public boolean isHighPrice() {
        return isHighPrice;
    }

    public boolean isProductType() {
        return isProductType;
    }

    public static class Builder{

        private String name;
        private boolean isName;
        private int id;
        private boolean isId;
        private float lowPrice;
        private boolean isLowPrice;
        private float highPrice;
        private boolean isHighPrice;
        private ProductType productType;
        private boolean isProductType;

        {
            name = DEFAULT_NAME;
            id = DEFAULT_ID;
            lowPrice = DEFAULT_LOW_PRICE;
            highPrice =DEFAULT_HIGH_PRICE;
            productType = DEFAULT_PRODUCT_TYPE;
            isName = false;
            isId = false;
            isLowPrice = false;
            isHighPrice = false;
            isProductType = false;
        }

        public Builder(){

        }

        public Builder setName(String name){
            this.name = name;
            this.isName = true;
            return this;
        }

        public Builder setId(int di){
            this.id = id;
            this.isId = true;
            return this;
        }

        public Builder setPriceRange(float lowPrice, float highPrice){
            this.lowPrice = lowPrice;
            this.isLowPrice = true;
            this.highPrice = highPrice;
            this.isHighPrice = true;
            return this;
        }

        public Builder setProductType(ProductType productType){
            this.productType = productType;
            this.isProductType = true;
            return this;
        }

        public ProductFilter build(){
            return new ProductFilter(this);
        }
    }
}
