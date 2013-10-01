package ru.joker.drools.dirt;

import org.joda.time.Interval;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Sms;

import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 30.09.13 0:25
 */
public interface CalculatorDao {
    Customer findByNumber(String number);

    Collection<Sms> findBySender(String number, Interval interval);
}
