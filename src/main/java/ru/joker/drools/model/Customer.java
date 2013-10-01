package ru.joker.drools.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 28.09.13 21:44
 */
public class Customer {
    private final String number;
    private final Tariff tariff;
    private final Collection<Subscription> subscriptions;

    public Customer(String number, Tariff tariff, Subscription... subscriptions) {
        this(number, tariff, Arrays.asList(subscriptions));
    }

    public Customer(String number, Tariff tariff, Collection<Subscription> subscriptions) {
        this.number = number;
        this.tariff = tariff;
        this.subscriptions = Collections.unmodifiableCollection(new ArrayList<Subscription>(subscriptions));
    }

    public String getNumber() {
        return number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("number", number)
                .toString();
    }
}
