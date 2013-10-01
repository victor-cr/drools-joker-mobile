package ru.joker.drools.marker;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.Interval;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class Day {
    private final Interval interval;

    public Day(Interval interval) {
        this.interval = interval;
    }

    public Interval getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("interval", interval)
                .toString();
    }
}
