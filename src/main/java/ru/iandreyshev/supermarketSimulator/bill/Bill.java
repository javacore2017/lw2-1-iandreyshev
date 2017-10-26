package ru.iandreyshev.supermarketSimulator.bill;

import ru.iandreyshev.supermarketSimulator.supermarket.DiscountType;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.util.Util;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Bill {
    public Bill(Date date, PaymentType payType) {
        this.date = date;
        this.payType = payType;
    }

    public void addProduct(SupermarketProduct product, Number amount) {
        int cost = Util.multiplicate(product.getType(), product.getCost(), amount);
        totalCost += cost;
        records.add(new BillRecord(product.getName(), cost, amount));
    }

    public void addDiscount(DiscountType type, Float value) {
        if (discounts.containsKey(type)) {
            return;
        } else if (value < MIN_DISCOUNT || MAX_DISCOUNT - value < totalDiscount) {
            return;
        }

        totalDiscount += value;
        discounts.put(type, value);
    }

    public Integer getCost() {
        return totalCost - ((int) (totalCost * totalDiscount / 100));
    }

    public Date getDate() {
        return date;
    }

    public PaymentType getPayType() {
        return payType;
    }

    public Set<BillRecord> getRecords() {
        return records;
    }

    private final float MIN_DISCOUNT = 0;
    private final float MAX_DISCOUNT = 100;

    private int totalCost;
    private float totalDiscount;
    private Date date;
    private PaymentType payType;
    private Set<BillRecord> records = new HashSet<>();
    private HashMap<DiscountType, Float> discounts = new HashMap<>();
}
