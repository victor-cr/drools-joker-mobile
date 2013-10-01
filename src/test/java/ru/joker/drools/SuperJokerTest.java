package ru.joker.drools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.junit.Test;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Tariff;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 14:37
 */
public class SuperJokerTest extends AbstractDroolsTest {
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal LOCAL = new BigDecimal("1.5").setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal OTHER = new BigDecimal("2.5").setScale(2, RoundingMode.HALF_UP);
    private static final int HOUR = DateTimeConstants.MILLIS_PER_HOUR;

    private final Customer callee = new Customer("007", Tariff.SMART);
    private final Customer called = new Customer("008", Tariff.SIMPLE);
    private final Interval billingPeriod = new Interval(new DateTime(2013, 9, 1, 0, 0, 0, 0), new DateTime(2013, 10, 1, 0, 0, 0, 0));

    @Test
    public void testFreeSms_Single() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = ZERO;
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "any", instant, 0, 1),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFreeSms_Multiple() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = ZERO;
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "any", instant, HOUR * 2, 11),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLocal() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = LOCAL;
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "008", instant, HOUR, 11),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testOthers() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = OTHER;
        BigDecimal actualResult = executeGetInvoice(callee,
                join(
                        generateSMS("007", "008", instant, HOUR, 10),
                        generateSMS("007", "any", instant.plusMillis(HOUR * 10), 0, 1)
                ),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }
}
