package ru.iandreyshev.supermarketSimulator.product;

public class SupermarketProduct extends Product {
    public SupermarketProduct(Product product, int cost, int bonus) {
        super(product);
        this.product = product;
        this.cost = cost;
        this.bonus = bonus;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getBonus() {
        return bonus;
    }

    public Product getProduct() {
        return product;
    }

    private Product product;
    private int cost;
    private int bonus;
}
