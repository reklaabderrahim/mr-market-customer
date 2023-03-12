package fr.mr_market.mr_customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    @Size(max = 50)
    @NotBlank(message = "The first name is required")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 50)
    @NotBlank(message = "The last name is required")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "mail")
    @Size(max = 255)
    @Email
    private String mail;

    @Column(name = "login_date", columnDefinition = "DATE")
    private LocalDateTime loginDate;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @OrderBy("create_date DESC")
    private List<CollectionItem> collectionItems = new ArrayList<>();

    public static Customer create(String firstName, String lastName, Gender gender, LocalDate birthDate, String mail) {
        Customer customer = new Customer();
        customer.setUuid(UUID.randomUUID());
        customer.setCreateDate(LocalDateTime.now());
        customer.setActive(true);
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.mail = mail;
        customer.gender = gender;
        customer.birthDate = birthDate;
        return customer;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]").add("firstName=" + firstName).add("lastName=" + lastName).add("gender=" + gender).add("birthDate=" + birthDate).add("mail=" + mail).add("loginDate=" + loginDate).add("active=" + active).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getUuid(), customer.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
