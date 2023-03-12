package fr.mr_market.mr_customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
abstract class BaseEntity {

    @GeneratedValue
    private UUID uuid;

    @Column(name = "create_date", columnDefinition = "DATE")
    private LocalDateTime createDate;
}
