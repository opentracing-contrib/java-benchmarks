package org.sample.billing.service.traced;

import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.sample.billing.model.Invoice;
import org.sample.billing.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tracedNotificationService")
public class TracedNotificationService extends NotificationServiceImpl {

    @Autowired
    public Tracer tracer;

    @Override
    public Boolean notifyCustomer(Invoice invoice) {
        try (Scope scope = tracer.buildSpan("notifyCustomer")
                .startActive(true)) {

            String recipientAddress = invoice.getCustomer().getEmail();
            String taxId = scope.span().getBaggageItem("taxId");
            scope.span().setTag("address", recipientAddress);
            scope.span().setTag("customer taxId", taxId);

            return super.notifyCustomer(invoice);
        }
    }
}
