package ru.iandreyshev.supermarketSimulator.util;

import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.Customer;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.Product;
import ru.iandreyshev.supermarketSimulator.product.ProductType;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.supermarket.Supermarket;

import java.util.Date;
import java.util.Map;

public class Logger {
    private static final String COUNT_AMOUNT_NAME = "units";
    private static final String MASS_AMOUNT_NAME = "mass";
    private static final String OPEN = "Supermarket is opened";
    private static final String CLOSED = "The supermarket closed";
    private static final String PRODUCTS_FORMED = "Supermarket products have been formed:";
    private static final String DAY_RESULT_TITLE = "The supermarket sold products:";

    public static void product(SupermarketProduct product, Number amount) {
        String name = product.getName();
        String cost = product.getCost().toString();
        String amountName = toStr(product.getType());
        String adultsOnly = product.isAdultOnly().toString();

        System.out.printf("Product: [name: %s, cost: %s, " +
                amountName + ": %s, adults only: %s]\n", name, cost, amount, adultsOnly);
    }

    public static void supermarketProducts(Basket superBasket) {
        if (superBasket.getSize() == 0) {
            System.out.printf("[%s]\n", "Supermarket is empty");
            return;
        }

        for (Map.Entry<SupermarketProduct, Number> product : superBasket.entrySet()) {
            Logger.product(product.getKey(), product.getValue());
        }
    }

    public static void takeProduct(Date date, String name, Product product, Number amount) {

        String text = "Customer " + name;
        text += " picked up " + amount.toString();
        text += " " + toStr(product.getType());
        text += " of " + product.getName();
        message(date, text);
    }

    public static void customerArrived(Date date, String name) {
        String text = "New customer '" + name + "' arrived";
        message(date, text);
    }

    public static void customerLeave(Date date, String name) {
        String text = "Customer '" + name + "' leave from supermarket";
        message(date, text);
    }

    public static void atCashDesk(Date date, Customer customer) {
        String text = "Customer " + customer.getName() + " at the cash desk, ";
        text += "amount to pay: " + customer.getBasket().getSumCost();
        message(date, text);
    }

    public static void afterPaid(Date date, Customer customer, Bill bill) {
        String text = customer.getName() + " paid ";
        text += bill.getCost() + " by " + bill.getPayType().toString();
        message(date, text);
    }

    public static void open(Date date) {
        message(date, OPEN);
    }

    public static void closed(Date date) {
        message(date, CLOSED);
    }

    public static void productsFormed(Date date) {
        message(date, PRODUCTS_FORMED);
    }

    public static void resultTitle(Date date) {
        message(date, DAY_RESULT_TITLE);
    }

    private static String toStr(ProductType type) {
        switch (type) {
            case MASS:
                return MASS_AMOUNT_NAME;
            case COUNT:
                return COUNT_AMOUNT_NAME;
        }
        return "";
    }

    private static void message(Date date, String message) {
        System.out.printf("[%s] %s\n", date.toString(), message);
    }
}
