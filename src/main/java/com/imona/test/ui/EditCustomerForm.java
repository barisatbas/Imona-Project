package com.imona.test.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;

import com.imona.test.model.Customer;
import com.imona.test.service.CustomerService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EditCustomerForm extends Window implements ClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    ComboBox genders = new ComboBox("Gender");
    
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
 
    Customer customer;
    
    @Autowired
	private transient CustomerService customerService;
    
    OnCustomerEditListener listener;

	public EditCustomerForm(Customer customer, OnCustomerEditListener listener){	
		this.customer = customer;
		this.listener = listener;
		
		setCaption("Edit Customer");
		
		VerticalLayout mainLayout = new VerticalLayout();
		setContent(mainLayout);
		
		mainLayout.setWidth("300px");
		mainLayout.setHeight("300px");
		
		genders.addItem("Male");
		genders.addItem("Female");
		
		FormLayout form = new FormLayout();
        form.setSizeUndefined();
        form.addComponent(name);
        form.addComponent(surname);
        form.addComponent(genders);
        mainLayout.addComponent(form);
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponent(cancel);
        actions.addComponent(save);
        
        mainLayout.addComponent(actions);
        
        save.addListener(this);
        cancel.addListener(this);
        
        name.setValue(customer.getName());
        surname.setValue(customer.getSurname());
        genders.setValue((customer.isMale()) ? "Male" : "Female");
               
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == save){
			
			try{
				if(name.getValue().equals("") || surname.getValue().equals("") || genders.getValue() == null)
					throw new IllegalArgumentException("empty value");
				
				customer.setName(name.getValue().toString());
				customer.setSurname(surname.getValue().toString());
				customer.setMale((genders.getValue().equals("Male") ? true : false));
				
				try {
					customerService.updateCustomer(customer);				    
				    getWindow().showNotification("Successfully saved !");	
				    
				    getParent().removeWindow(EditCustomerForm.this);
				    listener.onCustomerEditSuccess(customer);
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}catch (IllegalArgumentException e) {
				getWindow().showNotification("Missing value !");
			}
			
		}else if(event.getButton() == cancel){
			getParent().removeWindow(EditCustomerForm.this);
			listener.onCustomerEditCancel();
		}
	}	
}
