package ru.iandreyshev.supermarketSimulator;

import javafx.util.Pair;
import ru.iandreyshev.util.Logger;
import ru.iandreyshev.util.Rand;

import java.io.*;
import java.util.*;

class SupermarketSimulator {
    public static void main(String[] args) {
        try {
            m_workTime = getWorkTime(args);
            createActions();
            createSupermarket();
            enterSimulate();
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
    private static final int MAX_CUSTOMERS_COUNT = 50;
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    private static Supermarket m_supermarket;
    private static ActionsTimeline m_timeline;
    private static int m_workTime;

    private static int getWorkTime(String[] args) {
        if (args.length < ARGUMENTS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid arguments count.\nUse: SupermarketSimulator <work time in minutes>");
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
        m_supermarket = new Supermarket();
        ProductReader reader = new ProductReader(PRODUCTS_FILE);
        HashMap<SupermarketProduct, Number> products = reader.readAllProducts();
        for (Map.Entry<SupermarketProduct, Number> prodRecord : products.entrySet()) {
            m_supermarket.addProduct(prodRecord.getKey(), prodRecord.getValue());
        }

        Logger.Message(START_DATE, "Supermarket products have been formed:");

        if (m_supermarket.isEmpty()) {
            Logger.Message(START_DATE, "Supermarket is empty");
            return;
        }

        for (Map.Entry<SupermarketProduct, Number> product : m_supermarket.getProductsInfo()) {
            Logger.Product(product.getKey(), product.getValue());
        }
    }

    private static void createActions() {
        m_timeline = new ActionsTimeline();
        int customersCount = Rand.getInt(MAX_CUSTOMERS_COUNT);

        for (Integer i = 1; i <= customersCount; ++i) {
            String name = "Customer #" + i;
            Customer customer = new Customer(name);
            int actionsCount = Rand.getInt(MIN_ACTIONS_COUNT, MAX_ACTIONS_COUNT);
            int[] actionTimes = Rand.getInt(0, m_workTime, actionsCount);

            for (int j = 0; j < actionTimes.length; ++j) {
                Date time = new Date(actionTimes[j] + START_DATE.getTime());
                ActionType action = ActionType.TAKE_PRODUCT;
                if (j == 0) {
                    action = ActionType.ARRIVE;
                } else if (j == actionTimes.length - 1) {
                    action = ActionType.PAID;
                }
                m_timeline.addAction(time, customer, action);
            }
        }
    }

    private static void enterSimulate() {
        if (m_supermarket.isEmpty()) {
            Logger.Message(START_DATE, "The supermarket was not open because it is empty");
            return;
        }

        Logger.Message(START_DATE, "Supermarket is opened");

        for (Map.Entry<Date, ArrayList<Pair<Customer, ActionType>>> plansAtTime : m_timeline.entrySet()) {
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

                if (m_supermarket.isEmpty()) {
                    Logger.Message(currDate, "The supermarket closed because the products ended");
                    return;
                }
            }
        }

        Date closeDate = new Date(m_workTime + START_DATE.getTime());
        Logger.Message(closeDate, "The work day of the supermarket ended");
    }

    private static void processArrive(Date date, Customer customer) {
        Logger.CustomerArrived(date, customer.getName());
    }

    private static void processTake(Date date, Customer customer) {
        Set<SupermarketProduct> prods = m_supermarket.getProductsSet();
        Logger.TakeProduct(date, customer.getName(), prods.iterator().next(), 12);
    }

    private static void processPaid(Date date, Customer customer) {
        /*
        Set<SupermarketProduct> productsSet = m_supermarket.getProductsSet();
        Basket basket = customer.selectProducts(productsSet);
        Logger.Basket(date, basket);
        Bill bill = m_supermarket.buy(
                basket,
                customer.getType(),
                customer.getPayType(),
                customer.getMoney(),
                date
        );
        Logger.Bill(date, bill);*/
    }
}
