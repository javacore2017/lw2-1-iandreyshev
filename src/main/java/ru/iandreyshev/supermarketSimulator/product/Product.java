package ru.iandreyshev.supermarketSimulator.product;

public class Product {
    public Product(ProductType type, String name, boolean isAdultOnly) {
        this.type = type;
        this.name = name;
        this.isAdultOnly = isAdultOnly;
    }

    public Product(Product instance) {
        type = instance.type;
        name = instance.name;
        isAdultOnly = instance.isAdultOnly;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Boolean isAdultOnly() {
        return isAdultOnly;
    }

    private ProductType type;
    private String name;
    private boolean isAdultOnly;
}
