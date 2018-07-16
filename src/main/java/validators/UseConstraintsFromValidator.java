package validators;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UseConstraintsFromValidator implements ConstraintValidator<UseConstraintsFrom, Object> {
    private UseConstraintsFrom constraintAnnotation;
    private ValidatorFactory validatorFactory;

    @Override
    public void initialize(UseConstraintsFrom constraintAnnotation)
    {
        this.constraintAnnotation = constraintAnnotation;
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        boolean valid = true;

        Validator validator = this.validatorFactory.getValidator();
        Set<? extends ConstraintViolation<?>> violations = validator.validateValue(constraintAnnotation.klazz(), constraintAnnotation.property(), value, constraintAnnotation.groups());
        for (ConstraintViolation<?> violation: violations) {
            if (valid) {
                context.disableDefaultConstraintViolation();
                valid = false;
            }
            String message = violation.getMessage();
            if (message.length() >= 2) {
                message = message.substring(0, 1).toUpperCase() + message.substring(1);
            }
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return valid;
    }
}
