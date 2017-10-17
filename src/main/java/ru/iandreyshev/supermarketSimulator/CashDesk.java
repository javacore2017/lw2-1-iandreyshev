package ru.iandreyshev.supermarketSimulator;

public class CashDesk {
    public Bill buy(Customer customer, PaymentType paymentType, int cash) {
        return null;
    }

    protected void setRetireDiscount(float retireDiscount) {
        m_retireDiscount = retireDiscount;
    }

    private float m_retireDiscount;
}
