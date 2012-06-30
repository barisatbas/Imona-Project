package com.imona.test.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;

import com.imona.test.model.Customer;
import com.imona.test.service.CustomerService;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ListOfCustomers extends VerticalLayout implements ClickListener, OnCustomerEditListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Button showRecords;
	Button refreshTable;

	Table table;

	@Autowired
	private transient CustomerService customerService;

	public ListOfCustomers(){
		setMargin(true);
		
		showRecords = new Button("Show Records");
		showRecords.setDisableOnClick(true);
		showRecords.addListener(this);
		
		refreshTable = new Button("Refresh Table");
		refreshTable.addListener(this);
		
		HorizontalLayout actions = new HorizontalLayout();
        actions.addComponent(showRecords);
        actions.addComponent(refreshTable);
            
        addComponent(actions);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == showRecords){		
			addComponent(getCustomerTable());
		}else if(event.getButton() == refreshTable){
			if(table != null){
				removeComponent(table);	
				addComponent(getCustomerTable());
			}
		}
	}

	private Table getCustomerTable(){
		table = new Table("Customers");

		table.setWidth("100%");
		table.setHeight("170px");

		table.setContainerDataSource(createCustomerContainer(customerService.getCustomers()));
		table.setSelectable(true);
		table.setMultiSelect(true);
		table.setImmediate(true);

		table.setColumnHeaders(new String[] { "Id", "Name", "Surname", "Gender" });		
		table.addActionHandler(new TableActionHandler());

		return table;
	}

	private class TableActionHandler implements Handler{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Action remove = new Action("Delete");
		Action edit = new Action("Edit");
		Action[] actions = new Action[] { remove, edit };

		public Action[] getActions(Object target, Object sender) {
			return actions;
		}

		public void handleAction(Action action, Object sender, Object target) {
			Item item = table.getItem(target);       	
			if (action == remove) {

				try{

					customerService.removeCustomer(getCustomer(item));
					
					removeComponent(table);	
					addComponent(getCustomerTable());
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (action == edit) {
				getWindow().addWindow(new EditCustomerForm(getCustomer(item), ListOfCustomers.this));
			}
		}	
	}

	private Customer getCustomer(Item item){
		Customer customer = new Customer();

		customer.setId((Integer)(item.getItemProperty("id").getValue()));
		customer.setName((String) item.getItemProperty("name").getValue());
		customer.setSurname((String) item.getItemProperty("surname").getValue());

		String genderStr = (String) item.getItemProperty("gender").getValue();

		customer.setMale((genderStr.equals("Male")) ? true : false);

		return customer;
	}

	/*
	private IndexedContainer getDefaultContainer(){
		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("surname", String.class, null);
        container.addContainerProperty("gender", String.class, null);

        for (int i = 0; i < 20; i++) {
            String id = "id" + i;
        	String name = "name" + i;
            String surname = "surname" + i;
            String gender = (i%2 == 0) ? "male" : "female";

            Item item = container.addItem(id);
            item.getItemProperty("name").setValue(name);
            item.getItemProperty("surname").setValue(surname);
            item.getItemProperty("gender").setValue(gender);
        }

		return container;
	}*/

	private Container createCustomerContainer(List<Customer> customers) {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("id", Integer.class, null);
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("surname", String.class, null);
		container.addContainerProperty("gender", String.class, null);

		for(Customer customer : customers) {

			Item item = container.addItem(customer.getId());
			item.getItemProperty("id").setValue(customer.getId());
			item.getItemProperty("name").setValue(customer.getName());
			item.getItemProperty("surname").setValue(customer.getSurname());
			item.getItemProperty("gender").setValue(customer.isMale() ? "Male" : "Female");
		}

		return container;
	}

	@Override
	public void onCustomerEditSuccess(Customer customer) {
		getWindow().showNotification("on edit success !");

		removeComponent(table);	
		addComponent(getCustomerTable());
	}

	@Override
	public void onCustomerEditCancel() {

	}
}
