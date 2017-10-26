package ru.iandreyshev.supermarketSimulator.util;

import ru.iandreyshev.supermarketSimulator.product.ProductType;

public class Util {
    public static Number toAmount(ProductType type, Number amount) {
        switch (type) {
            case COUNT: return amount.intValue();
            case MASS: return amount.floatValue();
        }
        return amount;
    }
    public static int multiplicate(ProductType type, int value, Number amount) {
        int result = 0;
        switch (type) {
            case COUNT:
                result = value * amount.intValue();
                break;
            case MASS:
                result = (int)(value * amount.floatValue());
                break;
        }
        return result;
    }
}
