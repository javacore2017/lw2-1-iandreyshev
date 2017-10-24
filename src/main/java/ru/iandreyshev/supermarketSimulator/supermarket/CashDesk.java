package ru.iandreyshev.supermarketSimulator.supermarket;

import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.CustomerType;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CashDesk {
    protected Bill buy(Basket basket, int money, PaymentType payType, CustomerType customerType, Date date) {
        if (basket.getSumCost() > money) {
            return null;
        }

        Bill result = new Bill(date, payType);

        for (Map.Entry<SupermarketProduct, Number> product : basket.entrySet()) {
            result.addProduct(product.getKey(), product.getValue());
        }

        return result;
    }
}
