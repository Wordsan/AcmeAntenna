package validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation ensures the date property it is attached to is in the past, the present,
 * or up to 30 seconds in the future.
 *
 * The reason for it's existence is that the default @Past annotation works as specified in
 * it's documentation:
 * Returns true if and only if the instant of time represented by this Date object is strictly earlier than the
 * instant represented by when [(the current date)]; false otherwise.
 *
 * In most cases this is fine because the entity is created before it is validated.
 *
 * However, in rare instances, the current time does not change
 * between the creation of the entity and it’s validation. This can be due to
 * various reasons, for example a low clock resolution (in my experience this happens in Windows
 * XP more than any other OS I tested) or a NTP daemon correcting for clock skew, etc. As a
 * result, any entity that has a @Past annotation on a creationTime or similar field ends up
 * spuriously (and seemingly randomly) failing validations and cancelling the operation.
 *
 * This solves the problem in a robust way by allowing up to 30 seconds of leeway for validation to occur.
 *
 */

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastWithMarginValidator.class)
public @interface PastWithMargin {
    String message() default "{javax.validation.constraints.Past.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
