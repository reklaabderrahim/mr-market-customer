package fr.mr_market.mr_customer.model;

import lombok.Getter;

@Getter
public enum CustomerAttributeNames {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    GENDER("gender"),
    BIRTH_DATE("birthDate"),
    MAIL("mail"),
    LOGIN_DATE("loginDate"),
    ACTIVE("active");

    private final String value;

    CustomerAttributeNames(String value) {
        this.value = value;
    }
}
