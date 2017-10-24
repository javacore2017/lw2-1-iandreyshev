package ru.iandreyshev.supermarketSimulator;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.action.ActionType;
import ru.iandreyshev.supermarketSimulator.action.ActionsTimeline;
import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.Customer;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.Product;
import ru.iandreyshev.supermarketSimulator.product.ProductReader;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.supermarket.Supermarket;
import ru.iandreyshev.supermarketSimulator.util.Logger;
import ru.iandreyshev.supermarketSimulator.util.Rand;

import java.io.*;
import java.util.*;

class SupermarketSimulator {
    public static void main(String[] args) {
        try {
            workTime = getWorkTime(args);
            createActions();
            createSupermarket();
            enterSimulate();
            writeReport();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(EXIT_FAILURE);
        }
        System.exit(EXIT_SUCCESS);
    }

    private static final int ARGUMENTS_COUNT = 1;
    private static final Date START_DATE = new Date(1483218000000L);
    private static final Long MAX_WORK_TIME = (long) Integer.MAX_VALUE;
    private static final int MIN_ACTIONS_COUNT = 2;
    private static final int MAX_ACTIONS_COUNT = 20;
    private static final int MIN_ACTION_PAUSE = 120;
    private static final int MAX_CUSTOMERS_COUNT = 50;
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final String CUSTOMER_NAME_PATTERN = "Customer #";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    private static Supermarket supermarket;
    private static ActionsTimeline timeline;
    private static int workTime;

    private static int getWorkTime(String[] args) {
        if (args.length < ARGUMENTS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid arguments count.\nUse: SupermarketSimulator <work time in seconds>");
        }

        int result;

        try {
            result = Integer.parseUnsignedInt(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Working time is set in minutes. It can be in the range from 0 to" + MAX_WORK_TIME);
        }

        return result;
    }

    private static void createSupermarket() throws IOException {
        supermarket = new Supermarket();
        ProductReader reader = new ProductReader(PRODUCTS_FILE);
        HashMap<SupermarketProduct, Number> products = reader.readAllProducts();
        for (Map.Entry<SupermarketProduct, Number> prodRecord : products.entrySet()) {
            supermarket.addProduct(prodRecord.getKey(), prodRecord.getValue());
        }

        Logger.message(START_DATE, Logger.PRODUCTS_FORMED);
        Logger.supermarket(supermarket);
    }

    private static void createActions() {
        timeline = new ActionsTimeline();
        int customersCount = Rand.getInt(MAX_CUSTOMERS_COUNT);

        for (Integer i = 1; i <= customersCount; ++i) {
            final String name = CUSTOMER_NAME_PATTERN + i;
            Customer customer = new Customer(name);
            final int actionsCount = Rand.getInt(MIN_ACTIONS_COUNT, MAX_ACTIONS_COUNT);
            List<Integer> acts = Rand.getIntList(MIN_ACTION_PAUSE, workTime, actionsCount);
            boolean isArrive = false;
            int actNumber = 1;

            for (Integer actTime : acts) {
                Date time = new Date(actTime + START_DATE.getTime());
                ActionType action = ActionType.TAKE_PRODUCT;
                if (!isArrive) {
                    action = ActionType.ARRIVE;
                    isArrive = true;
                } else if (actNumber == acts.size()) {
                    action = ActionType.PAID;
                }
                timeline.addAction(time, customer, action);
                ++actNumber;
            }
        }
    }

    private static void enterSimulate() {
        if (supermarket.isEmpty()) {
            Logger.message(START_DATE, Logger.EMPTY_AT_START);
            return;
        }
        Logger.message(START_DATE, Logger.OPEN);

        for (Map.Entry<Date, ArrayList<Pair<Customer, ActionType>>> plansAtTime : timeline.entrySet()) {
            for (Pair<Customer, ActionType> plan : plansAtTime.getValue()) {
                Date currDate = plansAtTime.getKey();
                Customer customer = plan.getKey();

                switch (plan.getValue()) {
                    case ARRIVE:
                        processArrive(currDate, customer);
                        break;
                    case PAID:
                        processPaid(currDate, customer);
                        break;
                    case TAKE_PRODUCT:
                        processTake(currDate, customer);
                        break;
                }

                if (supermarket.isEmpty()) {
                    Logger.message(currDate, Logger.PRODUCTS_ENDED);
                    return;
                }
            }
        }

        Date closeDate = new Date(workTime + START_DATE.getTime());
        Logger.message(closeDate, Logger.END_OF_DAY);
    }

    private static void processArrive(Date date, Customer customer) {
        Logger.customerArrived(date, customer.getName());
    }

    private static void processTake(Date date, Customer customer) {
        Basket superBasket = supermarket.getBasket();
        Pair<SupermarketProduct, Number> takingProducts;

        if ((takingProducts = customer.takeRandProduct(superBasket)) == null) {
            return;
        }

        String customerName = customer.getName();
        Product product = takingProducts.getKey();
        Number amount = takingProducts.getValue();

        Logger.takeProduct(date, customerName, product, amount);
    }

    private static void processPaid(Date date, Customer customer) {
        Basket basket = customer.getBasket();
        if (basket.getSize() == 0) {
            Logger.customerLeave(date, customer.getName());
            return;
        }

        Logger.atCashDesk(date, customer, basket);
        Bill bill = supermarket.buy(basket, customer, date);

        Logger.afterPaid(date, customer, bill);
        Logger.customerLeave(date, customer.getName());
    }

    private static void writeReport() {

    }
}
