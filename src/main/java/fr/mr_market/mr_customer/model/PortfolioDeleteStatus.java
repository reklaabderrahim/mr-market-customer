package fr.mr_market.mr_customer.model;

import java.util.Arrays;

public enum PortfolioDeleteStatus {
    NOT_DELETED(0),
    DELETED_RETURNED_PRODUCT(1),
    DELETED_WRONGLY_ADDED_ITEM(2),
    DELETED_DUPLICATED_PRODUCT(3),
    DELETED_BY_USER(4),
    HISTORICAL_DELETION(9);

    private int id;

    PortfolioDeleteStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PortfolioDeleteStatus of(int id) {
        return Arrays.stream(values())
                .filter(portfolioDeleteStatus -> portfolioDeleteStatus.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("id portfolio delete status must be 0, 1, 2, 3, 4 or 9"));
    }
}
