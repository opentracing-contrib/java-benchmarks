package io.opentracing.contrib.benchmarks.billing.service;

import io.opentracing.contrib.benchmarks.billing.model.Invoice;

public interface NotificationService {

    Boolean notifyCustomer(Invoice invoice);

}
