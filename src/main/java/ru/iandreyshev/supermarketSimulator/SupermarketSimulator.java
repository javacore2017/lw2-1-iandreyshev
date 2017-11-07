package ru.iandreyshev.supermarketSimulator;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.Customer;
import ru.iandreyshev.supermarketSimulator.customer.CustomerActionType;
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
    private static final Date START_DATE = new Date(1483218000000L);
    private static final int MIN_TIME_STEP = 1000 * 60; // ms * sec
    private static final int MAX_TIME_STEP = 1000 * 60 * 5; // ms * sec * min
    private static final int WORK_TIME = 1000 * 60 * 60 * 8; // ms * sec * min * hours
    private static final int CUSTOMERS_COUNT = 15;
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final String CUSTOMER_NAME_PATTERN = "Customer #";

    private static Supermarket supermarket;
    private static List<Customer> customers;

    public static void main(String[] args) {
        try {
            createCustomers();
            createSupermarket();
            enterSimulate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createCustomers() {
        customers = new ArrayList<>();

        for (int i = 0; i < CUSTOMERS_COUNT; ++i) {
            String name = CUSTOMER_NAME_PATTERN + (i + 1);
            customers.add(new Customer(name));
        }
    }

    private static void createSupermarket() throws IOException {
        supermarket = new Supermarket();
        ProductReader reader = new ProductReader(PRODUCTS_FILE);
        HashMap<SupermarketProduct, Number> products = reader.readAllProducts();

        for (Map.Entry<SupermarketProduct, Number> prodRecord : products.entrySet()) {
            supermarket.addProduct(prodRecord.getKey(), prodRecord.getValue());
        }
        Logger.productsFormed(START_DATE);
        Logger.supermarketProducts(supermarket.getStartProducts());
    }

    private static CustomerActionType createAction(Customer customer) {
        CustomerActionType result = CustomerActionType.NONE;
        if (supermarket.isEmpty()) {
            if (customer.isArrived()) {
                result = CustomerActionType.PAY;
            }
        } else {
            result = CustomerActionType.ARRIVE;
            if (customer.isArrived()) {
                int rand = Rand.randInt(1);
                result = (rand == 0) ? CustomerActionType.TAKE : CustomerActionType.PAY;
            }
        }
        return result;
    }

    private static void enterSimulate() {
        Date currDate = new Date(START_DATE.getTime());
        Date closeDate = new Date(WORK_TIME + currDate.getTime());
        Logger.open(currDate);

        while (currDate.getTime() < closeDate.getTime()) {
            Customer customer = customers.get(Rand.randInt(customers.size() - 1));
            CustomerActionType action = createAction(customer);
            switch (action) {
                case ARRIVE:
                    processArrive(currDate, customer);
                    break;
                case TAKE:
                    processTake(currDate, customer);
                    break;
                case PAY:
                    processPaid(currDate, customer);
                    break;
            }
            int timeStep = Rand.randInt(MIN_TIME_STEP, MAX_TIME_STEP);
            currDate = new Date(currDate.getTime() + timeStep);
        }
        checkCustomersAfterClose(closeDate);
        Logger.closed(closeDate);
        Logger.resultTitle(closeDate);
        Logger.supermarketProducts(supermarket.getSoldProducts());
    }

    private static void checkCustomersAfterClose(Date closeDate) {
        for (Customer customer : customers) {
            if (customer.isArrived()) {
                processPaid(closeDate, customer);
            }
        }
    }

    private static void processArrive(Date date, Customer customer) {
        if (customer.isArrived()) {
            return;
        }
        customer.setArrived(true);
        Logger.customerArrived(date, customer.getName());
    }

    private static void processTake(Date date, Customer customer) {
        if (!customer.isArrived()) {
            return;
        }
        Basket superBasket = supermarket.getStartProducts();
        Pair<SupermarketProduct, Number> takingProduct;

        if ((takingProduct = customer.takeRandProduct(superBasket)) == null) {
            return;
        }
        String customerName = customer.getName();
        Product product = takingProduct.getKey();
        Number amount = takingProduct.getValue();
        Logger.takeProduct(date, customerName, product, amount);
    }

    private static void processPaid(Date date, Customer customer) {
        if (!customer.isArrived()) {
            return;
        }
        customer.setArrived(false);

        if (customer.getBasket().getSize() == 0) {
            Logger.customerLeave(date, customer.getName());
            return;
        }

        Bill bill = supermarket.buy(customer, date);

        if (bill != null) {
            Logger.atCashDesk(date, customer);
            Logger.afterPaid(date, customer, bill);
        }
        customer.resetBasket();
        Logger.customerLeave(date, customer.getName());
    }
}
