package ru.iandreyshev.util;

import ru.iandreyshev.supermarketSimulator.Product;
import ru.iandreyshev.supermarketSimulator.ProductType;
import ru.iandreyshev.supermarketSimulator.SupermarketProduct;

import java.util.Date;

public class Logger {
    public static void Message(Date date, String message) {
        System.out.printf("[%s] %s\n", date.toString(), message);
    }

    public static void Product(SupermarketProduct product, Number amount) {
        String name = product.getName();
        String cost = product.getCost().toString();
        String amountName = typeValueStr(product.getType());
        String adultsOnly = product.isAdultOnly().toString();

        System.out.printf("Product: [name: %s, cost: %s, " +
                amountName + ": %s, adults only: %s]\n", name, cost, amount, adultsOnly);
    }

    public static void TakeProduct(Date date, String name, Product product, Number amount) {

        String message = "Customer " + name;
        message += " picked up " + amount.toString();
        message += " " + typeValueStr(product.getType());
        message += " of " + product.getName();
        Message(date, message);
    }

    public static void CustomerArrived(Date date, String name) {
        String message = "New customer '" + name + "' arrived";
        Message(date, message);
    }

    private static final String COUNT_AMOUNT_NAME = "units";
    private static final String MASS_AMOUNT_NAME = "mass";

    private static String typeValueStr(ProductType type) {
        switch (type) {
            case MASS:
                return MASS_AMOUNT_NAME;
            case COUNT:
                return COUNT_AMOUNT_NAME;
        }
        return "";
    }
}
