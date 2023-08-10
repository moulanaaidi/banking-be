package com.banking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingCustomerIdValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingCustomerId {
	String message() default "Invalid customerId";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}