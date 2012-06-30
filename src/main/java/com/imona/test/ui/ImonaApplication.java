package com.imona.test.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.Application;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Window;

@Component(value = "imonaApplication")
@Scope(value = "prototype")
public class ImonaApplication extends Application implements TabSheet.SelectedTabChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Window mainWindow;
	
	@Override
	public void init() {
		
		mainWindow = new Window("Test Window");     

        TabSheet t = new TabSheet();
        t.setHeight("500px");
        t.setWidth("900px");

        t.addTab(new CustomerInfoForm(), "Customer Information");
        t.addTab(new ListOfCustomers(), "List of Customers");
        
        t.addListener(this);

        mainWindow.addComponent(t);
		
		setMainWindow(mainWindow);
		
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		
	}
}
