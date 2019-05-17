package jschool.validator.annotations;

import jschool.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * annotation for phone number validator
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {
    String message() default "Phone number format is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
