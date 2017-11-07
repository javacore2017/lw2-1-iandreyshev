package ru.iandreyshev.supermarketSimulator.product;

import java.math.BigDecimal;

public class SupermarketProduct extends Product {
    private Product product;
    private BigDecimal cost;
    private BigDecimal bonus;

    SupermarketProduct(Product product, BigDecimal cost, BigDecimal bonus) {
        super(product);
        this.product = product;
        this.cost = cost;
        this.bonus = bonus;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public Product getProduct() {
        return product;
    }
}
