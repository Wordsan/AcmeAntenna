package validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * This annotation can be used in a form class to ensure a field is validated
 * according to the constraints of another field in another entity class.
 * <p>
 * Note that this does not work with CustomValidator.
 * <p>
 * To use, attach this to a field's getter method as with any other constraint.
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UseConstraintsFromValidator.class)
public @interface UseConstraintsFrom {
    String message() default "{misc.error.undefinedValidationError}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Name of the class to use constraints from.
     */
    Class<?> klazz();

    /**
     * Field of the class to use constraints from
     */
    String property();
}
