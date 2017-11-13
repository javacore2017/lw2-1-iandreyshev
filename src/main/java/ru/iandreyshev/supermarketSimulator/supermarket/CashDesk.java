package ru.iandreyshev.supermarketSimulator.supermarket;

import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.BuyerType;
import ru.iandreyshev.supermarketSimulator.customer.IBuyer;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;

import java.util.Date;
import java.util.Map;

class CashDesk {
    Bill createBill(IBuyer buyer, Date date) {
        Basket basket = buyer.getBasket();
        if (basket.getSize() == 0 || basket.getSumCost().compareTo(buyer.getMoney()) > 0) {
            return null;
        }

        Bill result = new Bill(date, buyer.getPayType());

        for (Map.Entry<SupermarketProduct, Number> product : basket.entrySet()) {
            result.addProduct(product.getKey(), product.getValue());
        }

        return result;
    }
}
