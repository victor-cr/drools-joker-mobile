package ru.joker.drools;

import org.drools.ClassObjectFilter;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.joker.drools.model.Customer;
import ru.joker.drools.model.Invoice;
import ru.joker.drools.model.Sms;

import java.util.ArrayList;
import java.util.Collection;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 29.09.13 14:37
 */
public abstract class AbstractDroolsTest extends Assert {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final KnowledgeBase kBase;

    public AbstractDroolsTest() {
        KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf);

        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/logic/Smart.drl"), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/logic/Discount.drl"), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/logic/General.drl"), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/billing/Accounting.drl"), ResourceType.DRL);

        kBase = kBuilder.newKnowledgeBase();
    }

    protected <T> Collection<T> execute(Class<T> resultClass, Collection<?> objects, Object... facts) {
        StatefulKnowledgeSession session = kBase.newStatefulKnowledgeSession();

        try {
//            KnowledgeRuntimeLoggerFactory.newConsoleLogger(session);

            session.setGlobal("log", log);

            if (objects != null) {
                for (Object fact : objects) {
                    session.insert(fact);
                }
            }

            for (Object fact : facts) {
                session.insert(fact);
            }

            session.fireAllRules();

            return new ArrayList<T>((Collection<T>) session.getObjects(new ClassObjectFilter(resultClass)));
        } finally {
            session.dispose();
        }
    }

    protected Invoice executeGetInvoice(Customer customer, Collection<?> objects, Object... facts) {
        Collection<Invoice> invoices = execute(Invoice.class, objects, facts);

        for (Invoice invoice : invoices) {
            if (invoice.getCustomer() == customer) {
                return invoice;
            }
        }

        throw new AssertionError("There is no such invoice");
    }

    protected static Collection<Sms> generateSMS(String sender, String receiver, DateTime start, int millisInterval, int size) {
        Collection<Sms> smses = new ArrayList<Sms>(size);

        for (int i = 0; i < size; i++) {
            smses.add(new Sms(sender, receiver, start.plusMillis(millisInterval * i)));
        }

        return smses;
    }

    protected static <T> Collection<T> join(Collection<T> collection, Collection<T>... collections) {
        Collection<T> result = new ArrayList<T>(collection);

        for (Collection<T> c : collections) {
            result.addAll(c);
        }

        return result;
    }
}
