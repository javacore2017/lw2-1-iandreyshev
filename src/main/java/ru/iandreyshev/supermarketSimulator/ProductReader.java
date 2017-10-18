package ru.iandreyshev.supermarketSimulator;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;

public class ProductReader {
    public ProductReader() {}

    public ProductReader(String file) {
        open(file);
    }

    public void open(String file) {
        m_isOpen = false;
        try {
            m_fReader = new FileReader(file);
            m_bReader = new BufferedReader(m_fReader);
        } catch (FileNotFoundException e) {
            return;
        }
        m_isOpen = true;
    }

    public HashMap<SupermarketProduct, Number> readAllProducts() throws IOException {
        HashMap<SupermarketProduct, Number> result = new HashMap<>();
        String record;

        while ((record = m_bReader.readLine()) != null) {
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

    public boolean isOpen() {
        return m_isOpen;
    }

    private static final int PRODUCT_FIELDS_COUNT = 6;
    private static final String DELIMITER = ";";
    private static final char COMMENT_SYMBOL = '#';

    private boolean m_isOpen;
    private FileReader m_fReader;
    private BufferedReader m_bReader;

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
