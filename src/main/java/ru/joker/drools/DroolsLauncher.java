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
import org.joda.time.Interval;
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
 * @since 15.04.12 22:33
 */
public class DroolsLauncher {
    private static final Logger LOG = LoggerFactory.getLogger(DroolsLauncher.class);
    private static final GeneralDao DAO = new GeneralDao();

    public static void main(String[] args) throws Exception {
        KnowledgeBuilderConfiguration conf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(conf);

        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/logic/Smart.drl"), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/billing/Accounting.drl"), ResourceType.DRL);
        kBuilder.add(ResourceFactory.newClassPathResource("ru/joker/drools/drl/logic/General.drl"), ResourceType.DRL);

        KnowledgeBase kBase = kBuilder.newKnowledgeBase();

        StatefulKnowledgeSession session = kBase.newStatefulKnowledgeSession();

        try {
            session.setGlobal("log", LOG);

            for (Customer customer : DAO.findCustomers()) {
                session.insert(customer);
            }

            for (Sms sms : DAO.findSmses()) {
                session.insert(sms);
            }

            DateTime startOfMonth = new DateTime().withDayOfMonth(1).withMillisOfDay(0);

            session.insert(new Interval(startOfMonth, startOfMonth.plusMonths(1)));

            session.fireAllRules();

            Collection<Object> invoices = session.getObjects(new ClassObjectFilter(Invoice.class));

            LOG.info("Invoices: {}", new ArrayList<Object>(invoices));
        } finally {
            session.dispose();
        }
    }
}
