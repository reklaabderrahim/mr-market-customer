package fr.mr_market.mr_customer.model;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;

    Gender(String value) {
        this.gender = value;
    }
}