package com.example.productmicroservice.Dto;

import java.math.BigDecimal;

public class CreateProductDto {

    private String tittle;
    private BigDecimal price;
    private Integer quantity;

    public CreateProductDto() {
    }

    public CreateProductDto(String title, BigDecimal price, Integer quantity) {
        this.tittle = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
