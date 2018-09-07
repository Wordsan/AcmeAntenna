package validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

/**
 * This annotation is used to add a custom validator to a domain entity.
 * <p>
 * To use, attach the annotation HasCustomValidators to the class,
 * and attach this validator to a @Transient boolean method which name
 * MUST start with "is", and preferably starts with "isValid"
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValidator {
    /**
     * Error message that will be shown to the user on validation failure. Ensure it is surrounded by curly braces to use i18n messages.
     */
    String message() default "{misc.error.unknown}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Fields that will be marked as mistaken in the user form.
     */
    String[] applyOn() default {};
}
