package ru.joker.drools.marker;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import ru.joker.drools.model.BillingRecord;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class Refund {
    private final BillingRecord record;

    public Refund(BillingRecord record) {
        this.record = record;
    }

    public BillingRecord getRecord() {
        return record;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("record", record)
                .toString();
    }
}
