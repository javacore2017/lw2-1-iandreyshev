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
    public static final String EMPTY_AT_START =
            "The supermarket was not open because it is empty";
    public static final String OPEN =
            "Supermarket is opened";
    public static final String PRODUCTS_ENDED =
            "The supermarket closed because the products ended";
    public static final String END_OF_DAY =
            "The work day of the supermarket ended";
    public static final String PRODUCTS_FORMED =
            "Supermarket products have been formed:";
    public static final String DAY_RESULT_TITLE =
            "The supermarket sold products:";

    public static void message(Date date, String message) {
        System.out.printf("[%s] %s\n", date.toString(), message);
    }

    public static void product(SupermarketProduct product, Number amount) {
        String name = product.getName();
        String cost = product.getCost().toString();
        String amountName = toStr(product.getType());
        String adultsOnly = product.isAdultOnly().toString();

        System.out.printf("Product: [name: %s, cost: %s, " +
                amountName + ": %s, adults only: %s]\n", name, cost, amount, adultsOnly);
    }

    public static void supermarket(Supermarket market) {
        if (market.isEmpty()) {
            System.out.printf("[%s]", "Supermarket is empty");
            return;
        }

        Basket superBasket = market.getBasket();
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

    public static void atCashDesk(Date date, Customer customer, Basket basket) {
        String text = "Customer " + customer.getName() + " at the cash desk, ";
        text += "amount to pay: " + basket.getSumCost();
        message(date, text);
    }

    public static void afterPaid(Date date, Customer customer, Bill bill) {
        String text = customer.getName() + " paid ";
        text += bill.getCost() + " by " + toStr(bill.getPayType());
        message(date, text);
    }

    private static final String COUNT_AMOUNT_NAME = "units";
    private static final String MASS_AMOUNT_NAME = "mass";
    private static final String CASH_NAME = "cash";
    private static final String BONUS_NAME = "bonus";
    private static final String CARD_NAME = "card";

    private static String toStr(ProductType type) {
        switch (type) {
            case MASS:
                return MASS_AMOUNT_NAME;
            case COUNT:
                return COUNT_AMOUNT_NAME;
        }
        return "";
    }

    private static String toStr(PaymentType payType) {
        switch (payType) {
            case CARD:
                return CARD_NAME;
            case BONUSES:
                return BONUS_NAME;
            case CASH:
                return CASH_NAME;
        }
        return "";
    }
}
