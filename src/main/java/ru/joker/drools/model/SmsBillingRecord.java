package ru.joker.drools.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class SmsBillingRecord extends BillingRecord {
    private final Sms sms;

    public SmsBillingRecord(Customer customer, BigDecimal price, Sms sms) {
        super(customer, price);

        this.sms = sms;
    }

    public Sms getSms() {
        return sms;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("sms", sms)
                .toString();
    }
}
