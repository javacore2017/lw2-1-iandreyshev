package ru.iandreyshev.supermarketSimulator;

public class SupermarketProduct extends Product {
    public SupermarketProduct(Product product, int cost, int bonus) {
        super(product);
        m_product = product;
        m_cost = cost;
        m_bonus = bonus;
    }

    public Integer getCost() {
        return m_cost;
    }

    public Integer getBonus() {
        return m_bonus;
    }

    public Integer getBonus(Number amount) {
        return (int) (m_bonus * amount.floatValue());
    }

    public Product getProduct() {
        return m_product;
    }

    private Product m_product;
    private int m_cost;
    private int m_bonus;
}
