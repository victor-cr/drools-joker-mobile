package ru.joker.drools.marker;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Sms;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class AbstractCharge {
    private final Customer customer;
    private final Sms sms;

    public AbstractCharge(Customer customer, Sms sms) {
        this.customer = customer;
        this.sms = sms;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Sms getSms() {
        return sms;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customer", customer)
                .append("sms", sms)
                .toString();
    }
}
