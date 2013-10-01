package ru.joker.drools.model;

import java.math.BigDecimal;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class FixedBillingRecord extends BillingRecord {
    public FixedBillingRecord(Customer customer, BigDecimal price) {
        super(customer, price);
    }
}
