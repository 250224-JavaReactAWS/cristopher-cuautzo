package com.revature.models;

public class Product {
    private int productId;
    private String name;
    private String description;
    private Double price;
    private int stock;

    public Product(String name, String description, Double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(){}

    public int getProductId() { return productId; }

    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Product {" +
                "productId = " + productId + "'" +
                ", name = '" + name + "'" +
                ", description = '" + description + "'" +
                ", price = " + price +
                ", stock = " + stock +
                "}";
    }
}
