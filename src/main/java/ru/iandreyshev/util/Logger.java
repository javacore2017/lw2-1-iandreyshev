package ru.iandreyshev.util;

import ru.iandreyshev.supermarketSimulator.Product;

import java.util.Date;

public class Logger {
    public static void Message(Date date, String message) {
        System.out.printf("[%s] %s\n", date.toString(), message);
    }

    public static void Product(Product product, Float mass) {
        String name = product.getName();
        String cost = product.getCost().toString();
        System.out.printf("Product: [name: %s, cost: %s, mass: %s]\n", name, cost, mass);
    }

    public static void Product(Product product, Integer count) {
        String name = product.getName();
        String cost = product.getCost().toString();
        System.out.printf("Product: [name: %s, cost: %s, mass: %s]\n", name, cost, count);
    }
}
