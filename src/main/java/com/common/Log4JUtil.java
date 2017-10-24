package com.common;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * 
 * 
 */
public class Log4JUtil {
	private static Logger logger = null;

	static {
		URL fileURL = Log4JUtil.class.getClassLoader().getResource("log4j.properties");
		logger = Logger.getLogger(Log4JUtil.class);
		PropertyConfigurator.configure(fileURL);
	}

	public static void error(String msg) {
		Log4JUtil.error(msg, null);
	}

	public static void error(Throwable t) {
		Log4JUtil.error(null, t);
	}

	public static void error(String message, Throwable t) {
		logger.error(message, t);
	}
	public static void info(String msg) {
		Log4JUtil.info(msg, null);
	}
	
	public static void info(Throwable t) {
		Log4JUtil.info(null, t);
	}
	
	public static void info(String message, Throwable t) {
		logger.info(message, t);
	}
}
