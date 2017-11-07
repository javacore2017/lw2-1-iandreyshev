package ru.iandreyshev.supermarketSimulator.customer;

public enum CustomerActionType {
    NONE,
    ARRIVE,
    TAKE,
    PAY;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
