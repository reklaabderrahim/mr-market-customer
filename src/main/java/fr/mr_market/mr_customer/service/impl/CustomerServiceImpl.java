package fr.mr_market.mr_customer.service.impl;

import fr.mr_market.mr_customer.exception.CustomerNotFoundException;
import fr.mr_market.mr_customer.model.Customer;
import fr.mr_market.mr_customer.model.CustomerSearchCriteria;
import fr.mr_market.mr_customer.model.CustomerSpecs;
import fr.mr_market.mr_customer.repository.CustomerRepository;
import fr.mr_market.mr_customer.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final String CUSTOMER_WITH_UUID_CANNOT_BE_FOUND = "Customer with uuid: {} cannot be found";
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UUID createCustomer(Customer customer) {
        Customer customerModel = Customer.create(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getGender(),
                customer.getBirthDate(),
                customer.getMail());
        return customerRepository.saveAndFlush(customerModel).getUuid();
    }

    @Override
    public UUID updateCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer).getUuid();
    }

    @Override
    public List<Customer> retrieveAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> searchCustomers(CustomerSearchCriteria customerSearchCriteria, Pageable pageRequest, String sort) {
        long count = customerRepository.count(CustomerSpecs.searchCustomersSpecs(customerSearchCriteria, sort));

        if (pageRequest.getPageSize() == 0 && (int) count > 1) {
            pageRequest = PageRequest.of(0, (int) count);
        }
        return customerRepository.findAll(CustomerSpecs.searchCustomersSpecs(customerSearchCriteria, sort), pageRequest);
    }

    @Override
    public Customer findCustomerByUUID(UUID uuid) {
        return customerRepository.findCustomerByUuid(uuid).orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_WITH_UUID_CANNOT_BE_FOUND, uuid.toString()));
    }

    @Override
    @Transactional
    public UUID deleteCustomer(UUID uuid) {
        if (!customerRepository.existsByUuid(uuid)) {
            throw new CustomerNotFoundException(CUSTOMER_WITH_UUID_CANNOT_BE_FOUND, uuid.toString());
        }
        customerRepository.deleteByUuid(uuid);
        return uuid;
    }
}
