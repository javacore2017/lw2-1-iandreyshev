package ru.iandreyshev.supermarketSimulator.supermarket;

import ru.iandreyshev.supermarketSimulator.bill.Bill;
import ru.iandreyshev.supermarketSimulator.customer.BuyerType;
import ru.iandreyshev.supermarketSimulator.customer.IBuyer;
import ru.iandreyshev.supermarketSimulator.customer.PaymentType;
import ru.iandreyshev.supermarketSimulator.product.Basket;
import ru.iandreyshev.supermarketSimulator.product.Product;
import ru.iandreyshev.supermarketSimulator.product.ProductType;
import ru.iandreyshev.supermarketSimulator.product.SupermarketProduct;
import ru.iandreyshev.supermarketSimulator.util.Util;

import java.util.*;

public class Supermarket extends CashDesk {
    public void addProduct(SupermarketProduct product, Number amount) {
        products.add(product, amount);
    }

    public Bill buy(IBuyer buyer, Date date) {
        validateProducts(buyer);
        Bill result = createBill(buyer, date);
        HashMap<DiscountType, Float> discounts = calcDiscounts(buyer);

        for (Map.Entry<DiscountType, Float> entry : discounts.entrySet()) {
            result.addDiscount(entry.getKey(), entry.getValue());
        }
        int bonusForBuying = 0;
        Basket basket = buyer.getBasket();

        for (Map.Entry<SupermarketProduct, Number> buyingProduct : basket.entrySet()) {
            ProductType productType = buyingProduct.getKey().getType();
            Number amount = buyingProduct.getValue();
            int bonus = buyingProduct.getKey().getBonus();
            bonusForBuying += Util.multiplicate(productType, bonus, amount);
            soldBasket.add(buyingProduct.getKey(), amount);
        }
        if (buyer.getPayType() != PaymentType.BONUS) {
            buyer.addBonus(bonusForBuying);
        }
        return result;
    }

    public boolean isEmpty() {
        return products.getSize() == 0;
    }

    public Basket getStartProducts() {
        return products;
    }

    public Basket getSoldProducts() {
        return soldBasket;
    }

    private Float RETIRE_DISCOUNT_SIZE = 5.f;

    private Basket products = new Basket();
    private Basket soldBasket = new Basket();

    private HashMap<DiscountType, Float> calcDiscounts(IBuyer customer) {
        HashMap<DiscountType, Float> result = new HashMap<>();

        if (customer.getType() == BuyerType.RETIRED &&
            customer.getPayType() != PaymentType.BONUS) {
            result.put(DiscountType.RETIRE, RETIRE_DISCOUNT_SIZE);
        }
        return result;
    }

    private void validateProducts(IBuyer buyer) {
        Basket basket = buyer.getBasket();
        for (Map.Entry<SupermarketProduct, Number> prodInBasket : basket.entrySet()) {
            SupermarketProduct product = prodInBasket.getKey();
            Number amount = prodInBasket.getValue();

            if (buyer.getType() == BuyerType.CHILD && prodInBasket.getKey().isAdultOnly()) {
                basket.remove(product, amount);
                products.add(product, amount);
            }
        }
    }
}
