package io.opentracing.contrib.benchmarks.billing.service.traced;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.benchmarks.billing.model.Invoice;
import io.opentracing.contrib.benchmarks.billing.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tracedNotificationService")
public class TracedNotificationService extends NotificationServiceImpl {

    @Autowired
    public Tracer tracer;

    @Override
    public Boolean notifyCustomer(Invoice invoice) {
        Span span = tracer.buildSpan("notifyCustomer").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {

            String recipientAddress = invoice.getCustomer().getEmail();
            String taxId = span.getBaggageItem("taxId");
            span.setTag("address", recipientAddress);
            span.setTag("customer taxId", taxId);

            return super.notifyCustomer(invoice);
        }
    }
}
