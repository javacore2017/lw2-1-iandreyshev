package ru.iandreyshev.supermarketSimulator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ActionsTimeline extends TreeMap<Date, ArrayList<Pair<Customer, ActionType>>> {
    public void addAction(Date date, Customer customer, ActionType action) {
        ArrayList<Pair<Customer, ActionType>> list;
        list = getOrDefault(date, new ArrayList<>());
        list.add(new Pair<>(customer, action));
        put(date, list);
    }
}
