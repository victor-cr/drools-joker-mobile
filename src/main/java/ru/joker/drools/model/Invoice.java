package ru.joker.drools.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 28.09.13 22:07
 */
public class Invoice {
    private final Customer customer;
    private BigDecimal amount;
    private Collection<BillingRecord> records = new ArrayList<BillingRecord>();

    public Invoice(Customer customer) {
        this(customer, BigDecimal.ZERO);
    }

    public Invoice(Customer customer, BigDecimal amount) {
        this.customer = customer;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void add(BigDecimal amount) {
        this.amount = this.amount.add(amount.setScale(2, RoundingMode.HALF_UP));
    }

    public Collection<BillingRecord> getRecords() {
        return Collections.unmodifiableCollection(records);
    }

    public void addRecord(BillingRecord record) {
        records.add(record);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("customer", customer)
                .append("amount", amount)
                .toString();
    }
}
