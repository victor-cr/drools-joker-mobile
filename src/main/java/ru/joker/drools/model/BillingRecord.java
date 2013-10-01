package ru.joker.drools.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public abstract class BillingRecord {
    private final Customer customer;
    private final BigDecimal price;

    public BillingRecord(Customer customer, BigDecimal price) {
        this.customer = customer;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customer", customer)
                .append("price", price)
                .toString();
    }
}
