package com.imona.test.ui;

import com.imona.test.model.Customer;

public interface OnCustomerEditListener {
	
	public void onCustomerEditSuccess(Customer customer);	
	public void onCustomerEditCancel();
}
