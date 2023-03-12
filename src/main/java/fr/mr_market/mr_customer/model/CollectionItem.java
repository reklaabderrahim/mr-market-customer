package fr.mr_market.mr_customer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "collection_item")
public class CollectionItem extends FullBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_item_id_seq")
    @SequenceGenerator(name = "collection_item_id_seq", sequenceName = "collection_item_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "product_uuid")
    private UUID productUuid;

    @Column(name = "is_gift")
    private boolean gift;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "delete_status")
    private PortfolioDeleteStatus deleteStatus;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    public static CollectionItem create(Customer customer, UUID productUuid) {
        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setUuid(randomUUID());
        collectionItem.customer = customer;
        collectionItem.productUuid = productUuid;
        collectionItem.deleteStatus = PortfolioDeleteStatus.NOT_DELETED;
        return collectionItem;
    }

    public CollectionItem receiveAsGift(boolean gift) {
        this.gift = gift;
        return this;
    }

    public CollectionItem deleteStatus(PortfolioDeleteStatus portfolioDeleteStatus) {
        this.deleteStatus = portfolioDeleteStatus;
        this.deleteDate = isDeleted() ? now() : null;
        return this;
    }

    public CollectionItem update() {
        this.setUpdateDate(now());
        return this;
    }

    public boolean isDeleted() {
        return deleteStatus != PortfolioDeleteStatus.NOT_DELETED;
    }
}
