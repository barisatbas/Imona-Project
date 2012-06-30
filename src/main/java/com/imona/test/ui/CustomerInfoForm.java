package com.imona.test.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;

import com.imona.test.model.Customer;
import com.imona.test.service.CustomerService;
import com.imona.test.util.Utility;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CustomerInfoForm extends VerticalLayout implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    ComboBox genders = new ComboBox("Gender");
    
    private Button save = new Button("Save");
    
    FormLayout form;
    
    @Autowired
	private transient CustomerService customerService;

	public CustomerInfoForm(){
		
		setMargin(true);

		genders.addItem("Male");
		genders.addItem("Female");
		
		form = new FormLayout();
        form.setSizeUndefined();
        form.addComponent(name);
        form.addComponent(surname);
        form.addComponent(genders);
        addComponent(form);
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponent(save);
        
        addComponent(actions);
        
        save.addListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		if(event.getButton() == save){		
			try{
				
				if(name.getValue().equals("") || surname.getValue().equals("") || genders.getValue() == null)
					throw new IllegalArgumentException("empty value");
				
				Customer customer = new Customer();
				customer.setId(Utility.createRandomId());
				customer.setName(name.getValue().toString());
				customer.setSurname(surname.getValue().toString());
				customer.setMale((genders.getValue().equals("Male") ? true : false));
				
				try {
					customerService.saveCustomer(customer);				    
				    getWindow().showNotification("Successfully saved !");	
				    
				    name.setValue("");
				    surname.setValue("");
				    genders.setValue("");
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}catch (IllegalArgumentException e) {				
				getWindow().showNotification("Missing value !");
			}		
		}		
	}
}
