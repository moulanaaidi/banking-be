package com.banking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class BankingApplication {

	private static final Logger LOGGER = LogManager.getLogger(BankingApplication.class);

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(BankingApplication.class, args);

		LOGGER.info("Info level log message");
		LOGGER.debug("Debug level log message");
		LOGGER.error("Error level log message");

	}

}
