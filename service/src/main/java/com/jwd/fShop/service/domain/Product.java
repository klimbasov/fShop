package com.jwd.fShop.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwd.fShop.service.domain.ProductType;

import java.util.Objects;

public class Product {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("id")
    private final int id;
    @JsonProperty("quantity")
    private final int quantity;
    @JsonProperty("price")
    private final float price;
    @JsonProperty("productType")
    private final ProductType productType;
    @JsonProperty("params")
    private final String params;

    Product(String name, int id, int quantity, float price, ProductType productType, final String params){
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productType = productType;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getParameters() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, id, quantity, price, productType);
        result = 31 * result + params.hashCode();
        return result;
    }

    public static class Builder{
        private String name;
        private int id;
        private int quantity;
        private float price;
        private ProductType productType;
        private String params;

        public Builder(){

        }

        public Builder(Product product){
            this.name = product.name;
            this.id = product.id;
            this.quantity = product.quantity;
            this.price = product.price;
            this.productType = product.productType;
            this.params = product.params;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setQuantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public Builder setPrice(float price){
            this.price = price;
            return this;
        }

        public Builder setProductType(ProductType productType){
            this.productType = productType;
            return this;
        }

        public Builder setParams(String params) throws NullPointerException, IndexOutOfBoundsException{
            this.params = "";
            if(Objects.nonNull(params)){
                this.params = params;
            }
            return this;
        }

        public Product build(){
            return new Product(name,
                    id,
                    quantity,
                    price,
                    productType,
                    params);
        }
    }
}
