package ru.joker.drools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Sms;
import ru.joker.drools.model.Tariff;

import java.util.ArrayList;
import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 15.04.12 23:29
 */
public class GeneralDao {
    private Collection<Customer> customers;
    private Collection<Sms> smses;

    public GeneralDao() {
        customers = new ArrayList<Customer>();
        smses = new ArrayList<Sms>();

        DateTime startOfMonth = new DateTime().withDayOfMonth(1).withMillisOfDay(0);

        for (int i = 0; i < 2; i++) {
            DateTime begin = startOfMonth.plusMillis(generateMillis(DateTimeConstants.MINUTES_PER_WEEK));
            Customer customer = new Customer(generateNumber(), Tariff.SMART);

            customers.add(customer);

            for (int j = 1; j < 24*30; j++) {
                smses.add(new Sms(customer.getNumber(), generateNumber(), begin));

                begin = begin.plusMillis(generateMillis(DateTimeConstants.MINUTES_PER_HOUR));
            }
        }
    }

    public Collection<Customer> findCustomers() {
        return customers;
    }

    public Collection<Sms> findSmses() {
        return smses;
    }

    private String generateNumber() {
        return String.valueOf((long) Math.abs(100 * Math.random()));
    }

    private int generateMillis(int minutes) {
        return (int) Math.abs(Math.random() * minutes * DateTimeConstants.MILLIS_PER_MINUTE);
    }
}
