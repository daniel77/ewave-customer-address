package com.ewave.volvo.repository;

import com.ewave.volvo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByZipCodeAndNumberEquals(String zip, Integer number);
}
