package ru.iandreyshev.supermarketSimulator;

public class SupermarketProduct {
    public SupermarketProduct(Product product, Number amount, Integer bonus) {
        m_product = product;
        m_amount = amount;
        m_bonus = bonus;
    }

    public Integer getBonus() {
        return m_bonus;
    }

    public Integer getBonusToAmount(Number amount) {
        return (int) (m_bonus * amount.floatValue());
    }

    public Number getAmount() {
        return m_amount;
    }

    public Product getProduct() {
        return m_product;
    }

    private Product m_product;
    private Number m_amount;
    private Integer m_bonus;
}
