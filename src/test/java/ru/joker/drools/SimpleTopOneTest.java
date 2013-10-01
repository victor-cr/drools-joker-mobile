package ru.joker.drools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.junit.Test;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Subscription;
import ru.joker.drools.model.Tariff;

import java.math.BigDecimal;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 14:37
 */
public class SimpleTopOneTest extends AbstractDroolsTest {
    private static final int HOUR = DateTimeConstants.MILLIS_PER_HOUR;

    private final Customer callee = new Customer("007", Tariff.SIMPLE, Subscription.TOP_ONE);
    private final Interval billingPeriod = new Interval(new DateTime(2013, 9, 1, 0, 0, 0, 0), new DateTime(2013, 10, 1, 0, 0, 0, 0));

    @Test
    public void testSingle() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = new BigDecimal("0.00");
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "any", instant, 0, 1),
                billingPeriod,
                callee
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMultiple() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = new BigDecimal("20.00");
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "any", instant, HOUR * 2, 11),
                billingPeriod,
                callee
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }
}
