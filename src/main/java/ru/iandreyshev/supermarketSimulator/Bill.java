package ru.iandreyshev.supermarketSimulator;

public class Bill {
    public int getBonuses() {
        return m_bonuses;
    }

    public boolean isSuccess() {
        return m_isSuccess;
    }

    private int m_bonuses;
    private boolean m_isSuccess;
}
