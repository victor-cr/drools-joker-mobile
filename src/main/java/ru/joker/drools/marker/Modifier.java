package ru.joker.drools.marker;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import ru.joker.drools.model.Sms;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 0:53
 */
public class Modifier {
    private final Sms sms;
    private final BigDecimal modifier;

    public Modifier(Sms sms, BigDecimal modifier) {
        this.sms = sms;
        this.modifier = modifier.setScale(2, RoundingMode.HALF_UP);
    }

    public Sms getSms() {
        return sms;
    }

    public BigDecimal getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sms", sms)
                .append("modifier", modifier)
                .toString();
    }
}
