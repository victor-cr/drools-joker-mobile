package ru.joker.drools;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.junit.Test;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Subscription;
import ru.joker.drools.model.Tariff;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 14:37
 */
public class SuperJokerTop1Test extends AbstractDroolsTest {
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal LOCAL = new BigDecimal("1.5").setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal OTHER = new BigDecimal("2.5").setScale(2, RoundingMode.HALF_UP);
    private static final int HOUR = DateTimeConstants.MILLIS_PER_HOUR;

    private final Customer callee = new Customer("007", Tariff.SMART, Subscription.TOP_ONE);
    private final Customer called = new Customer("008", Tariff.SIMPLE);
    private final Interval billingPeriod = new Interval(new DateTime(2013, 9, 1, 0, 0, 0, 0), new DateTime(2013, 10, 1, 0, 0, 0, 0));

    @Test
    public void testSMSWithinBounds_Single() {
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
    public void testCallWithinBounds_Multiple() {
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
    public void testLocalCallOutOfBounds() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = ZERO;
        BigDecimal actualResult = executeGetInvoice(callee,
                generateSMS("007", "008", instant, HOUR, 11),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testOthersCallOutOfBounds_OtherIsFree() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = new BigDecimal("16.50");
        BigDecimal actualResult = executeGetInvoice(callee,
                join(
                        generateSMS("007", "008", instant, HOUR, 10),
                        generateSMS("007", "008", instant, HOUR + 20, 10),
                        generateSMS("007", "any", instant.plusMillis(HOUR + 100), 0, 1)
                ),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testOthersCallOutOfBounds_OtherIsMax() {
        DateTime instant = new DateTime(2013, 9, 20, 4, 40, 11);

        BigDecimal expectedResult = new BigDecimal("15.00");
        BigDecimal actualResult = executeGetInvoice(callee,
                join(
                        generateSMS("007", "008", instant, HOUR, 10),
                        generateSMS("007", "008", instant, HOUR + 20, 10),
                        generateSMS("007", "any", instant.plusMillis(HOUR * 11 + 100), 0, 1)
                ),
                billingPeriod,
                callee,
                called
        ).getAmount();

        assertEquals(expectedResult, actualResult);
    }
}
