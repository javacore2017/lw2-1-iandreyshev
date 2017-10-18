package ru.iandreyshev.supermarketSimulator;

import java.util.HashMap;

public class CashDesk {
    public Bill buy(Basket basket, PaymentType payType, int money, DiscountType[] discounts) {
        return null;
    }

    public void addProduct(SupermarketProduct product) {
        m_bonuses.put(product.getProduct(), product.getBonus());
        m_price.put(product.getProduct(), product.getCost());
    }

    public void addDiscount(DiscountType type, float value) {
        m_discounts.put(type, value);
    }

    private HashMap<DiscountType, Float> m_discounts = new HashMap<>();
    private HashMap<Product, Integer> m_bonuses = new HashMap<>();
    private HashMap<Product, Integer> m_price = new HashMap<>();;

    private int getCostWithDiscounts(DiscountType[] discount, int cost) {
        float discountValue = 0;
        for (DiscountType currDiscount : discount) {
            discountValue += m_discounts.getOrDefault(currDiscount, 0.f);
        }
        int discountInMoney = (int)(cost * discountValue);
        return (discountInMoney > cost) ? 0 : cost - discountInMoney;
    }
}
