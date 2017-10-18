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
            createActionsTimeline();
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
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;
    private static final int MAX_CUSTOMERS_COUNT = 100;

    private static Supermarket m_supermarket;
    private static ActionsTimeline m_customersPlan;
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

    private static void createActionsTimeline() {
        m_customersPlan = new ActionsTimeline();
        int customersCount = Rand.getInt(MAX_CUSTOMERS_COUNT);

        for (Integer i = 0; i < customersCount; ++i) {
            String name = "Customer #" + (i + 1);
            Customer customer = new Customer(name);
            long timeFromStart = 0;

            while (timeFromStart < m_workTime) {
                int offset = Rand.getInt(m_workTime);
                boolean isTimeLeft = m_workTime - offset < timeFromStart;
                timeFromStart = (isTimeLeft) ? m_workTime : timeFromStart + offset;
                Date actionTime = new Date(timeFromStart + START_DATE.getTime());
                m_customersPlan.addAction(actionTime, customer, ActionType.ARRIVE);

                offset = Rand.getInt(m_workTime);
                isTimeLeft = m_workTime - offset < timeFromStart;
                timeFromStart = (isTimeLeft) ? m_workTime : timeFromStart + offset;
                actionTime = new Date(timeFromStart + START_DATE.getTime());
                m_customersPlan.addAction(actionTime, customer, ActionType.ARRIVE);
            }
        }
    }

    private static void enterSimulate() {
        if (m_supermarket.isEmpty()) {
            Logger.Message(START_DATE, "The supermarket was not open because it is empty");
            return;
        }

        Logger.Message(START_DATE, "Supermarket is opened");

        for (Map.Entry<Date, ArrayList<Pair<Customer, ActionType>>> plansAtTime : m_customersPlan.entrySet()) {
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

    private static void processPaid(Date date, Customer customer) {
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
        Logger.Bill(date, bill);
    }
}
