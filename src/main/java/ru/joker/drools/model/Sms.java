package ru.joker.drools.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 28.09.13 21:44
 */
public class Sms {
    private final String sender;
    private final String receiver;
    private final DateTime sent;

    public Sms(String sender, String receiver, DateTime sent) {
        this.sender = sender;
        this.receiver = receiver;
        this.sent = sent;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public DateTime getSent() {
        return sent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sender", sender)
                .append("receiver", receiver)
                .append("sent", sent)
                .toString();
    }
}
