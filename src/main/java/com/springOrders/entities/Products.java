package com.springOrders.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Products {
    @Id
    private String id;
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @NotNull
    @Length(max = 500)
    private String image;

    @Column(name = "count_in_stock")
    private int countInStock;

    public Products(String id, String title,
            double price, String image, int countInStock) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.countInStock = countInStock;
    }

    public void updateProduct(Products product) {
        if (product.title != null) {
            this.title = product.title;
        }
        if (product.price != 0) {
            this.price = product.price;
        }
        if (product.image != null) {
            this.image = product.image;
        }
        if (product.countInStock != 0) {
            this.countInStock = product.countInStock;
        }

    }

    @Override
    public String toString() {
        return "Products [id=" + id + ", title=" + title + ", price=" + price + ", image=" + image + ", countInStock="
                + countInStock + "]";
    }

}
