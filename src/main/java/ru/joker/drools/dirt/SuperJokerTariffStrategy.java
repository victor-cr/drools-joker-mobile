package ru.joker.drools.dirt;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Sms;
import ru.joker.drools.model.SmsBillingRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 30.09.13 0:28
 */
public class SuperJokerTariffStrategy implements TariffStrategy {
    private CalculatorDao dao;

    public SuperJokerTariffStrategy(CalculatorDao dao) {
        this.dao = dao;
    }

    public Collection<SmsBillingRecord> calculate(Customer customer, Interval interval) {
        Collection<Sms> messages = dao.findBySender(customer.getNumber(), interval);
        Collection<SmsBillingRecord> records = new ArrayList<SmsBillingRecord>();
        Map<DateTime, List<Sms>> dailyMap = new HashMap<DateTime, List<Sms>>();
        for (Sms sms : messages) {
            DateTime date = sms.getSent().withMillisOfDay(0);
            List<Sms> collection = dailyMap.get(date);
            if (collection == null) {
                collection = new ArrayList<Sms>();
                dailyMap.put(date, collection);
            }
            collection.add(sms);
        }
        for (List<Sms> value : dailyMap.values()) {
            Collections.sort(value, SMSComparator.INSTANCE);
            for (int i = 0; i < value.size(); i++) {
                Sms sms = value.get(i);
                if (i < 10) {
                    records.add(new SmsBillingRecord(customer, BigDecimal.ZERO, sms));
                } else if (dao.findByNumber(sms.getReceiver()) != null) {
                    records.add(new SmsBillingRecord(customer, new BigDecimal("1.5"), sms));
                } else {
                    records.add(new SmsBillingRecord(customer, new BigDecimal("2.5"), sms));
                }
            }
        }
        return records;
    }

    private static final class SMSComparator implements Comparator<Sms> {
        private static final Comparator<Sms> INSTANCE = new SMSComparator();

        public int compare(Sms o1, Sms o2) {
            return o1.getSent().compareTo(o2.getSent());
        }
    }
}
