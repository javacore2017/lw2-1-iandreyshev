package ru.iandreyshev.util;

import ru.iandreyshev.supermarketSimulator.Product;

import java.util.Date;

public class Logger {
    public static void Message(Date date, String message) {
        System.out.printf("[%s] %s\n", date.toString(), message);
    }

    public static void Product(Product product, Number amount) {
        String name = product.getName();
        String cost = product.getCost().toString();
        String amountName = "";
        String adultsOnly = product.isAdultOnly().toString();

        switch (product.getType()) {
            case MASS:
                amountName = MASS_AMOUNT_NAME;
                break;
            case COUNT:
                amountName = COUNT_AMOUNT_NAME;
                break;
        }

        System.out.printf("Product: [name: %s, cost: %s, " +
                amountName + ": %s, adults only: %s]\n", name, cost, amount, adultsOnly);
    }

    public static void CustomerArrived(Date date, String name) {
        String message = "New customer '" + name + "' arrived";
        Message(date, message);
    }

    private static final String COUNT_AMOUNT_NAME = "count";
    private static final String MASS_AMOUNT_NAME = "mass";
}
