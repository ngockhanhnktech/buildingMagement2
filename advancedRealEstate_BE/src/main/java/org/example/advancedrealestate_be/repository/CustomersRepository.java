package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, String> {
}
