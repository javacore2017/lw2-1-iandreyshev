package ru.iandreyshev.supermarketSimulator.action;

import javafx.util.Pair;
import ru.iandreyshev.supermarketSimulator.customer.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class ActionsTimeline extends TreeMap<Date, ArrayList<Pair<Customer, ActionType>>> {
    public void addAction(Date date, Customer customer, ActionType action) {
        ArrayList<Pair<Customer, ActionType>> list;
        list = getOrDefault(date, new ArrayList<>());
        list.add(new Pair<>(customer, action));
        put(date, list);
    }
}
