package ru.iandreyshev.supermarketSimulator.bill;

public class BillRecord {
    public BillRecord(String productName, int totalCost, Number amount) {
        m_productName = productName;
        m_amount = amount;
        m_cost = totalCost;
    }

    public String getProductName() {
        return m_productName;
    }

    public Integer getTotalCost() {
        return m_cost;
    }

    public Number getAmount() {
        return m_amount;
    }

    private String m_productName;
    private int m_cost;
    private Number m_amount;
}
