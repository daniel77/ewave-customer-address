package com.ewave.volvo.service;

import com.ewave.volvo.model.Address;
import com.ewave.volvo.model.Customer;
import com.ewave.volvo.repository.AddressRepository;
import com.ewave.volvo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public Customer saveCustomer(Customer customer) {
        List<Address> addresses = new ArrayList<>();

        if (customer.getAddress() != null) {
            customer.getAddress().forEach(a -> {
                Optional<Address> allByZipCodeAndNumberEquals =
                        addressRepository.findByZipCodeAndNumberEquals(a.getZipCode(), a.getNumber());
                if (allByZipCodeAndNumberEquals.isPresent()) {
                    addresses.add(allByZipCodeAndNumberEquals.get());
                } else {
                    addresses.add(addressRepository.save(a));
                }
            });
            customer.setAddress(addresses);
        }

        customer = customerRepository
                .save(customer);
        return customer;
    }

    public List<Customer> findAllByAddressZipCodeEquals(String zipcode) {
        return customerRepository.findAllByAddressZipCodeEquals(zipcode);
    }

    public Optional<Customer> findById(long id) {
        return customerRepository.findByDocumentId(id);
    }

    public void delete(long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
