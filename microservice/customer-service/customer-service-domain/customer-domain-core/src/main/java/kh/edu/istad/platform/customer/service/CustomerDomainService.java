package kh.edu.istad.platform.customer.service;

import kh.edu.istad.platform.customer.entity.Customer;
import kh.edu.istad.platform.customer.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.event.CustomerInitiatedEvent;
import kh.edu.istad.platform.customer.event.CustomerUpdatedEvent;

import java.time.ZonedDateTime;

public interface CustomerDomainService {

    CustomerInitiatedEvent initiateCustomer(Customer customer);

    CustomerUpdatedEvent updateCustomer(Customer customer, String familyName, String givenName);
    CustomerDeactivatedEvent deactivateCustomer();



}
