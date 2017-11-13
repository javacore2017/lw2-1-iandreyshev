package ru.iandreyshev.supermarketSimulator.util;

import ru.iandreyshev.supermarketSimulator.product.ProductType;

import java.math.BigDecimal;

public class Util {
    public static Number toAmount(ProductType type, Number amount) {
        switch (type) {
            case COUNT: return amount.intValue();
            case MASS: return amount.floatValue();
        }
        return amount;
    }
    public static BigDecimal multiplicate(ProductType type, BigDecimal value, Number amount) {
        BigDecimal result = new BigDecimal(0);
        switch (type) {
            case COUNT:
                result = value.multiply(new BigDecimal(amount.intValue()));
                break;
            case MASS:
                result = value.multiply(new BigDecimal(amount.floatValue()));
                break;
        }
        return result;
    }
}
