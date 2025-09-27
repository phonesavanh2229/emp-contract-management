// com.contractEmployee.contractEmployee.search.EmployeeSpecification.java
package com.contractEmployee.contractEmployee.search;

import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import com.contractEmployee.contractEmployee.entity.Visa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecification {

    // ---- PASSPORT ----
    public static Specification<Employee> passportFilter(FilterState state, int expiringDays) {
        if (state == null || state == FilterState.ANY) return alwaysTrue();

        return (root, query, cb) -> {
            Join<Employee, Passport> p = root.join("passports", JoinType.LEFT);
            LocalDate now = LocalDate.now();

            return switch (state) {
                case ACTIVE    -> cb.and(cb.equal(p.get("status"), "ACTIVE"),
                        cb.greaterThan(p.get("expiryDate"), now));
                case EXPIRING  -> cb.between(p.get("expiryDate"), now, now.plusDays(expiringDays));
                case EXPIRED   -> cb.lessThan(p.get("expiryDate"), now);
                default        -> cb.conjunction();
            };
        };
    }

    // ---- VISA ----
    public static Specification<Employee> visaFilter(FilterState state, int expiringDays) {
        if (state == null || state == FilterState.ANY) return alwaysTrue();

        return (root, query, cb) -> {
            Join<Employee, Passport> p = root.join("passports", JoinType.LEFT);
            Join<Passport, Visa> v = p.join("visas", JoinType.LEFT);
            LocalDate now = LocalDate.now();

            return switch (state) {
                case ACTIVE    -> cb.and(cb.equal(v.get("status"), "ACTIVE"),
                        cb.greaterThan(v.get("expiryDate"), now));
                case EXPIRING  -> cb.between(v.get("expiryDate"), now, now.plusDays(expiringDays));
                case EXPIRED   -> cb.lessThan(v.get("expiryDate"), now);
                default        -> cb.conjunction();
            };
        };
    }

    // ---- RENTAL CERT ----
    public static Specification<Employee> rentalFilter(FilterState state, int expiringDays) {
        if (state == null || state == FilterState.ANY) return alwaysTrue();

        return (root, query, cb) -> {
            Join<Employee, Passport> p = root.join("passports", JoinType.LEFT);
            Join<Passport, Visa> v = p.join("visas", JoinType.LEFT);
            Join<Visa, RentalCertificate> r = v.join("rentals", JoinType.LEFT);
            LocalDate now = LocalDate.now();

            return switch (state) {
                case ACTIVE    -> cb.and(cb.equal(r.get("status"), "ACTIVE"),
                        cb.greaterThan(r.get("endDate"), now));
                case EXPIRING  -> cb.between(r.get("endDate"), now, now.plusDays(expiringDays));
                case EXPIRED   -> cb.lessThan(r.get("endDate"), now);
                default        -> cb.conjunction();
            };
        };
    }

    // ---- Utility ----
    public static Specification<Employee> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static FilterState parse(String s) {
        if (s == null || s.isBlank()) return FilterState.ANY;
        try { return FilterState.valueOf(s.trim().toUpperCase()); }
        catch (IllegalArgumentException ex) { return FilterState.ANY; }
    }
}
