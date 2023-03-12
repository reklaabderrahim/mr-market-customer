package fr.mr_market.mr_customer.service;

import fr.mr_market.mr_customer.model.Customer;
import fr.mr_market.mr_customer.model.CustomerSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    UUID createCustomer(Customer customer);

    UUID updateCustomer(Customer customer);
    List<Customer> retrieveAllCustomers();

    Page<Customer> searchCustomers(CustomerSearchCriteria customerSearchCriteria, Pageable pageRequest, String sort);

    Customer findCustomerByUUID(UUID uuid);

    UUID deleteCustomer(UUID uuid);
}
