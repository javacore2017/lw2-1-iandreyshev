package ru.iandreyshev.supermarketSimulator.product;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;

public class ProductReader {
    public ProductReader(String file) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(file));
    }

    public HashMap<SupermarketProduct, Number> readAllProducts() throws IOException {
        HashMap<SupermarketProduct, Number> result = new HashMap<>();
        String record;

        while ((record = bufferedReader.readLine()) != null) {
            if (isCommentLine(record)) {
                continue;
            }

            String[] fields = record.split(DELIMITER);
            if (fields.length < PRODUCT_FIELDS_COUNT) {
                throw new InvalidPropertiesFormatException(
                        "Invalid product record format");
            }

            Pair<SupermarketProduct, Number> product = createProduct(fields);
            result.put(product.getKey(), product.getValue());
        }

        return result;
    }

    private static final int PRODUCT_FIELDS_COUNT = 6;
    private static final String DELIMITER = ";";
    private static final char COMMENT_SYMBOL = '#';

    private BufferedReader bufferedReader;

    private Pair<SupermarketProduct, Number> createProduct(String[] fields) {
        String name = fields[0];
        ProductType type = ProductType.valueOf(fields[1]);
        int cost = Integer.parseUnsignedInt(fields[2]);
        boolean isAdultOnly = Boolean.parseBoolean(fields[3]);
        Number amount = getProductAmount(type, fields[4]);
        Integer bonus = Integer.parseUnsignedInt(fields[5]);

        Product product = new Product(type, name, isAdultOnly);
        return new Pair<>(new SupermarketProduct(product, cost, bonus), amount);
    }

    private Number getProductAmount(ProductType type, String amountStr) {
        switch (type) {
            case COUNT:
                return Integer.parseUnsignedInt(amountStr);
            case MASS:
                return Float.parseFloat(amountStr);
        }
        return 0;
    }

    private boolean isCommentLine(String line) {
        for (int i = 0; i < line.length(); ++i) {
            char ch = line.charAt(i);
            if (ch == ' ') {
                continue;
            } else if (ch == COMMENT_SYMBOL) {
                return true;
            }
            return false;
        }
        return false;
    }
}
