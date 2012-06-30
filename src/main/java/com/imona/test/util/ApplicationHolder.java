package com.imona.test.util;

import com.vaadin.Application;

public class ApplicationHolder {
	
	private static ThreadLocal<Application> app = new ThreadLocal<Application>();
	
	public static void setApplication(Application application) {
		app.set(application);
	}
	
	public static void resetApplication() {
		app.remove();
	}
	
	public static Application getApplication() {
		return app.get();
	}
}
