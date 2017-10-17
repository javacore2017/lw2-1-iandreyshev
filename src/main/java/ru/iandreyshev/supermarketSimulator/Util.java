package ru.iandreyshev.supermarketSimulator;

public class Util {
    public static int calcCost(Product product, Number amount) {
        switch (product.getType()) {
            case COUNT:
                return product.getCost() * amount.intValue();
            case MASS:
                return (int)(product.getCost() * amount.floatValue());
        }
        return 0;
    }
}
