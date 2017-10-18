package ru.iandreyshev.supermarketSimulator;

public class Util {
    public static int calcCost(ProductType type, int cost, Number amount) {
        switch (type) {
            case COUNT:
                return cost * amount.intValue();
            case MASS:
                return (int)(cost * amount.floatValue());
        }
        return 0;
    }
}
