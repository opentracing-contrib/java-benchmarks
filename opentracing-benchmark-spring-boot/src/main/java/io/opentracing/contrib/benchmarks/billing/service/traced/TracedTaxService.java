package io.opentracing.contrib.benchmarks.billing.service.traced;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.benchmarks.billing.model.Invoice;
import io.opentracing.contrib.benchmarks.billing.service.impl.TaxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tracedTaxService")
public class TracedTaxService extends TaxServiceImpl {

    @Autowired
    public Tracer tracer;

    @Override
    public Invoice computeTaxes(Invoice invoice) {
        Span span = tracer.buildSpan("computeTaxes").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {

            Invoice computedInvoice =  super.computeTaxes(invoice);

            String taxId = span.getBaggageItem("taxId");

            span.setTag("currency", computedInvoice.getCurrency().toString());
            span.log("computeTaxes");
            span.setTag("total", computedInvoice.getAmountDue());
            span.setTag("customer taxId", taxId);

            return computedInvoice;
        }
    }
}
