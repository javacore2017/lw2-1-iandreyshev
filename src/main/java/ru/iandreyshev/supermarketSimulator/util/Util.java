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
    public static int multiplication(ProductType type, int value, Number amount) {
        switch (type) {
            case COUNT:
                return value * amount.intValue();
            case MASS:
                return (int)(value * amount.floatValue());
        }
        return 0;
    }
}
