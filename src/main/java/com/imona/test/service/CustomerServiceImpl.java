package com.imona.test.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.imona.test.model.Customer;

@Repository
public class CustomerServiceImpl implements CustomerService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void saveCustomer(Customer customer) {
		em.persist(customer);	
	}

	@Transactional
	public Customer updateCustomer(Customer customer) {
		return em.merge(customer);
	}
	
	@Transactional
	public void removeCustomer(Customer customer) {		
		em.remove(em.merge(customer));
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomers() {
		return (List<Customer>)em.createQuery("select c from Customer c").getResultList();
	}
}
