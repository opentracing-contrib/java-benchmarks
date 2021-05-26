package io.opentracing.contrib.benchmarks.billing.service.traced;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.benchmarks.billing.model.Invoice;
import io.opentracing.contrib.benchmarks.billing.service.TaxService;
import io.opentracing.contrib.benchmarks.billing.service.impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tracedInvoiceService")
public class TracedInvoiceService extends InvoiceServiceImpl {

    @Autowired
    private TracedNotificationService notificationService;

    @Autowired
    private TracedTaxService taxService;

    @Autowired
    public Tracer tracer;

    @Override
    public Long createInvoice(Invoice invoice) {
        Span span = tracer.buildSpan("createInvoice").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            span.log("createInvoice");
            span.setTag("customer", invoice.getCustomer().getEmail());
            span.setBaggageItem("taxId",
                    invoice.getCustomer().getTaxId());
            return super.createInvoice(invoice);
        }
    }

    @Override
    public TracedNotificationService getNotificationService() {
        return notificationService;
    }

    @Override
    public TaxService getTaxesService() {
        return taxService;
    }
}
