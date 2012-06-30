package com.imona.test.util;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationHelper {

	private ApplicationContext context;

	public ApplicationHelper(Application application) {
		ServletContext servletContext = ((WebApplicationContext) application.getContext()).getHttpSession().getServletContext();
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	public Object getBean(final String beanRef) {
		return context.getBean(beanRef);
	}    
}