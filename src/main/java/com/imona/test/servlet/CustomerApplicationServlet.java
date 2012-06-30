package com.imona.test.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@SuppressWarnings("serial")
public class CustomerApplicationServlet extends AbstractApplicationServlet {

	private static final Log logger = LogFactory.getLog(CustomerApplicationServlet.class);

	private WebApplicationContext applicationContext;
	private Class<? extends Application> applicationClass;
	private String applicationBean;

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("init()");
		}
		super.init(servletConfig);
		applicationBean = servletConfig.getInitParameter("applicationBean");
		if (applicationBean == null) {
			throw new ServletException("ApplicationBean not specified in servlet parameters");
		}
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
		applicationClass = (Class<? extends Application>) applicationContext.getType(applicationBean);
	}
	
	@Override
	protected Application getNewApplication(HttpServletRequest request) throws ServletException {
		if (logger.isTraceEnabled()) {
			logger.trace("getNewApplication()");
		}
		return (Application) applicationContext.getBean(applicationBean);
	}

	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		if (logger.isTraceEnabled()) {
			logger.trace("getApplicationClass()");
		}
		return applicationClass;
	}

}