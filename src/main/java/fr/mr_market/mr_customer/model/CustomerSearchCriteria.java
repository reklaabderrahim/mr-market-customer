package fr.mr_market.mr_customer.model;

import lombok.Data;

@Data
public class CustomerSearchCriteria {
    private Gender gender;
    private Boolean active;
}
