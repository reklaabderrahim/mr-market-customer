package fr.mr_market.mr_customer.controller;

import com.github.dozermapper.core.Mapper;
import fr.mr_market.mr_customer.service.CustomerService;
import fr.mr_market.mr_market_customer.swagger.CustomersApi;
import fr.mr_market.mr_market_customer.swagger.model.customer.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@Slf4j
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;
    private final Mapper mapper;

    public CustomerController(CustomerService customerService, Mapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Override
    @Operation(operationId = "customerCreate")
    public ResponseEntity<CustomerCreate201Response> customerCreate(Customer customer) {
        log.info("controller:: create customer: {}", customer);
        fr.mr_market.mr_customer.model.Customer CustomerModel
                = mapper.map(customer, fr.mr_market.mr_customer.model.Customer.class);
        CustomerCreate201Response response = new CustomerCreate201Response();
        response.setCustomerIdentifier(customerService.createCustomer(CustomerModel));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Operation(operationId = "customerUpdate")
    public ResponseEntity<CustomerUpdate201Response> customerUpdate(Customer customer) {
        log.info("controller:: update customer: {}", customer);
        fr.mr_market.mr_customer.model.Customer CustomerModel
                = mapper.map(customer, fr.mr_market.mr_customer.model.Customer.class);
        CustomerUpdate201Response response = new CustomerUpdate201Response();
        response.setCustomerIdentifier(customerService.createCustomer(CustomerModel));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Override
    @Operation(operationId = "deleteUserByUuid")
    public ResponseEntity<DeleteUserByUuid200Response> deleteUserByUuid(String customerUuid) {
        log.info("controller:: delete customer by uuid: {}", customerUuid);
        DeleteUserByUuid200Response response = new DeleteUserByUuid200Response();
        response.setCustomerIdentifier(customerService.deleteCustomer(UUID.fromString(customerUuid)));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Override
    @Operation(operationId = "findUserByUuid")
    public ResponseEntity<Customer> findUserByUuid(String customerUuid) {
        log.info("controller:: find customer by uuid: {}", customerUuid);
        fr.mr_market.mr_customer.model.Customer customer =
                customerService.findCustomerByUUID(UUID.fromString(customerUuid));
        return new ResponseEntity<>(mapper.map(customer, Customer.class), HttpStatus.ACCEPTED);
    }

    @Override
    @Operation(operationId = "searchCustomers")
    public ResponseEntity<List<Customer>> findUsers() {
        log.info("controller:: find customers");
        List<Customer> customers =
                customerService.retrieveAllCustomers().stream()
                        .map(customer -> mapper.map(customer, Customer.class))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
    }

    @Override
    @Operation(operationId = "customerUpdate")
    public ResponseEntity<CustomerSearchResponse> searchCustomers(CustomerSearchCriteria customerSearchCriteria) {
        log.info("controller:: search customers with criteria: {}", customerSearchCriteria);
        fr.mr_market.mr_customer.model.CustomerSearchCriteria criteria =
                mapper.map(customerSearchCriteria, fr.mr_market.mr_customer.model.CustomerSearchCriteria.class);

        int page = 0;
        int pageSize = 1;
        String sort = customerSearchCriteria.getSort();
        if (customerSearchCriteria.getPagination() != null) {
            page = customerSearchCriteria.getPagination().getPage();
            pageSize = customerSearchCriteria.getPagination().getPageSize();
        }

        Page<fr.mr_market.mr_customer.model.Customer> customers =
                customerService.searchCustomers(criteria, PageRequest.of(page, pageSize), sort);

        CustomerSearchResponse response = new CustomerSearchResponse();
        if (customers != null) {
            List<Customer> customerList = customers.getContent().stream().map(customer -> mapper.map(customer, Customer.class))
                    .collect(Collectors.toList());
            response.setCustomers(customerList);
            response.setTotalCount(customers.getTotalElements());
        }

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
