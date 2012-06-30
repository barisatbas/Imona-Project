package com.imona.test.service;

import java.util.List;

import com.imona.test.model.Customer;

public interface CustomerService {

	public void saveCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public List<Customer> getCustomers();
	
	public void removeCustomer(Customer customer);
}
