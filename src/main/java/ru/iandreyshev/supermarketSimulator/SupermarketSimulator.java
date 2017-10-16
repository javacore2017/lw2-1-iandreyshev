package ru.iandreyshev.supermarketSimulator;

import java.io.*;
import java.util.*;

class SupermarketSimulator {
    public static void main(String[] args) {
        try {
            long workTime = getWorkTimeInMinutesFromArgs(args);
            m_supermarket = new Supermarket(START_DATE);
            addProducts();
            m_supermarket.startWork(workTime);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(EXIT_FAILURE);
        }
        System.exit(EXIT_SUCCESS);
    }

    private static final int ARGUMENTS_COUNT = 1;
    private static final int PRODUCT_FIELDS_COUNT = 4;
    private static final long MIN_WORK_TIME = 0;
    private static final Date START_DATE = new Date(1483218000000L);
    private static final String PRODUCTS_FILE = "./resources/products.csv";
    private static final String DELIMITER = ";";
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    private static Supermarket m_supermarket;

    private static long getWorkTimeInMinutesFromArgs(String[] args) {
        if (args.length < ARGUMENTS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid arguments count.\n" +
                            "Use: SupermarketSimulator <work time in minutes>");
        }

        long result;

        try {
            result = Long.parseUnsignedLong(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Work time must be in the range" +
                            "from " + MIN_WORK_TIME +
                            " to " + Integer.MAX_VALUE);
        }

        return result;
    }

    private static void addProducts() throws FileNotFoundException, IOException {
        FileReader fReader = new FileReader(PRODUCTS_FILE);
        BufferedReader bReader = new BufferedReader(fReader);
        String record;

        while ((record = bReader.readLine()) != null) {
            String[] productFields = record.split(DELIMITER);
            if (productFields.length < PRODUCT_FIELDS_COUNT) {
                throw new InvalidPropertiesFormatException("Invalid record");
            }

            Product product = createProduct(productFields);

            if (product.getType() == ProductType.Count) {
                Integer count = getProductCount(productFields[3]);
                m_supermarket.addProduct(product, count);
            } else if (product.getType() == ProductType.Mass) {
                Float mass = getProductMass(productFields[3]);
                m_supermarket.addProduct(product, mass);
            }
        }
    }

    private static Product createProduct(String[] fields) {
        String name = fields[0];
        ProductType type = ProductType.valueOf(fields[1]);
        int cost = Integer.parseUnsignedInt(fields[2]);
        return new Product(type, name, cost);
    }

    private static Float getProductMass(String massStr) {
        return Float.parseFloat(massStr);
    }

    private static Integer getProductCount(String countStr) {
        return Integer.parseUnsignedInt(countStr);
    }
}
