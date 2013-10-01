package ru.joker.drools.dirt;

import org.joda.time.Interval;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.SmsBillingRecord;

import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 30.09.13 0:20
 */
public interface TariffStrategy {
    Collection<SmsBillingRecord> calculate(Customer customer, Interval interval);
}
