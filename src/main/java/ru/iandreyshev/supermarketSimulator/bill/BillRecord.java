package ru.iandreyshev.supermarketSimulator.bill;

import java.math.BigDecimal;

public class BillRecord {
    private String productName;
    private BigDecimal cost = new BigDecimal(0);
    private Number amount;

    BillRecord(String productName, BigDecimal totalCost, Number amount) {
        this.productName = productName;
        this.amount = amount;
        cost = totalCost;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getTotalCost() {
        return cost;
    }

    public Number getAmount() {
        return amount;
    }
}
