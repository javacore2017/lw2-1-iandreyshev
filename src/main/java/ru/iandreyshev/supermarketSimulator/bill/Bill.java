package ru.iandreyshev.supermarketSimulator.bill;

import ru.iandreyshev.supermarketSimulator.supermarket.DiscountType;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.util.Util;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Bill {
    private final float MIN_DISCOUNT = 0;
    private final float MAX_DISCOUNT = 100;

    private BigDecimal totalCost = new BigDecimal(0);
    private float totalDiscount;
    private Date date;
    private PaymentType payType;
    private Set<BillRecord> records = new HashSet<>();
    private HashMap<DiscountType, Float> discounts = new HashMap<>();

    public Bill(Date date, PaymentType payType) {
        this.date = date;
        this.payType = payType;
    }

    public void addProduct(SupermarketProduct product, Number amount) {
        BigDecimal cost = Util.multiplicate(product.getType(), product.getCost(), amount);
        totalCost = totalCost.add(cost);
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

    public BigDecimal getCost() {
        BigDecimal costPersents = totalCost.multiply(new BigDecimal(totalDiscount));
        return totalCost.subtract(costPersents.divide(new BigDecimal(100)));
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
}
