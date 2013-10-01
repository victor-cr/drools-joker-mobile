package ru.joker.drools.marker;

import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Sms;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class ChargeAsFree extends AbstractCharge {
    public ChargeAsFree(Customer customer, Sms sms) {
        super(customer, sms);
    }
}
