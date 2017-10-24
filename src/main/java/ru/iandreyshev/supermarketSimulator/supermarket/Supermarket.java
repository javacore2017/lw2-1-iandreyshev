package ru.iandreyshev.supermarketSimulator.supermarket;

import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.Customer;
import ru.iandreyshev.supermarketSimulator.customer.CustomerType;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.ProductType;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.util.Util;

import java.util.*;

public class Supermarket extends CashDesk {
    public void addProduct(SupermarketProduct product, Number amount) {
        products.add(product, amount);
    }

    public Bill buy(Basket basket, Customer customer, Date date) {
        int money = customer.getMoney();
        PaymentType payType = customer.getPayType();
        CustomerType customerType = customer.getType();

        Bill result = buy(basket, money, payType, customerType, date);
        if (result == null) {
            return null;
        }

        HashMap<DiscountType, Float> discounts = calcDiscounts(customer);
        for (Map.Entry<DiscountType, Float> entry : discounts.entrySet()) {
            result.addDiscount(entry.getKey(), entry.getValue());
        }

        int bonusForBuying = 0;
        for (Map.Entry<SupermarketProduct, Number> buyingProduct : basket.entrySet()) {
            ProductType productType = buyingProduct.getKey().getType();
            Number amount = buyingProduct.getValue();
            int bonus = buyingProduct.getKey().getBonus();

            bonusForBuying += Util.multiplication(productType, bonus, amount);
            products.remove(buyingProduct.getKey(), amount);
            soldBasket.add(buyingProduct.getKey(), amount);
        }

        if (customer.getPayType() != PaymentType.BONUSES) {
            customer.addBonus(bonusForBuying);
        }

        return result;
    }

    public boolean isEmpty() {
        return products.getSize() == 0;
    }

    public Basket getBasket() {
        return products;
    }

    private Float RETIRE_DISCOUNT_SIZE = 5.f;

    private Basket products = new Basket();
    private Basket soldBasket = new Basket();

    private HashMap<DiscountType, Float> calcDiscounts(Customer customer) {
        HashMap<DiscountType, Float> result = new HashMap<>();
        if (customer.getType() == CustomerType.RETIRED &&
            customer.getPayType() != PaymentType.BONUSES) {
            result.put(DiscountType.RETIRE, RETIRE_DISCOUNT_SIZE);
        }
        return result;
    }
}
