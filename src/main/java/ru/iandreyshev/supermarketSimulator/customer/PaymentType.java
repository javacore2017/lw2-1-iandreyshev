package ru.iandreyshev.supermarketSimulator.customer;

public enum PaymentType {
    CARD,
    CASH,
    BONUS;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
