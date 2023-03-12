package fr.mr_market.mr_customer.model;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerSpecs {

    public static Specification<Customer> searchCustomersSpecs(CustomerSearchCriteria criteria, String sort) {
        return (root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> searchCustomerPredicate = new ArrayList<>();

            if (criteria.getGender() != null) {
                searchCustomerPredicate.add(criteriaBuilder.equal(root.get(CustomerAttributeNames.GENDER.getValue()), criteria.getGender()));
            }

            if (criteria.getActive() != null) {
                searchCustomerPredicate.add(criteriaBuilder.equal(root.get(CustomerAttributeNames.ACTIVE.getValue()), criteria.getActive()));
            }

            query.orderBy(createOrderBy(sort, root, criteriaBuilder));
            return criteriaBuilder.and(searchCustomerPredicate.toArray(new Predicate[0]));
        };
    }

    private static List<Order> createOrderBy(String sort, Root<?> from, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new ArrayList<>();
        if (null == sort) {
            orders.add(criteriaBuilder.asc(from.get(CustomerAttributeNames.LAST_NAME.getValue())));
            return orders;
        }
        boolean desc = sort.startsWith("-");
        String field = sort.replaceAll("^[+-]", "");

        String[] fields = field.split("\\.");
        Path<Object> path = from.get(fields[0]);
        path = getFieldChildren(fields, path);

        if (desc) {
            orders.add(criteriaBuilder.desc(path));
        } else {
            orders.add(criteriaBuilder.asc(path));
        }
        return orders;
    }

    private static Path<Object> getFieldChildren(String[] fields, Path<Object> path) {
        if (fields.length > 1) {
            String[] childrenFields = Arrays.copyOfRange(fields, 1, fields.length);
            for (String childrenField : childrenFields) {
                path = path.get(childrenField);
            }
        }
        return path;
    }
}
