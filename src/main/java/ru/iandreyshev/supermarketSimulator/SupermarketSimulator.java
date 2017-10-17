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
            createSupermarket();
            createCustomersPlan();
            enterProcess();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(EXIT_FAILURE);
        }
        System.exit(EXIT_SUCCESS);
    }

    private static final int ARGUMENTS_COUNT = 1;
    private static final long MIN_WORK_TIME = 0;
    private static final Date START_DATE = new Date(1483218000000L);
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;
    private static final int MAX_CUSTOMERS_COUNT = 100;

    private static Supermarket m_supermarket;
    private static HashSet<Customer> m_customers;
    private static CustomerPlan m_customersPlan;
    private static int m_workTime;

    private static int getWorkTime(String[] args) {
        if (args.length < ARGUMENTS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid arguments count.\n" +
                            "Use: SupermarketSimulator <work time in minutes>");
        }

        int result;

        try {
            result = Integer.parseUnsignedInt(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Work time must be in the range from " + MIN_WORK_TIME +
                            " to " + Integer.MAX_VALUE);
        }

        return result;
    }

    private static void createSupermarket() throws IOException {
        m_supermarket = new Supermarket(START_DATE);
        ProductReader reader = new ProductReader(PRODUCTS_FILE);
        HashSet<SupermarketProduct> products = reader.readAllProducts();
        for (SupermarketProduct product : products) {
            m_supermarket.addProduct(product);
        }
        m_supermarket.startWork(m_workTime);
    }

    private static void createCustomersPlan() {
        m_customers = new HashSet<>();
        m_customersPlan = new CustomerPlan();
        int customersCount = Rand.getInt(MAX_CUSTOMERS_COUNT);

        for (Integer i = 0; i < customersCount; ++i) {
            String name = "Customer #" + i.toString();
            Customer customer = new Customer(name);
            m_customers.add(customer);
            int currentTime = 0;

            while (currentTime < m_workTime) {
                int timeOffset = Rand.getInt(m_workTime);
                boolean isTimeLeft = m_workTime - timeOffset < currentTime;
                currentTime = (isTimeLeft) ? m_workTime : currentTime + timeOffset;
                ArrayList<Pair<Customer, CustomerBehaviourType>> planAtTime =
                        m_customersPlan.getOrDefault(currentTime, new ArrayList<>());
                planAtTime.add(new Pair<>(customer, CustomerBehaviourType.ARRIVE));
                m_customersPlan.put(currentTime, planAtTime);

                timeOffset = Rand.getInt(m_workTime);
                isTimeLeft = m_workTime - timeOffset < currentTime;
                currentTime = (isTimeLeft) ? m_workTime : currentTime + timeOffset;
                planAtTime = m_customersPlan.getOrDefault(currentTime, new ArrayList<>());
                planAtTime.add(new Pair<>(customer, CustomerBehaviourType.PAID));
                m_customersPlan.put(currentTime, planAtTime);
            }
        }
    }

    private static void processCustomer(Date date, Customer customer, CustomerBehaviourType behaviour) {
        if (behaviour == CustomerBehaviourType.ARRIVE) {
            Logger.CustomerArrived(date, customer.getName());
        }
    }

    private static void enterProcess() throws IOException {
        for (Map.Entry<Integer, ArrayList<Pair<Customer, CustomerBehaviourType>>>
                plansList : m_customersPlan.entrySet()) {
            for (Pair<Customer, CustomerBehaviourType> plan : plansList.getValue()) {
                Date currentDate = m_supermarket.getStartDate();
                currentDate.setTime(plansList.getKey());
                processCustomer(currentDate, plan.getKey(), plan.getValue());
            }
        }
    }
}
